package xyz.thetbw.blog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.thetbw.blog.data.constant.ConstValue;

import java.io.File;

@SpringBootTest
public class ThemeTest {

    @Test
    public void getThemes(){
        File file = new File(ConstValue.THEMES_PATH);
//        if (!file.exists()&&!file.isDirectory())
//            throw new RuntimeException("目录不存在");
        File[] files = file.listFiles();
        for (File f:files){
            System.out.println(f.getName());
        }
    }
}
