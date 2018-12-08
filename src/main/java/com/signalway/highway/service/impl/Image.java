package com.signalway.highway.service.impl;

import com.signalway.highway.dao.ImageMatchDao.ImageMatchDao;
import com.signalway.highway.entity.MapPojo;
import com.signalway.highway.service.ImageMatchService;
import com.signalway.highway.util.ImageUtil;
import com.signalway.highway.util.PropertiesUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

/**
 * Created by ZhangGang on 2017/8/31.
 */
public class Image implements Runnable {
    private static final Logger logger = Logger.getLogger(Image.class);
    ImageMatchService imageMatchService;
    CountDownLatch latch;
    private File filepath ;
    List<String> tempTableNameList;

    public Image(ImageMatchService imageMatchService, CountDownLatch latch, File filepath,List<String> tempTableNameList) {
        this.imageMatchService = imageMatchService;
        this.latch = latch;
        this.filepath = filepath;
        this.tempTableNameList = tempTableNameList;
    }
    @Override
    public void run() {

        try{
            imageMatchService.SaveImageMergeInfo(filepath);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("[error]图片更新失败: "+ e);
        }finally {
            latch.countDown();
        }
    }
}
