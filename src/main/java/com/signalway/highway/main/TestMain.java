package com.signalway.highway.main;

import com.signalway.highway.service.ImageMatchService;
import com.signalway.highway.util.ImageUtil;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.sun.tools.doclint.Entity.image;

/**
 * Created by ZhangGang on 2017/12/20.
 */
public class TestMain {


    protected final SimpleDateFormat sdf_min = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public void test() throws IOException {
/*
       *//* BeanFactory context = new ClassPathXmlApplicationContext("spring/springContent.xml");
        ImageMatchService imageMatchService = (ImageMatchService)context.getBean("imageMatchService");
        imageMatchService.CreateTempTable("testTempTable"+(int)((Math.random()*9+1)*10000));*//*
       // System.out.println(sdf_min.format(new Date()));
        Pattern pattern = Pattern.compile("^Java.*");
        Matcher matcher = pattern.matcher("Javajavaad");
        boolean b= matcher.matches();
        //当条件满足时，将返回true，否则返回false
        System.out.println(b);*/

        /**
         * 读取一张图片的RGB值
         *
         * @throws Exception
         */
        /*String image = "D:\\ImageMatch\\guangxi_s31s40\\S31_S40_18_Insert\\18_210912_113920_210975_113983\\TileGroup0\\1-0-0.jpg";
        System.out.println(ImageUtil.isSimpleColorImg(image,1));*/
        Map<String,List> map = new HashMap<>();
        List<String> list  = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        map.put("001",list);
        List object = new ArrayList<>();
        object.add("001");
        List aa = map.get(object.get(0));
        aa.forEach(System.out::println);
        /*int[] rgb = new int[3];
        File file = new File(image);
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int width = bi.getWidth();
        int height = bi.getHeight();
        int minx = bi.getMinX();
        int miny = bi.getMinY();
        System.out.println("width=" + width + ",height=" + height + ".");
        System.out.println("minx=" + minx + ",miniy=" + miny + ".");
        for (int i = minx; i < width; i++) {
            for (int j = miny; j < height; j++) {
                int pixel = bi.getRGB(i, j); // 下面三行代码将一个数字转换为RGB数字
                rgb[0] = (pixel & 0xff0000) >> 16;
                rgb[1] = (pixel & 0xff00) >> 8;
                rgb[2] = (pixel & 0xff);
                System.out.println("i=" + i + ",j=" + j + ":(" + rgb[0] + ","
                        + rgb[1] + "," + rgb[2] + ")");
            }
        }*/
    }

    public static void main(String[] args) {

        TestMain tt = new TestMain();
        try {
            tt.test();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
