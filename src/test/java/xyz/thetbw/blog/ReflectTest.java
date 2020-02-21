package xyz.thetbw.blog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.thetbw.blog.data.constant.ConstValue;
import xyz.thetbw.blog.util.ReflectUtils;

@SpringBootTest
public class ReflectTest {

    @Test
    void getFileValue(){
        String blog = ReflectUtils.getInstance().getFieldValue("BLOG_NAME", ConstValue.class,String.class);
        System.out.println(blog);
    }
}
