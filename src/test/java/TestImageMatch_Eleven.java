import com.alibaba.druid.util.StringUtils;
import com.signalway.highway.entity.MapQueryPojo;
import com.signalway.highway.service.ImageMatchService;
import com.signalway.highway.util.ImageUtil;
import com.signalway.highway.util.PropertiesUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

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
    public  void ImageMerge() {
        System.out.println("开始测试");
        long startTime = System.currentTimeMillis();//获取当前时间
        MapQueryPojo listQueryPojo = new MapQueryPojo();
        //根据级别、高速编号获取瓦块
        Properties photomatch = PropertiesUtils.load("conf/photomatch.properties");
        listQueryPojo.setZoom(Integer.valueOf(photomatch.getProperty("zoom")));
        listQueryPojo.setRoadCode(photomatch.getProperty("roadcode"));
        listQueryPojo.setType(Integer.valueOf(photomatch.getProperty("type")));
        if(!StringUtils.isEmpty(photomatch.getProperty("pid"))){

            listQueryPojo.setId(Integer.valueOf(photomatch.getProperty("pid")));

        }
        //拼接图片
        imageMatchService.ImageMergeInfoList(listQueryPojo);
        long endTime = System.currentTimeMillis();
        System.out.println("程序运行时间："+(endTime-startTime)/1000/60+"min");
    }

    /*读取Excel文件入库*/
    @Test
    public  void InsertExcel() {
        System.out.println("开始测试");

        try {
            String filePath = "D:\\ImageMatch\\guangxi_g6511\\g6511.xlsx";
            InputStream input = new FileInputStream(new File(filePath));
            List<MapQueryPojo> mapQueryPojoList = ImageUtil.readReport(input);
            imageMatchService.InsertImageMergeInfoList(mapQueryPojoList);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
