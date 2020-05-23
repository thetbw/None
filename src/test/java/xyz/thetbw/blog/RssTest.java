package xyz.thetbw.blog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.thetbw.blog.util.RssUtil;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@SpringBootTest
public class RssTest {

    @Test
    public void test(){
        RssUtil util = RssUtil.newInstance();
        RssUtil.Channel channel = util.getChannel();
        channel.setTitle("黑羽");
        channel.setLink("http://thetbw.xyz");
        channel.setDescription("黑羽的个人网站");
        List<RssUtil.Item> items = new ArrayList<>();
        for (int i=1;i<10;i++){
            RssUtil.Item item = new RssUtil.Item();
            item.setTitle("文章"+i);
            item.setLink("http://thetbw.xyz/"+i);
            item.setDescription("这是我的第"+i+"个文章");
            items.add(item);
        }
        channel.setItem(items);

        String xml = util.generateXML();
        System.out.println(xml);



    }
}
