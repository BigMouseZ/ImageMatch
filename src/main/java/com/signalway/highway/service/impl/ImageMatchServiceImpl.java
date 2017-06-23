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

/**
 * Created by ZhangGang on 2017/5/15.
 */
@Service
public class ImageMatchServiceImpl implements ImageMatchService {

  @Autowired
  ImageMatchDao imageMatchDao;

  @Override
  public List<MapQueryPojo> ImageMergeInfoList(MapQueryPojo listQueryPojo)  {
    int i =0;
    Properties filePathInfo = PropertiesUtils.load("filepath.properties");
    List<MapQueryPojo> mapQueryPojoList = imageMatchDao.ImageMergeInfoList(listQueryPojo);

    for (MapQueryPojo mapQueryPojo:mapQueryPojoList) {

      List<MapPojo> list = imageMatchDao.ImageMatchBlobList(mapQueryPojo);

      byte[] bbcc = null;
      try {

        for (MapPojo mapPojo:list) {

          bbcc = (byte[]) mapPojo.getTile();
          ++i;
          System.out.println(bbcc);
          ByteArrayInputStream bais = new ByteArrayInputStream(bbcc);
          BufferedImage bi1 = ImageIO.read(bais);
          //创建临时文件
          File w2 = new File(filePathInfo.getProperty("filepath.temp")+mapPojo.getZoom()+"_"+mapPojo.getY()+"_"+mapPojo.getX()+".jpg");//可以是jpg,png,gif格式
          ImageIO.write(bi1, "jpg", w2);//不管输出什么格式图片，此处不需改动
        }
        System.out.println("总瓦片数："+i);
        //拼接图片
        ImageUtil.TestMapPrint(mapQueryPojo);

      } catch (IOException e) {

        e.printStackTrace();
      }

    }

    return mapQueryPojoList;
  }

  @Override
  public void SaveImageMergeInfo()  {

    MapPojo mapPojo = new MapPojo();
    try {
      Properties filePathInfo = PropertiesUtils.load("filepath.properties");
     //依次执行9到18级的导入
      String filePathName = filePathInfo.getProperty("filepath.Insert.18");

      File filePathIn = new File(filePathName);
      File[] filePaths= filePathIn.listFiles();
      //获取所有文件路径
      for (File filepath:filePaths) {
        List<String> list = new ArrayList<String>();
        //  filePath :"D:\\ImageMatch\\S43_18_Insert\\18_210304_113962_210367_114025";
        String filePath = filepath.getAbsolutePath();
        System.out.println("下个文件路径："+filepath);
        //遍历此路径下的所有jpg文件
        list = ImageUtil.getListFiles(filePath,"jpg",true,list);
        String[] splits = filePath.split("\\\\");
        String[] strings = splits[splits.length-1].split("_");
        int zoom = Integer.valueOf(strings[0]);
        /**
         * 最小原子不同对应最大偏移量不同
         *
         * */

        //int MinAtom = 12;
        int Xstart = Integer.valueOf(strings[1]);
        int Ystart =  Integer.valueOf(strings[2]);
        for (int i=0;i<list.size();i++ ) {

          File file = new File(list.get(i));
          boolean flag =  ImageUtil.TestColor(file.getAbsolutePath());
          //不是纯色
          if(!flag){

            InputStream inputStream = new FileInputStream(file);

            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0 ,bytes.length);

            System.out.println(file.getAbsolutePath()+"======="+file.getName());

            String filename = file.getName().substring(0,file.getName().length()-4);
            String[] Zoom_X_Y = filename.split("-");
            System.out.println(Zoom_X_Y[0]+"==="+Zoom_X_Y[1]+"==="+Zoom_X_Y[2]);
            int Zoffset = Integer.parseInt(Zoom_X_Y[0]);
            int Xoffset = Integer.valueOf(Zoom_X_Y[1]);
            int Yoffset = Integer.valueOf(Zoom_X_Y[2]);
            double divisor = 0.0;
            int tileX = 0;
            int tileY = 0;
            if(zoom ==18){//最小原子是13  64X64
              switch (Zoffset){
                //对应18级
                case 6:
                  divisor = Math.pow(2,(6-Zoffset));
                  tileX =(int) Math.ceil(Xstart/divisor) + Xoffset;
                  tileY =(int) Math.ceil(Ystart/divisor) + Yoffset;
                  mapPojo.setType(47626774);
                  mapPojo.setZoom(18);
                  mapPojo.setX(tileX);
                  mapPojo.setY(tileY);
                  mapPojo.setTile(bytes);
                  imageMatchDao.SaveImageMergeInfo(mapPojo);
                  break;
                //对应17级
                case 5:
                  divisor = Math.pow(2,(6-Zoffset));
                  tileX =(int) Math.ceil(Xstart/divisor) + Xoffset;
                  tileY =(int) Math.ceil(Ystart/divisor) + Yoffset;
                  mapPojo.setType(47626774);
                  mapPojo.setZoom(17);
                  mapPojo.setX(tileX);
                  mapPojo.setY(tileY);
                  mapPojo.setTile(bytes);
                  imageMatchDao.SaveImageMergeInfo(mapPojo);
                  break;
                //对应16级
                case 4:
                  divisor = Math.pow(2,(6-Zoffset));
                  tileX =(int) Math.ceil(Xstart/divisor) + Xoffset;
                  tileY =(int) Math.ceil(Ystart/divisor) + Yoffset;
                  mapPojo.setType(47626774);
                  mapPojo.setZoom(16);
                  mapPojo.setX(tileX);
                  mapPojo.setY(tileY);
                  mapPojo.setTile(bytes);
                  imageMatchDao.SaveImageMergeInfo(mapPojo);
                  break;
                //对应15级
                case 3:
                  divisor = Math.pow(2,(6-Zoffset));
                  tileX =(int) Math.ceil(Xstart/divisor) + Xoffset;
                  tileY =(int) Math.ceil(Ystart/divisor) + Yoffset;
                  mapPojo.setType(47626774);
                  mapPojo.setZoom(15);
                  mapPojo.setX(tileX);
                  mapPojo.setY(tileY);
                  mapPojo.setTile(bytes);
                  imageMatchDao.SaveImageMergeInfo(mapPojo);
                  break;
                default:break;
              }

            }else if(zoom ==14){ //最小原子是10 16X16
              switch (Zoffset) {
                //对应14级
                case 4:
                  divisor = Math.pow(2, (4 - Zoffset));
                  tileX = (int) Math.floor(Xstart / divisor) + Xoffset;
                  tileY = (int) Math.floor(Ystart / divisor) + Yoffset;
                  mapPojo.setType(47626774);
                  mapPojo.setZoom(14);
                  mapPojo.setX(tileX);
                  mapPojo.setY(tileY);
                  mapPojo.setTile(bytes);
                  imageMatchDao.SaveImageMergeInfo(mapPojo);

                  break;
              }
            }else if(zoom ==13){ //最小原子是7 1X1
              switch (Zoffset) {
                //对应13级
                case 6:
                  divisor = Math.pow(2, (6 - Zoffset));
                  tileX = (int) Math.floor(Xstart / divisor) + Xoffset;
                  tileY = (int) Math.floor(Ystart / divisor) + Yoffset;
                  mapPojo.setType(47626774);
                  mapPojo.setZoom(13);
                  mapPojo.setX(tileX);
                  mapPojo.setY(tileY);
                  mapPojo.setTile(bytes);
                  imageMatchDao.SaveImageMergeInfo(mapPojo);

                  break;

              }
            } else if(zoom ==12){ //最小原子是7 1X1
              switch (Zoffset) {
                //对应12级
                case 5:
                  divisor = Math.pow(2, (5 - Zoffset));
                  tileX = (int) Math.floor(Xstart / divisor) + Xoffset;
                  tileY = (int) Math.floor(Ystart / divisor) + Yoffset;
                  mapPojo.setType(47626774);
                  mapPojo.setZoom(12);
                  mapPojo.setX(tileX);
                  mapPojo.setY(tileY);
                  mapPojo.setTile(bytes);
                  imageMatchDao.SaveImageMergeInfo(mapPojo);

                  break;

              }
            }else if(zoom ==11){ //最小原子是7 1X1
              switch (Zoffset) {
                //对应10级
                case 4:
                  divisor = Math.pow(2, (4 - Zoffset));
                  tileX = (int) Math.floor(Xstart / divisor) + Xoffset;
                  tileY = (int) Math.floor(Ystart / divisor) + Yoffset;
                  mapPojo.setType(47626774);
                  mapPojo.setZoom(11);
                  mapPojo.setX(tileX);
                  mapPojo.setY(tileY);
                  mapPojo.setTile(bytes);
                  imageMatchDao.SaveImageMergeInfo(mapPojo);

                  break;

              }
            } else if(zoom ==10){ //最小原子是7 1X1
              switch (Zoffset) {
                //对应10级
                case 3:
                  divisor = Math.pow(2, (3 - Zoffset));
                  tileX = (int) Math.floor(Xstart / divisor) + Xoffset;
                  tileY = (int) Math.floor(Ystart / divisor) + Yoffset;
                  mapPojo.setType(47626774);
                  mapPojo.setZoom(10);
                  mapPojo.setX(tileX);
                  mapPojo.setY(tileY);
                  mapPojo.setTile(bytes);
                  imageMatchDao.SaveImageMergeInfo(mapPojo);

                  break;

              }
            }else if(zoom ==9){ //最小原子是7 1X1
              switch (Zoffset) {
                //对应10级
                case 2:
                  divisor = Math.pow(2, (2 - Zoffset));
                  tileX = (int) Math.floor(Xstart / divisor) + Xoffset;
                  tileY = (int) Math.floor(Ystart / divisor) + Yoffset;
                  mapPojo.setType(47626774);
                  mapPojo.setZoom(9);
                  mapPojo.setX(tileX);
                  mapPojo.setY(tileY);
                  mapPojo.setTile(bytes);
                  imageMatchDao.SaveImageMergeInfo(mapPojo);

                  break;

              }
            }

            System.out.println("总数："+list.size()+"===下标："+i);

          }


        }

        System.out.println("瓦片总数："+list.size());
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}


