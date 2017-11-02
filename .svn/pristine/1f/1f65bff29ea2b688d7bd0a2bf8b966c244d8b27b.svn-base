package com.signalway.highway.main;

import com.alibaba.druid.util.StringUtils;
import com.signalway.highway.entity.MapQueryPojo;
import com.signalway.highway.service.ImageMatchService;
import com.signalway.highway.util.PropertiesUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Properties;
import java.util.Scanner;

/**
 * Created by ZhangGang on 2017/10/25.
 */
public class EXcuteMain {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();//获取当前时间
        BeanFactory context = new ClassPathXmlApplicationContext("spring/springContent.xml");
        ImageMatchService imageMatchService = (ImageMatchService)context.getBean("imageMatchService");
        //图片更新入库
        try {
            System.out.print("输入1或2（1执行图片更新操作；2执行图片拼接工作）：");
            Scanner scan = new Scanner(System.in);
            int intput = scan.nextInt();
            while (intput!=1&&intput!=2){

                System.out.print("输入1或2（1执行图片更新操作；2执行图片拼接工作）：");
                 scan = new Scanner(System.in);
                intput = scan.nextInt();
            }
            if(intput == 1){
                System.out.println("执行图片更新操作");
                //图片更新操作
                imageMatchService.SaveImageMergeInfo();
            }else if(intput == 2){
                Properties photomatch = PropertiesUtils.load("conf/photomatch.properties");
                //图片拼接工作
                System.out.println("执行图片拼接工作");
                MapQueryPojo listQueryPojo = new MapQueryPojo();
                //根据级别、高速编号获取瓦块
                listQueryPojo.setZoom(Integer.valueOf(photomatch.getProperty("zoom")));
                listQueryPojo.setRoadCode(photomatch.getProperty("roadcode"));
                listQueryPojo.setType(Integer.valueOf(photomatch.getProperty("type")));
                if(!StringUtils.isEmpty(photomatch.getProperty("pid"))){

                      listQueryPojo.setId(Integer.valueOf(photomatch.getProperty("pid")));

                }
                //拼接图片
                imageMatchService.ImageMergeInfoList(listQueryPojo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("程序运行时间："+(endTime-startTime)/1000/60+"min");
    }
}
