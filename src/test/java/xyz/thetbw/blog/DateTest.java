package xyz.thetbw.blog;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.thetbw.blog.service.BrowseService;
import xyz.thetbw.blog.util.TimeUtils;

import java.util.Date;

@SpringBootTest
public class DateTest {


    @Autowired
    BrowseService browseService;

    @Test
    public void test(){
        TimeUtils utils = TimeUtils.getInstance();
        Long n = System.currentTimeMillis();
        Long b = utils.getDayTimeRange(new Date())[0];
        Long e = utils.getDayTimeRange(new Date())[1];
        System.out.println(n);
        System.out.println(b);
        System.out.println(e);
        System.out.println(utils.format("yyyy-MM-dd:HH",n));
        System.out.println(utils.format("yyyy-MM-dd:HH",b));
        System.out.println(utils.format("yyyy-MM-dd:HH",e));
    }
}
