package com.signalway.highway.util;

import com.alibaba.druid.util.StringUtils;
import com.signalway.highway.entity.MapQueryPojo;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Created by ZhangGang on 2017/6/19.
 */
public class ImageUtil {

    public static void TestMapPrint( MapQueryPojo mapQueryPojo )  {
        Properties filePathInfo = PropertiesUtils.load("conf/filepath.properties");
        //读取临时文件
        File file = new File(filePathInfo.getProperty("filepath.temp"));
        File [] files = file.listFiles();
        try{
            Image image = mergeList(files,mapQueryPojo);
            String mergeImageName = mapQueryPojo.getZoom()+"_"+mapQueryPojo.getXstart()+"_"+mapQueryPojo.getYstart()+"_"+mapQueryPojo.getXend()+"_"+mapQueryPojo.getYend();
            //可以是jpg,png,gif格式
            String strPath = filePathInfo.getProperty("filepath.Merge");
            File filepath = new File(strPath);
            if(!filepath.exists()){
                filepath.mkdirs();
            }
            File w2 = new File(filePathInfo.getProperty("filepath.Merge")+mergeImageName+"."+filePathInfo.getProperty("filepath.type"));
            //不管输出什么格式图片，此处不需改动
            ImageIO.write((RenderedImage) image, filePathInfo.getProperty("filepath.type"), w2);
        }catch (IOException ex){
            //删除临时文件
            for (File file1 : files) {
                file1.delete();
            }
            ex.printStackTrace();
        }finally {
            //删除临时文件
            for (File file1 : files) {
                file1.delete();
            }
        }

    }

    /*拼接图片*/
    private static Image mergeList(File[] files, MapQueryPojo mapQueryPojo) throws IOException {
        int Xnumber = (mapQueryPojo.getXend() - mapQueryPojo.getXstart() + 1);
        int Ynumber = (mapQueryPojo.getYend() - mapQueryPojo.getYstart() + 1);
        int width = Xnumber * 256;
        int height = Ynumber * 256;
        int Xwidth = 0;
        int Yheiht = 0;
        Properties filePathInfo = PropertiesUtils.load("conf/filepath.properties");
        BufferedImage merged = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = (Graphics2D) merged.getGraphics();
        try {
            if ("png".equalsIgnoreCase(filePathInfo.getProperty("filepath.type"))) {
                // ----------  增加下面的代码使得背景透明  -----------------
                merged = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
                g2d.dispose();
                g2d = merged.createGraphics();
                // ----------  背景透明代码结束  -----------------
            }
            System.out.println("图片拼接中，请稍等...");
            System.out.println("图片拼接结果路径："+filePathInfo.getProperty("filepath.Merge"));
            for (File file : files) {
                Image image = ImageIO.read(file);
                if ("png".equalsIgnoreCase(filePathInfo.getProperty("filepath.type"))) {
                    //如果拼图是png格式才执行
                    //纯黑色的图片替换成png图片
                    if (isBlackColorImg((BufferedImage) image, 1)) {
                        File pngFile = new File(filePathInfo.getProperty("filepath.type.path"));
                        image = ImageIO.read(pngFile);
                    }
                }
                String filePath = file.getAbsolutePath();
                //遍历此路径下的所有jpg文件
                String[] splits = filePath.split("\\\\");
                String[] strings = splits[splits.length - 1].split("_");
                Yheiht = (Integer.valueOf(strings[1]) - mapQueryPojo.getYstart()) * 256;
                Xwidth = (Integer.valueOf(strings[2].substring(0, strings[2].length() - 4)) - mapQueryPojo.getXstart()) * 256;
                g2d.drawImage(image, Xwidth, Yheiht, null);
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
        boolean bb = false;
        try {
            bb = isSimpleColorImg(imgPath,1);
        } catch (Exception e) {
            System.out.println("判断图片颜色出错！");
            e.printStackTrace();
        }
        return bb;

    }


    public  static boolean isSimpleColorImg(String imgPath,float percent) throws IOException {
        Properties filePathInfo = PropertiesUtils.load("conf/filepath.properties");
        BufferedImage src= ImageIO.read(new File(imgPath));
        int[] rgb = new int[3];
        int height=src.getHeight();
        int width=src.getWidth();
        int count=0,pixTemp=0,pixel=0;
        boolean flag = false;
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                pixel=src.getRGB(i, j);
                rgb[0] = (pixel & 0xff0000) >> 16;
                rgb[1] = (pixel & 0xff00) >> 8;
                rgb[2] = (pixel & 0xff);

                if(rgb[0]==Integer.valueOf(filePathInfo.getProperty("filepath.R"))&&
                        rgb[1]==Integer.valueOf(filePathInfo.getProperty("filepath.G"))&&
                        rgb[2]==Integer.valueOf(filePathInfo.getProperty("filepath.B")))  //如果上一个像素点和这个像素点颜色一样的话，就判定为同一种颜色
                    count++;
                else
                    count=0;
                if((float)(count+1)/(height*width)>=percent) //如果连续相同的像素点大于设定的百分比的话，就判定为是纯色的图片
                    flag = true;
            }
        }
        return flag;
    }


/*
* 判断图片是否是纯黑色
* */

    public  static boolean isBlackColorImg(BufferedImage src,float percent) throws IOException {
        Properties filePathInfo = PropertiesUtils.load("conf/filepath.properties");
     //   BufferedImage src= ImageIO.read(new File(imgPath));
        int[] rgb = new int[3];
        int height=src.getHeight();
        int width=src.getWidth();
        int count=0,pixTemp=0,pixel=0;
        boolean flag = false;
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                pixel=src.getRGB(i, j);
                rgb[0] = (pixel & 0xff0000) >> 16;
                rgb[1] = (pixel & 0xff00) >> 8;
                rgb[2] = (pixel & 0xff);

                if(rgb[0]==0&& rgb[1]==0&& rgb[2]==0)  //如果上一个像素点和这个像素点颜色一样的话，就判定为同一种颜色
                    count++;
                else
                    count=0;
                if((float)(count+1)/(height*width)>=percent) //如果连续相同的像素点大于设定的百分比的话，就判定为是纯色的图片
                    flag = true;
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

    public static List<String> getListFiles(String path, String suffix, boolean isdepth, List<String> list ) {

        File file = new File(path);
        List<String> relist = listFile(file, suffix, isdepth,list);
        return relist;
    }

    public static List<String> listFile(File f, String suffix, boolean isdepth, List<String> fileList ) {

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


    /**
     * 读取报表
     */
    public static List<MapQueryPojo> readReport(InputStream inp) {

        List<MapQueryPojo> computerList = new ArrayList<MapQueryPojo>();

        try {

            String cellStr = null;

            Workbook wb = WorkbookFactory.create(inp);

            Sheet sheet = wb.getSheetAt(0);// 取得第一个sheets

            //从第二行开始读取数据
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {


                MapQueryPojo deploymentCarPojo = new MapQueryPojo();
                MapQueryPojo addDeploymentCarPojo = new MapQueryPojo();

                Row row = sheet.getRow(i); // 获取行(row)对象
                System.out.println(row);
                if (row == null) {
                    // row为空的话,不处理
                    continue;
                }

                for (int j = 0; j < row.getLastCellNum(); j++) {

                    Cell cell = row.getCell(j); // 获得单元格(cell)对象

                    // 转换接收的单元格
                    if(cell != null){
                        cellStr = ConvertCellStr(cell, cellStr);
                        // 将单元格的数据添加至一个对象
                        if(!StringUtils.isEmpty(cellStr)){

                            addDeploymentCarPojo = addingComputer(j, deploymentCarPojo, cellStr);
                        }
                    }
                }

                if(addDeploymentCarPojo.getRoadCode() != null){

                    // 将添加数据后的对象填充至list中
                    computerList.add(addDeploymentCarPojo);

                }

            }

        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inp != null) {
                try {
                    inp.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {

            }
        }
        return computerList;

    }


    /**
     * 把单元格内的类型转换至String类型
     */
    public static String ConvertCellStr(Cell cell, String cellStr) {

        switch (cell.getCellType()) {

            case Cell.CELL_TYPE_STRING:
                // 读取String
                cellStr = cell.getStringCellValue().toString();
                break;

            case Cell.CELL_TYPE_BOOLEAN:
                // 得到Boolean对象的方法
                cellStr = String.valueOf(cell.getBooleanCellValue());
                break;

            case Cell.CELL_TYPE_NUMERIC:

                // 先看是否是日期格式
                if (DateUtil.isCellDateFormatted(cell)) {

                    // 读取日期格式
                    Date d = cell.getDateCellValue();

                    DateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    cellStr = formater.format(d);
                    // cellStr = cell.getDateCellValue().toString();

                } else {

                    // 读取数字
                    DecimalFormat df = new DecimalFormat("0");
                    cellStr = df.format(cell.getNumericCellValue());
                    //cellStr = String.valueOf(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_BLANK:
                cellStr = null;
                break;
        }

        return cellStr;
    }

    /**
     * 获得单元格的数据添加至DeploymentCarPojo
     *
     * @param j
     *            列数
     * @param deploymentCarPojo
     *            添加对象
     * @param cellStr
     *            单元格数据
     * @return
     */
    public static MapQueryPojo addingComputer(int j,MapQueryPojo deploymentCarPojo, String cellStr) {
        switch (j) {
            case 0:
                deploymentCarPojo.setType(Integer.parseInt(cellStr));
                break;
            case 1:
                deploymentCarPojo.setZoom(Integer.parseInt(cellStr));
                break;
            case 2:
                deploymentCarPojo.setXstart(Integer.parseInt(cellStr));
                break;
            case 3:
                deploymentCarPojo.setYstart(Integer.parseInt(cellStr));
                break;
            case 4:
                deploymentCarPojo.setXend(Integer.parseInt(cellStr));
                break;
            case 5:
                deploymentCarPojo.setYend(Integer.parseInt(cellStr));
                break;
            case 6:
                deploymentCarPojo.setRoadCode(cellStr);
                break;
        }

        return deploymentCarPojo;
    }
}
