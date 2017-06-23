package com.signalway.highway.util;

import org.springframework.core.io.ClassPathResource;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by admin on 2017/1/9.
 */
public class PropertiesUtils {
    private static Properties properties;
    /**
     * 加载属性文件(getResourceAsStream(file)会先查找java虚拟机中是否有此文件，如果有，直接从缓存中读.因些更新不能用此方法加载)
     * @param filePath 文件路径
     * @return
     */
    public static Properties load(String filePath){
        Properties properties = new Properties();
        try {
            //InputStream oo = Object.class.getResourceAsStream(filePath);
            InputStream fs=new FileInputStream(new ClassPathResource(filePath).getFile());
            properties.load(fs);
            fs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }
    /**
     * 加载属性文件
     *
     * @param filePath 文件路径
     * @return
     */
    public static Properties loadForUpdate(String filePath) {
        properties = new Properties();
        try {
            String path = Object.class.getResource(filePath).getPath();
            InputStream fs=new FileInputStream(path);
            InputStream in = new BufferedInputStream(fs);
            properties.load(in);
            fs.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }
    /**
     * 加载磁盘中属性文件
     * @param filePath 文件路径
     * @return
     */
    public static Properties loadFromDisk(String filePath){
        properties = new Properties();
        try {
            InputStream fs=new FileInputStream(filePath);
            InputStream in =new BufferedInputStream(fs);
            properties.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }
    /**
     * 读取配置文件
     * @param key
     * @return
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * 更新配置文件
     *
     * @return
     */
    public static void updateProperty(Properties properties, String filePath, String keyname, String keyvalue) {
        try {
            properties.setProperty(keyname, keyvalue);
            String path = Object.class.getResource(filePath).getPath();
            FileOutputStream outputFile = new FileOutputStream(path);
            properties.store(outputFile, "modify");
            outputFile.flush();
            outputFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
 * 更新磁盘properties文件的键值对
 * 如果该主键已经存在，更新该主键的值；
 * 如果该主键不存在，则插入一对键值。
 * @param keyname 键名
 * @param keyvalue 键值
 */
    public static void updatePropertyToDisk(Properties properties,String filePath,String keyname,String keyvalue) {
        try {

            // 从输入流中读取属性列表（键和元素对）
            properties.setProperty(keyname, keyvalue);
            FileOutputStream outputFile = new FileOutputStream(filePath);
            properties.store(outputFile, null);
            outputFile.flush();
            outputFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
