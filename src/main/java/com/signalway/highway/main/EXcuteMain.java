package com.signalway.highway.main;

import com.alibaba.druid.util.StringUtils;
import com.signalway.highway.entity.MapQueryPojo;
import com.signalway.highway.service.ImageMatchService;
import com.signalway.highway.service.impl.Image;
import com.signalway.highway.util.ImageUtil;
import com.signalway.highway.util.PropertiesUtils;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
            System.out.print("输入1或2或3（1执行图片更新操作；2执行图片拼接工作；3读取Excel文件入库）：");
            Scanner scan = new Scanner(System.in);
            int intput = scan.nextInt();
            while (intput!=1&&intput!=2&&intput!=3){

                System.out.print("输入1或2或3（1执行图片更新操作；2执行图片拼接工作；3读取Excel文件入库）：");
                 scan = new Scanner(System.in);
                intput = scan.nextInt();
            }
            if(intput == 1){
                System.out.println("图片更新操作中，请稍等...");
                //图片更新操作
                Properties filePathInfo = PropertiesUtils.load("conf/filepath.properties");
                //依次执行9到18级的导入
                String filePathName = filePathInfo.getProperty("filepath.Insert");
                File filePathIn = new File(filePathName);
                File[] filePaths= filePathIn.listFiles();
                SaveImageMergeInfo(filePaths,imageMatchService);

            }else if(intput == 2){
                Properties photomatch = PropertiesUtils.load("conf/photomatch.properties");
                //图片拼接工作
                System.out.println("图片拼接执行中，请稍等...");
                MapQueryPojo QueryPojo = new MapQueryPojo();
                //根据级别、高速编号获取瓦块
                QueryPojo.setZoom(Integer.valueOf(photomatch.getProperty("zoom")));
                QueryPojo.setRoadCode(photomatch.getProperty("roadcode"));
                QueryPojo.setType(Integer.valueOf(photomatch.getProperty("type")));
                if(!StringUtils.isEmpty(photomatch.getProperty("pid"))){

                      QueryPojo.setId(Integer.valueOf(photomatch.getProperty("pid")));

                }
                //拼接图片
                imageMatchService.ImageMergeInfoList(QueryPojo);
            }else if(intput == 3){
                 /*读取Excel文件入库*/
                    System.out.println("读取文件中...");
                Properties photomatch = PropertiesUtils.load("conf/filepath.properties");
                InputStream input = new FileInputStream(new File(photomatch.getProperty("filepath.excel")));
                List<MapQueryPojo> mapQueryPojoList = ImageUtil.readReport(input);
                imageMatchService.InsertImageMergeInfoList(mapQueryPojoList);

            }
        } catch (Exception e) {
            System.out.println("无法找到配置文件:");
            e.printStackTrace();
        }finally {

        }
        long endTime = System.currentTimeMillis();
        System.out.println("程序运行时间："+(endTime-startTime)/1000/60+"min");
    }

    public static void SaveImageMergeInfo(File[] filePaths,ImageMatchService imageMatchService){

        try {
            //获取所有文件路径
            CountDownLatch latch = new CountDownLatch(filePaths.length);//线程计数器
            List<String> tempTableNameList = new ArrayList<>();
            ExecutorService executor = Executors.newFixedThreadPool(20);//4个线程，4个线程以上无区别，疑惑
            System.out.println("文件总量："+filePaths.length);
            for(File filepath:filePaths){

                executor.execute(new Image(imageMatchService,latch,filepath,tempTableNameList));
            }
            executor.shutdown();
            latch.await();
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }
    }
}
