package com.signalway.highway.util;

import com.signalway.highway.entity.MapQueryPojo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by ZhangGang on 2017/6/19.
 */
public class ImageUtil {

    public static void TestMapPrint( MapQueryPojo mapQueryPojo )  {
        Properties filePathInfo = PropertiesUtils.load("filepath.properties");
        //读取临时文件
        File file = new File(filePathInfo.getProperty("filepath.temp"));
        File [] files = file.listFiles();

        try{

            Image image = mergeList(files,mapQueryPojo);
            String mergeImageName = mapQueryPojo.getZoom()+"_"+mapQueryPojo.getXstart()+"_"+mapQueryPojo.getYstart()+"_"+mapQueryPojo.getXend()+"_"+mapQueryPojo.getYend();
            //可以是jpg,png,gif格式
            File w2 = new File(filePathInfo.getProperty("filepath.Merge.12")+mergeImageName+".jpg");
            //不管输出什么格式图片，此处不需改动
            ImageIO.write((RenderedImage) image, "jpg", w2);
        }catch (IOException ex){

            ex.printStackTrace();


        }finally {
            //删除临时文件
            for (int i = 0; i < files.length; i++)
            {
                files[i].delete();
            }

        }

    }

    public static Image mergeList( File [] files, MapQueryPojo mapQueryPojo) throws IOException {
        int Xnumber = (mapQueryPojo.getXend() - mapQueryPojo.getXstart() + 1);
        int Ynumber = (mapQueryPojo.getYend() - mapQueryPojo.getYstart() + 1);
        int width = Xnumber * 256;
        int height = Ynumber * 256;
        int Xwidth = 0;
        int Yheiht = 0;

        BufferedImage merged = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) merged.getGraphics();
        try {

            for (int i = 0; i < files.length; i++) {

                File file = files[i];
                Image image = ImageIO.read(file);
                String filePath = file.getAbsolutePath();
                //遍历此路径下的所有jpg文件
                String[] splits = filePath.split("\\\\");
                String[] strings = splits[splits.length-1].split("_");

                Yheiht = (Integer.valueOf(strings[1])-mapQueryPojo.getYstart())*256;
                Xwidth = (Integer.valueOf(strings[2].substring(0,strings[2].length()-4))-mapQueryPojo.getXstart())*256;

                g.drawImage(image, Xwidth, Yheiht, null);

            }

        } catch (Exception ex) {

            ex.printStackTrace();
        }
        return merged;
    }



    /**
     * 判断是否为纯色
     * param imgPath 图片源
     * param percent 纯色百分比，即大于此百分比为同一种颜色则判定为纯色,范围[0-1]
     * @return
     * @throws IOException
     */

    public static boolean TestColor(String imgPath ){

        long startTime = System.currentTimeMillis();//获取当前时间
        boolean bb = false;
        try {
            bb = isSimpleColorImg(imgPath,1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(bb);

        long endTime = System.currentTimeMillis();
        System.out.println("校验时间："+(endTime-startTime)+"ms");

        return bb;

    }


    public  static boolean isSimpleColorImg(String imgPath,float percent) throws IOException {
        BufferedImage src= ImageIO.read(new File(imgPath));

        int height=src.getHeight();
        int width=src.getWidth();
        int count=0,pixTemp=0,pixel=0;
        boolean flag = false;
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                pixel=src.getRGB(i, j);
                if(pixel==pixTemp) //如果上一个像素点和这个像素点颜色一样的话，就判定为同一种颜色
                    count++;
                else
                    count=0;
                if((float)(count+1)/(height*width)>=percent) //如果连续相同的像素点大于设定的百分比的话，就判定为是纯色的图片
                    flag = true;
                pixTemp=pixel;
            }
        }
        return flag;
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

    public static java.util.List getListFiles(String path, String suffix, boolean isdepth, java.util.List<String> list ) {

        File file = new File(path);
        java.util.List<String> relist = listFile(file, suffix, isdepth,list);
        return relist;
    }

    public static java.util.List listFile(File f, String suffix, boolean isdepth, java.util.List<String> fileList ) {

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
