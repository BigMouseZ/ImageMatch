import com.mysql.jdbc.Blob;
import com.signalway.highway.dao.ImageMatchDao.ImageMatchDao;
import com.signalway.highway.entity.MapPojo;
import com.signalway.highway.entity.MapQueryPojo;
import com.signalway.highway.service.ImageMatchService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.applet.Applet;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhangGang on 2017/5/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-servlet.xml","classpath:spring/springContent.xml"})

public class TestImageMatch {

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
    public  void Test() throws SQLException {
        System.out.println("开始测试");
        int i =0;

                List<MapQueryPojo> mapQueryPojoList =null;
                //启用时放开
                 MapQueryPojo listQueryPojo = new MapQueryPojo();
                //根据级别、高速编号获取瓦块
                listQueryPojo.setZoom(14);
                listQueryPojo.setRoadCode("S60");

              mapQueryPojoList =   imageMatchService.ImageMergeInfoList(listQueryPojo);
        for (MapQueryPojo mapQueryPojo:mapQueryPojoList) {

            List<MapPojo> list = imageMatchService.ImageMatchBlobList(mapQueryPojo);

            byte[] bbcc = null;
            try {

                for (MapPojo mapPojo:list) {

                    bbcc = (byte[]) mapPojo.getTile();
                    ++i;
                    System.out.println(bbcc);
                    ByteArrayInputStream bais = new ByteArrayInputStream(bbcc);
                    BufferedImage bi1 =ImageIO.read(bais);
                    //创建临时文件
                    File w2 = new File("D://ImageMatch//tempImage//"+mapPojo.getZoom()+"_"+mapPojo.getY()+"_"+mapPojo.getX()+".jpg");//可以是jpg,png,gif格式
                    ImageIO.write(bi1, "jpg", w2);//不管输出什么格式图片，此处不需改动
                }
                System.out.println("总瓦片数："+i);
                //拼接图片
                TestMapPrint(mapQueryPojo);

            } catch (IOException e) {

                e.printStackTrace();
            }

        }




    }

    public void TestMapPrint( MapQueryPojo mapQueryPojo)  {
        //读取临时文件
        File file = new File("D:\\ImageMatch\\tempImage");
        File [] files = file.listFiles();
        List<Image> listImage = new ArrayList<Image>();
        try{

        for (int i = 0; i < files.length; i++)
        {
            listImage.add(ImageIO.read(files[i]));
        }

       Image image = mergeList(listImage,mapQueryPojo);
        String mergeImageName = mapQueryPojo.getZoom()+"_"+mapQueryPojo.getXstart()+"_"+mapQueryPojo.getYstart()+"_"+mapQueryPojo.getXend()+"_"+mapQueryPojo.getYend();
        //S43高速_18级别
       // File w2 = new File("D://ImageMatch//mergeImage//"+mergeImageName+".jpg");//可以是jpg,png,gif格式
        //S43高速_14级别
         File w2 = new File("D://ImageMatch//S60_14_mergeImage//"+mergeImageName+".jpg");//可以是jpg,png,gif格式
        //S60高速_18级别
        //File w2 = new File("D://ImageMatch//S60_mergeImage//"+mergeImageName+".jpg");//可以是jpg,png,gif格式

        ImageIO.write((RenderedImage) image, "jpg", w2);//不管输出什么格式图片，此处不需改动
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

    public Image mergeList(List<Image> imageList, MapQueryPojo mapQueryPojo) {
        int Xnumber = (mapQueryPojo.getXend()-mapQueryPojo.getXstart()+1);
        int Ynumber = (mapQueryPojo.getYend()-mapQueryPojo.getYstart()+1);
        int width = Xnumber*256;
        int height = Ynumber*256;
        int Xwidth = 0;
        int Yheiht = 0;
        BufferedImage merged = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) merged.getGraphics();
        for (int i=0;i<imageList.size();i++){

            g.drawImage(imageList.get(i), Xwidth, Yheiht, null);
            Xwidth = Xwidth+256;
            if((i+1)%Xnumber==0){
                Xwidth = 0;
                Yheiht = Yheiht +256;

            }

        }

        return merged;
    }




    @Test
    public void STest(){

        byte [] bbcc = null;
        MapPojo mapPojo = imageMatchService.SelectTest();

        bbcc = (byte[]) mapPojo.getTile();
        System.out.println(bbcc);
        ByteArrayInputStream bais = new ByteArrayInputStream(bbcc);
        BufferedImage bi1 = null;
        try {
            bi1 = ImageIO.read(bais);
            String mergeImageName = mapPojo.getZoom()+"_"+mapPojo.getX()+"_"+mapPojo.getY();

            File w2 = new File("D://ImageMatch//TestImage//"+mergeImageName+".jpg");//可以是jpg,png,gif格式
            ImageIO.write(bi1, "jpg", w2);//不管输出什么格式图片，此处不需改动
        } catch (IOException e) {
            e.printStackTrace();
        }




    }
}
