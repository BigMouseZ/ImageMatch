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

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangGang on 2017/5/23.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-servlet.xml","classpath:spring/springContent.xml"})
public class TestImageMatch_Eleven {

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
        listQueryPojo.setZoom(12);
        listQueryPojo.setRoadCode("S43_S60");
       // listQueryPojo.setId(14);

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
                    BufferedImage bi1 = ImageIO.read(bais);
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
    public void TestMapPrint( MapQueryPojo mapQueryPojo )  {
       /* MapQueryPojo mapQueryPojo = new MapQueryPojo();
        mapQueryPojo.setXstart(210304);
        mapQueryPojo.setYstart(114368);
        mapQueryPojo.setXend(210431);
        mapQueryPojo.setYend(114495);
        mapQueryPojo.setZoom(18);*/
        //读取临时文件
        File file = new File("D:\\ImageMatch\\tempImage");
        File [] files = file.listFiles();

     try{

         Image image = mergeList(files,mapQueryPojo);

            String mergeImageName = mapQueryPojo.getZoom()+"_"+mapQueryPojo.getXstart()+"_"+mapQueryPojo.getYstart()+"_"+mapQueryPojo.getXend()+"_"+mapQueryPojo.getYend();
            //S43高速_18级别
             File w2 = new File("D://ImageMatch//S43_S60_12_mergeImage//"+mergeImageName+".jpg");//可以是jpg,png,gif格式
            //S43高速_14级别
            //File w2 = new File("D://ImageMatch//S60_14_mergeImage//"+mergeImageName+".jpg");//可以是jpg,png,gif格式
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

    public Image mergeList( File [] files, MapQueryPojo mapQueryPojo) throws IOException {
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

}
