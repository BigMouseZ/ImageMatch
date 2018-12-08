package com.signalway.highway.service.impl;

import com.signalway.highway.dao.ImageMatchDao.ImageMatchDao;
import com.signalway.highway.entity.MapPojo;
import com.signalway.highway.entity.MapQueryPojo;
import com.signalway.highway.service.ImageMatchService;
import com.signalway.highway.util.ImageUtil;
import com.signalway.highway.util.PropertiesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Created by ZhangGang on 2017/5/15.
 */
@Service("imageMatchService")
public class ImageMatchServiceImpl implements ImageMatchService {

  @Autowired
  ImageMatchDao imageMatchDao;
  @Override
  public List<MapQueryPojo> ImageMergeInfoList(MapQueryPojo QueryPojo)  {
      int i =0;
      Properties filePathInfo = PropertiesUtils.load("conf/filepath.properties");
      List<MapQueryPojo> mapQueryPojoList = imageMatchDao.ImageMergeInfoList(QueryPojo);
      if(mapQueryPojoList.size() == 0){
        System.out.println("没有找到可拼接数据，请确认配置是否正确。");
      }
      for (MapQueryPojo mapQueryPojo:mapQueryPojoList) {
          List<MapPojo> list = imageMatchDao.ImageMatchBlobList(mapQueryPojo);
          byte[] bbcc;
          try {
              for (MapPojo mapPojo:list) {
                  bbcc =  mapPojo.getTile();
                  ++i;
                 // System.out.println(bbcc);
                  ByteArrayInputStream bais = new ByteArrayInputStream(bbcc);
                  BufferedImage bi1 = ImageIO.read(bais);
                  //创建临时文件
                  String strPath = filePathInfo.getProperty("filepath.temp");
                  File file = new File(strPath);
                  if(!file.exists()){
                    file.mkdirs();
                    System.out.println("创建临时文件");
                  }
                  File w2 = new File(filePathInfo.getProperty("filepath.temp")+mapPojo.getZoom()+"_"+mapPojo.getY()+"_"+mapPojo.getX()+"."+filePathInfo.getProperty("filepath.type"));//可以是jpg,png,gif格式
                  ImageIO.write(bi1, filePathInfo.getProperty("filepath.type"),w2);//不管输出什么格式图片，此处不需改动
              }
              System.out.println("总瓦片数："+i);
              i =0;
              //拼接图片
              ImageUtil.TestMapPrint(mapQueryPojo);
          } catch (IOException e) {
            e.printStackTrace();

          }
      }
      return mapQueryPojoList;
  }

  @Override
  public void SaveImageMergeInfo(File filepath) throws InterruptedException {

      try {

          List<String> list = new ArrayList<String>();
          List<MapPojo> mapPojoList = new ArrayList<>();
          List<String> notPureColorlist = new ArrayList<String>();
          String filePath = filepath.getAbsolutePath();
          System.out.println("下个文件路径："+filepath);
          //遍历此路径下的所有jpg文件
          list = ImageUtil.getListFiles(filePath,"jpg",true,list);
          if(list.size() > 0) {
            //不是纯色图片的路径,并行处理
            notPureColorlist = list.stream().parallel().filter(example -> !ImageUtil.TestColor(example)).collect(Collectors.toList());
          }
          if(notPureColorlist.size() > 0){
            notPureColorlist.forEach(example->{
              MapPojo mapPojo = savePicture(example,filePath);
              if(mapPojo.getType()!=null&&mapPojo.getType()>0){
                mapPojoList.add(mapPojo);
              }
            });
          }
        if(mapPojoList.size() > 0){
            imageMatchDao.SaveImageMergeInfoList(mapPojoList);
        }
        System.out.println("=====线程ID：" + Thread.currentThread().getId());
        System.out.println("=====当前线程处理数据量：" + mapPojoList.size());
        System.out.println("=====当前线程的文件夹：" + filepath);
        System.out.println("瓦片总数：" + list.size());

      }catch (Exception e){
          System.out.println("=====当前出错线程的文件夹===："+filepath);
          e.printStackTrace();
          return;
      }
  }

  public MapPojo savePicture(String example,String filePath){
      Properties filePathInfo = PropertiesUtils.load("conf/filepath.properties");
      File file = new File(example);
      MapPojo mapPojo = new MapPojo();
      String[] splits = filePath.split("\\\\");
      String[] strings = splits[splits.length-1].split("_");
      int zoom = Integer.valueOf(strings[0]);
      int Xstart = Integer.valueOf(strings[1]);
      int Ystart =  Integer.valueOf(strings[2]);
      try {
          InputStream inputStream = new FileInputStream(file);
          byte[] bytes = new byte[inputStream.available()];
          inputStream.read(bytes, 0 ,bytes.length);
         // System.out.println(file.getAbsolutePath()+"======="+file.getName());
          String filename = file.getName().substring(0,file.getName().length()-4);
          String[] Zoom_X_Y = filename.split("-");
         // System.out.println(Zoom_X_Y[0]+"==="+Zoom_X_Y[1]+"==="+Zoom_X_Y[2]);
          int Zoffset = Integer.parseInt(Zoom_X_Y[0]);
          int Xoffset = Integer.valueOf(Zoom_X_Y[1]);
          int Yoffset = Integer.valueOf(Zoom_X_Y[2]);
          double divisor;
          if(Integer.valueOf(filePathInfo.getProperty("filepath.MaxLevel")) ==19
                  &&zoom==19){ //最小原子是13  64X64
            switch (Zoffset){
              //对应19级
              case 7:
                divisor = Math.pow(2,(7-Zoffset));
                mapPojo = sealMaoPojo(mapPojo,divisor,19,Xstart,Ystart,Xoffset,Yoffset,bytes);
                break;
              //对应18级
              case 6:
                divisor = Math.pow(2,(7-Zoffset));
                mapPojo = sealMaoPojo(mapPojo,divisor,18,Xstart,Ystart,Xoffset,Yoffset,bytes);
                break;
              //对应17级
              case 5:
                divisor = Math.pow(2,(7-Zoffset));
                mapPojo = sealMaoPojo(mapPojo,divisor,17,Xstart,Ystart,Xoffset,Yoffset,bytes);
                break;
              //对应16级
              case 4:
                divisor = Math.pow(2,(7-Zoffset));
                mapPojo = sealMaoPojo(mapPojo,divisor,16,Xstart,Ystart,Xoffset,Yoffset,bytes);
                break;
             //对应15级
              case 3:
                divisor = Math.pow(2,(7-Zoffset));
                mapPojo = sealMaoPojo(mapPojo,divisor,15,Xstart,Ystart,Xoffset,Yoffset,bytes);
                break;
              default:break;
            }
          }else if(Integer.valueOf(filePathInfo.getProperty("filepath.MaxLevel")) ==18
                &&zoom==18){
                switch (Zoffset){
              //对应18级最小原子是13  32X32
              case 6:
                divisor = Math.pow(2,(6-Zoffset));
                mapPojo = sealMaoPojo(mapPojo,divisor,18,Xstart,Ystart,Xoffset,Yoffset,bytes);
                break;
              //对应17级
              case 5:
                divisor = Math.pow(2,(6-Zoffset));
                mapPojo = sealMaoPojo(mapPojo,divisor,17,Xstart,Ystart,Xoffset,Yoffset,bytes);
                break;
              //对应16级
              case 4:
                divisor = Math.pow(2,(6-Zoffset));
                mapPojo = sealMaoPojo(mapPojo,divisor,16,Xstart,Ystart,Xoffset,Yoffset,bytes);
                break;
              //对应15级
              case 3:
                divisor = Math.pow(2,(6-Zoffset));
                mapPojo = sealMaoPojo(mapPojo,divisor,15,Xstart,Ystart,Xoffset,Yoffset,bytes);
                break;
              default:break;
            }
          }

          if(zoom ==14){ //最小原子是10 16X16
            switch (Zoffset) {
              //对应14级
              case 6:
                divisor = Math.pow(2, (6 - Zoffset));
                mapPojo = sealMaoPojo(mapPojo,divisor,14,Xstart,Ystart,Xoffset,Yoffset,bytes);
                break;
           /* case 5:
                divisor = Math.pow(2, (5 - Zoffset));
                mapPojo = sealMaoPojo(mapPojo,divisor,14,Xstart,Ystart,Xoffset,Yoffset,bytes);
                break;*/
            }
          }else if(zoom ==13){ //最小原子是7 1X1
            switch (Zoffset) {
              //对应13级
              case 6:
                divisor = Math.pow(2, (6 - Zoffset));
                mapPojo = sealMaoPojo(mapPojo,divisor,13,Xstart,Ystart,Xoffset,Yoffset,bytes);
                break;
            }
          }else if(zoom ==12){ //最小原子是7 1X1
            switch (Zoffset) {
              //对应12级
              case 5:
                divisor = Math.pow(2, (5 - Zoffset));
                mapPojo = sealMaoPojo(mapPojo,divisor,12,Xstart,Ystart,Xoffset,Yoffset,bytes);

                break;
            }
          }else if(zoom ==11){ //最小原子是7 1X1
            switch (Zoffset) {
              //对应11级
              case 4:
                divisor = Math.pow(2, (4 - Zoffset));
                mapPojo = sealMaoPojo(mapPojo,divisor,11,Xstart,Ystart,Xoffset,Yoffset,bytes);
                break;
            }
          }else if(zoom ==10){ //最小原子是7 1X1
            switch (Zoffset) {
              //对应10级
              case 3:
                divisor = Math.pow(2, (3 - Zoffset));
                mapPojo = sealMaoPojo(mapPojo,divisor,10,Xstart,Ystart,Xoffset,Yoffset,bytes);
                break;
            }
          }else if(zoom ==9){ //最小原子是7 1X1
            switch (Zoffset){
              //对应9级
              case 2:
                divisor = Math.pow(2, (2 - Zoffset));
                mapPojo = sealMaoPojo(mapPojo,divisor,9,Xstart,Ystart,Xoffset,Yoffset,bytes);
                break;
            }
          }else if(zoom ==8){ //最小原子是7 1X1
              switch (Zoffset){
                  //对应8级
                  case 1:
                      divisor = Math.pow(2, (1 - Zoffset));
                      mapPojo = sealMaoPojo(mapPojo,divisor,8,Xstart,Ystart,Xoffset,Yoffset,bytes);
                      break;
              }
          }else if(zoom ==7){ //最小原子是7 1X1
              switch (Zoffset){
                  //对应7级
                  case 0:
                      divisor = Math.pow(2, (0 - Zoffset));
                      mapPojo = sealMaoPojo(mapPojo,divisor,7,Xstart,Ystart,Xoffset,Yoffset,bytes);
                      break;
              }
          }
      }catch (IOException ex){
          ex.printStackTrace();
      }
        return mapPojo;
  }
  //返回实体
  public MapPojo sealMaoPojo(MapPojo mapPojo,
                             double divisor,
                             int zoom,
                             int Xstart,
                             int Ystart ,
                             int Xoffset ,
                             int Yoffset,
                             byte[] bytes){
    Properties photomatch = PropertiesUtils.load("conf/photomatch.properties");
    int tileX = (int) Math.floor(Xstart / divisor) + Xoffset;
    int tileY = (int) Math.floor(Ystart / divisor) + Yoffset;
    mapPojo.setType(Integer.valueOf(photomatch.getProperty("type")));
    mapPojo.setZoom(zoom);
    mapPojo.setX(tileX);
    mapPojo.setY(tileY);
    mapPojo.setTile(bytes);
    return mapPojo;
  }
  @Override
  public void InsertImageMergeInfoList(List<MapQueryPojo> mapQueryPojos){
    imageMatchDao.InsertImageMergeInfoList(mapQueryPojos);
  }
    public int CreateTempTable(String tableName){
      return  imageMatchDao.createNewTable(tableName);
    }
}


