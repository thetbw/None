package xyz.thetbw.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.thetbw.blog.data.dao.ArticleDao;

@SpringBootTest
class MyBlogApplicationTests {

    @Autowired
    ArticleDao articleDao;

    @Test
    void contextLoads() {
        System.out.println(articleDao.getAll().toString());
    }

}
