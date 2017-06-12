import com.signalway.highway.entity.MapPojo;
import com.signalway.highway.service.ImageMatchService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ZhangGang on 2017/5/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-servlet.xml","classpath:spring/springContent.xml"})
public class TestImageInsert {

    @Autowired
    ImageMatchService imageMatchService;

    @Before
    public void beforTest(){
        System.out.println("进入测试");

    }
    @After
    public  void afterTest(){


        System.out.println("测试结束");
    }

    @Test
    public void UpdateImage(){

        long startTime = System.currentTimeMillis();//获取当前时间



        MapPojo mapPojo = new MapPojo();

        try {

          //  File filePathIn = new File("D:\\ImageMatch\\S43_18_Insert");
            File filePathIn = new File("D:\\ImageMatch\\Test");
            File[] filePaths= filePathIn.listFiles();
            //获取所有文件路径
            for (File filepath:filePaths) {
                List<String> list = new ArrayList<String>();
               //  filePath :"D:\\ImageMatch\\S43_18_Insert\\18_210304_113962_210367_114025";
                String filePath = filepath.getAbsolutePath();
                System.out.println("下个文件路径："+filepath);
                    //遍历此路径下的所有jpg文件
                    list = getListFiles(filePath,"jpg",true,list);
                    String[] splits = filePath.split("\\\\");
                    String[] strings = splits[splits.length-1].split("_");
                    int zoom = Integer.valueOf(strings[0]);
                   /**
                    * 最小原子不同对应最大偏移量不同
                    *
                    * */

                    int MinAtom = 12;
                    int Xstart = Integer.valueOf(strings[1]);
                    int Ystart =  Integer.valueOf(strings[2]);
                    for (int i=0;i<list.size();i++ ) {

                        File file = new File(list.get(i));
                       /* TeatPictureColorJudge teatPictureColorJudge = new TeatPictureColorJudge();
                        teatPictureColorJudge.TestColor(file.getAbsolutePath());*/
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
                        if(zoom ==18&&MinAtom==12){//最小原子是12  64X64
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
                                imageMatchService.SaveImageMergeInfo(mapPojo);
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
                                imageMatchService.SaveImageMergeInfo(mapPojo);
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
                                imageMatchService.SaveImageMergeInfo(mapPojo);
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
                                imageMatchService.SaveImageMergeInfo(mapPojo);
                                break;
                                default:break;
                            }

                        }else if(zoom ==14&&MinAtom==10){ //最小原子是10 16X16
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
                                    imageMatchService.SaveImageMergeInfo(mapPojo);

                                    break;
                                //对应13级
                                case 3:
                                    divisor = Math.pow(2, (4 - Zoffset));
                                    tileX = (int) Math.floor(Xstart / divisor) + Xoffset;
                                    tileY = (int) Math.floor(Ystart / divisor) + Yoffset;
                                    mapPojo.setType(47626774);
                                    mapPojo.setZoom(13);
                                    mapPojo.setX(tileX);
                                    mapPojo.setY(tileY);
                                    mapPojo.setTile(bytes);
                                    imageMatchService.SaveImageMergeInfo(mapPojo);

                                    break;
                                //对应12级
                                case 2:
                                    divisor = Math.pow(2, (4 - Zoffset));
                                    tileX = (int) Math.floor(Xstart / divisor) + Xoffset;
                                    tileY = (int) Math.floor(Ystart / divisor) + Yoffset;
                                    mapPojo.setType(47626774);
                                    mapPojo.setZoom(12);
                                    mapPojo.setX(tileX);
                                    mapPojo.setY(tileY);
                                    mapPojo.setTile(bytes);
                                    imageMatchService.SaveImageMergeInfo(mapPojo);

                                    break;
                                //对应11级
                                case 1:
                                    divisor = Math.pow(2, (4 - Zoffset));
                                    tileX = (int) Math.floor(Xstart / divisor) + Xoffset;
                                    tileY = (int) Math.floor(Ystart / divisor) + Yoffset;
                                    mapPojo.setType(47626774);
                                    mapPojo.setZoom(11);
                                    mapPojo.setX(tileX);
                                    mapPojo.setY(tileY);
                                    mapPojo.setTile(bytes);
                                    imageMatchService.SaveImageMergeInfo(mapPojo);

                                    break;
                            }
                        }else if(zoom ==14&&MinAtom==9){ //最小原子是9 32X32
                            switch (Zoffset) {
                                //对应14级
                                case 5:
                                    divisor = Math.pow(2, (5 - Zoffset));
                                    tileX = (int) Math.floor(Xstart / divisor) + Xoffset;
                                    tileY = (int) Math.floor(Ystart / divisor) + Yoffset;
                                    mapPojo.setType(47626774);
                                    mapPojo.setZoom(14);
                                    mapPojo.setX(tileX);
                                    mapPojo.setY(tileY);
                                    mapPojo.setTile(bytes);
                                    imageMatchService.SaveImageMergeInfo(mapPojo);

                                    break;
                                //对应13级
                                case 4:
                                    divisor = Math.pow(2, (5 - Zoffset));
                                    tileX = (int) Math.floor(Xstart / divisor) + Xoffset;
                                    tileY = (int) Math.floor(Ystart / divisor) + Yoffset;
                                    mapPojo.setType(47626774);
                                    mapPojo.setZoom(13);
                                    mapPojo.setX(tileX);
                                    mapPojo.setY(tileY);
                                    mapPojo.setTile(bytes);
                                    imageMatchService.SaveImageMergeInfo(mapPojo);

                                    break;
                                //对应12级
                                case 3:
                                    divisor = Math.pow(2, (5 - Zoffset));
                                    tileX = (int) Math.floor(Xstart / divisor) + Xoffset;
                                    tileY = (int) Math.floor(Ystart / divisor) + Yoffset;
                                    mapPojo.setType(47626774);
                                    mapPojo.setZoom(12);
                                    mapPojo.setX(tileX);
                                    mapPojo.setY(tileY);
                                    mapPojo.setTile(bytes);
                                    imageMatchService.SaveImageMergeInfo(mapPojo);

                                    break;
                                //对应11级
                                case 2:
                                    divisor = Math.pow(2, (5 - Zoffset));
                                    tileX = (int) Math.floor(Xstart / divisor) + Xoffset;
                                    tileY = (int) Math.floor(Ystart / divisor) + Yoffset;
                                    mapPojo.setType(47626774);
                                    mapPojo.setZoom(11);
                                    mapPojo.setX(tileX);
                                    mapPojo.setY(tileY);
                                    mapPojo.setTile(bytes);
                                    imageMatchService.SaveImageMergeInfo(mapPojo);

                                    break;
                            }
                        }else if(zoom ==10&&MinAtom==7){ //最小原子是7 8X8
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
                                    imageMatchService.SaveImageMergeInfo(mapPojo);

                                    break;

                            }
                        }else if(zoom ==12&&MinAtom==12){ //最小原子是7 8X8
                            switch (Zoffset) {
                                //对应9级
                             /*   case 0:

                                    tileX = Xstart + Xoffset;
                                    tileY = Ystart + Yoffset;
                                    mapPojo.setType(47626774);
                                    mapPojo.setZoom(9);
                                    mapPojo.setX(tileX);
                                    mapPojo.setY(tileY);
                                    mapPojo.setTile(bytes);
                                    imageMatchService.SaveImageMergeInfo(mapPojo);

                                    break;*/
                                case 1:
                                    /*divisor = Math.pow(2, (3 - Zoffset));
                                    tileX = (int) Math.floor(Xstart / divisor) + Xoffset;
                                    tileY = (int) Math.floor(Ystart / divisor) + Yoffset;*/
                                    tileX = Xstart + Xoffset;
                                    tileY = Ystart + Yoffset;
                                    mapPojo.setType(47626774);
                                    mapPojo.setZoom(12);
                                    mapPojo.setX(tileX);
                                    mapPojo.setY(tileY);
                                    mapPojo.setTile(bytes);
                                    imageMatchService.SaveImageMergeInfo(mapPojo);

                                    break;


                            }
                        }

                        System.out.println("总数："+list.size()+"===下标："+i);
                    }

                    System.out.println("瓦片总数："+list.size());
                }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        long endTime = System.currentTimeMillis();
        System.out.println("程序运行时间："+(endTime-startTime)/1000/60+"min");
    }

    /**
     *
     * param path
     *            文件路径
     * param suffix
     *            后缀名
     * param isdepth
     *            是否遍历子目录
     * @return
     */

    public List  getListFiles( String path, String suffix,boolean isdepth, List<String> list ) {

        File file = new File(path);
        List<String> relist = listFile(file, suffix, isdepth,list);
        return relist;
    }

    public  List listFile(File f, String suffix, boolean isdepth,List<String> fileList ) {

        // fileList = new ArrayList<String>();

        // 是目录，同时需要遍历子目录
        if (f.isDirectory() && isdepth == true) {
            File[] t = f.listFiles();
            for (int i = 0; i < t.length; i++) {
                listFile(t[i], suffix, isdepth,fileList);
            }
        } else {
            String filePath = f.getAbsolutePath();

            if (suffix != null) {
                int begIndex = filePath.lastIndexOf(".");// 最后一个.(即后缀名前面的.)的索引
                String tempsuffix = "";

                if (begIndex != -1)// 防止是文件但却没有后缀名结束的文件
                {
                    tempsuffix = filePath.substring(begIndex + 1, filePath
                            .length());
                }

                if (tempsuffix.equals(suffix)) {
                    fileList.add(filePath);
                }
            } else {
                // 后缀名为null则为所有文件
                fileList.add(filePath);
            }

        }

        return fileList;
    }


}
