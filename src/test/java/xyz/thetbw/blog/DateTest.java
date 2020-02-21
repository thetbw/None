package xyz.thetbw.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.thetbw.blog.service.BrowseService;

@SpringBootTest
public class DateTest {


    @Autowired
    BrowseService browseService;

    @Test
    public void test(){
        browseService.getTodayTime();
        browseService.getTodayTime();
    }
}
