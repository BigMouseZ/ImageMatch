import com.signalway.highway.service.ImageMatchService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by ZhangGang on 2017/5/24.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-servlet.xml","classpath:spring/springContent.xml"})
public class TeatPictureColorJudge {
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
    public void UpdateImage() throws InterruptedException {

        long startTime = System.currentTimeMillis();//获取当前时间
        //图片更新入库
       // imageMatchService.SaveImageMergeInfo();

        long endTime = System.currentTimeMillis();

        System.out.println("程序运行时间："+(endTime-startTime)/1000/60+"min");
    }

}
