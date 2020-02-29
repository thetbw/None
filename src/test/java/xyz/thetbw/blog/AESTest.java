package xyz.thetbw.blog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.thetbw.blog.util.AESEncodeUtils;
import xyz.thetbw.blog.util.EncodeUtils;

@SpringBootTest
public class AESTest {



    @Test
    public void test() throws Exception{
        String o = "富强民主文明和谐dfasfsafdwqwqerioqdjsjadfsjdjhjsdjhfhjsdjfsjabxzbcbznczBXjchkjsadkhlsaklfiioqeioiowr";
        String pass = "test";
        byte[] r = AESEncodeUtils.getInstance().encrypt(o,pass);
        String v = EncodeUtils.getOurInstance().parseByte2HexStr(r);
        System.out.println(v);
        byte[] r2 = AESEncodeUtils.getInstance().decrypt(EncodeUtils.getOurInstance().parseHexStr2Byte(v),pass);
        System.out.println(new String(r2));
    }
}
