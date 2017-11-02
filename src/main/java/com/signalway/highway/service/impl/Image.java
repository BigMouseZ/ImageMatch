package com.signalway.highway.service.impl;

import com.signalway.highway.dao.ImageMatchDao.ImageMatchDao;
import com.signalway.highway.entity.MapPojo;
import com.signalway.highway.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by ZhangGang on 2017/8/31.
 */
public class Image implements Runnable {

    ImageMatchDao imageMatchDao;
    CountDownLatch latch;
    private File filepath ;
    private int index;
    private int test;

    public Image(ImageMatchDao imageMatchDao, CountDownLatch latch, File filepath, int index) {
        this.imageMatchDao = imageMatchDao;
        this.latch = latch;
        this.filepath = filepath;
        this.index = index;
    }

    @Override
    public void run() {

            try {
                System.out.println("当前执行任务："+index);
                List<MapPojo> mapPojoList = new ArrayList<>();
                List<String> list = new ArrayList<String>();
                //  filePath :"D:\\ImageMatch\\S43_18_Insert\\18_210304_113962_210367_114025";
                String filePath = filepath.getAbsolutePath();
                System.out.println("下个文件路径："+filepath);
                //遍历此路径下的所有jpg文件
                list = ImageUtil.getListFiles(filePath,"jpg",true,list);
                String[] splits = filePath.split("\\\\");
                String[] strings = splits[splits.length-1].split("_");
                int zoom = Integer.valueOf(strings[0]);
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
                        if(zoom ==18){ //最小原子是13  64X64
                            MapPojo mapPojo = new MapPojo();
                            switch (Zoffset){
                                //对应18级
                                case 6:
                                    divisor = Math.pow(2,(6-Zoffset));
                                    mapPojo = sealMaoPojo(mapPojo,divisor,zoom,Xstart,Ystart,Xoffset,Yoffset,bytes);
                                    mapPojoList.add(mapPojo);
                                    break;
                                //对应17级
                                case 5:
                                    divisor = Math.pow(2,(6-Zoffset));
                                    mapPojo = sealMaoPojo(mapPojo,divisor,17,Xstart,Ystart,Xoffset,Yoffset,bytes);
                                    mapPojoList.add(mapPojo);
                                    break;
                                //对应16级
                                case 4:
                                    divisor = Math.pow(2,(6-Zoffset));
                                    mapPojo = sealMaoPojo(mapPojo,divisor,16,Xstart,Ystart,Xoffset,Yoffset,bytes);
                                    mapPojoList.add(mapPojo);
                                    break;
                                //对应15级
                                case 3:
                                    divisor = Math.pow(2,(6-Zoffset));
                                    mapPojo = sealMaoPojo(mapPojo,divisor,15,Xstart,Ystart,Xoffset,Yoffset,bytes);
                                    mapPojoList.add(mapPojo);
                                    break;
                                default:break;
                            }

                        }else if(zoom ==14){ //最小原子是10 16X16
                            MapPojo mapPojo = new MapPojo();
                            switch (Zoffset) {
                                //对应14级
                                case 6:
                                    divisor = Math.pow(2, (6 - Zoffset));
                                    mapPojo = sealMaoPojo(mapPojo,divisor,14,Xstart,Ystart,Xoffset,Yoffset,bytes);
                                    mapPojoList.add(mapPojo);
                                    break;
                                //对应14级
                             /*   case 5:
                                    divisor = Math.pow(2, (5 - Zoffset));
                                    mapPojo = sealMaoPojo(mapPojo,divisor,14,Xstart,Ystart,Xoffset,Yoffset,bytes);
                                    mapPojoList.add(mapPojo);
                                    break;*/
                            }
                        }else if(zoom ==13){ //最小原子是7 1X1
                            MapPojo mapPojo = new MapPojo();
                            switch (Zoffset) {
                                //对应13级
                                case 6:
                                    divisor = Math.pow(2, (6 - Zoffset));
                                    mapPojo = sealMaoPojo(mapPojo,divisor,13,Xstart,Ystart,Xoffset,Yoffset,bytes);
                                    mapPojoList.add(mapPojo);
                                    break;
                            }
                        } else if(zoom ==12){ //最小原子是7 1X1
                            MapPojo mapPojo = new MapPojo();
                            switch (Zoffset) {
                                //对应12级
                                case 5:
                                    divisor = Math.pow(2, (5 - Zoffset));
                                    mapPojo = sealMaoPojo(mapPojo,divisor,12,Xstart,Ystart,Xoffset,Yoffset,bytes);
                                    mapPojoList.add(mapPojo);
                                    break;
                            }
                        }else if(zoom ==11){ //最小原子是7 1X1
                            MapPojo mapPojo = new MapPojo();
                            switch (Zoffset) {
                                //对应11级
                                case 4:
                                    divisor = Math.pow(2, (4 - Zoffset));
                                    mapPojo = sealMaoPojo(mapPojo,divisor,11,Xstart,Ystart,Xoffset,Yoffset,bytes);
                                    mapPojoList.add(mapPojo);
                                    break;
                            }
                        } else if(zoom ==10){ //最小原子是7 1X1
                            MapPojo mapPojo = new MapPojo();
                            switch (Zoffset) {
                                //对应10级
                                case 3:
                                    divisor = Math.pow(2, (3 - Zoffset));
                                    mapPojo = sealMaoPojo(mapPojo,divisor,10,Xstart,Ystart,Xoffset,Yoffset,bytes);
                                    mapPojoList.add(mapPojo);
                                    break;
                            }
                        }else if(zoom ==9){ //最小原子是7 1X1
                            MapPojo mapPojo = new MapPojo();
                            switch (Zoffset) {
                                //对应9级
                                case 2:
                                    divisor = Math.pow(2, (2 - Zoffset));
                                    mapPojo = sealMaoPojo(mapPojo,divisor,9,Xstart,Ystart,Xoffset,Yoffset,bytes);
                                    mapPojoList.add(mapPojo);
                                    break;
                            }
                        }
                        System.out.println("总数："+list.size()+"===下标："+i);
                    }

                }
                //同步代码块
                synchronized(this) {
                    test = mapPojoList.size();
                    imageMatchDao.SaveImageMergeInfoList(mapPojoList);
                    System.out.println("=====当前线程的文件夹：" + filepath);
                    System.out.println("=====当前线程处理数据量：" + mapPojoList.size());
                    mapPojoList.clear();
                    System.out.println("瓦片总数：" + list.size());
                }

            }catch (Exception e){
                System.out.println("=====当前出错线程的文件夹===："+filepath);
                System.out.println("=====当前线程处理数据量："+test);
                e.printStackTrace();
                return;
            }

            latch.countDown();

    }
    //返回实体
    public MapPojo sealMaoPojo( MapPojo mapPojo,
                                double divisor,
                                int zoom,
                                int Xstart,
                                int Ystart ,
                                int Xoffset ,
                                int Yoffset,
                                byte[] bytes){
        int tileX = (int) Math.floor(Xstart / divisor) + Xoffset;
        int tileY = (int) Math.floor(Ystart / divisor) + Yoffset;
        mapPojo.setType(47626774);
        mapPojo.setZoom(zoom);
        mapPojo.setX(tileX);
        mapPojo.setY(tileY);
        mapPojo.setTile(bytes);
        return mapPojo;
    }
}
