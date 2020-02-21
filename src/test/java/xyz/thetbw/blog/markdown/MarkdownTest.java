package xyz.thetbw.blog.markdown;

import com.sun.xml.internal.ws.api.ha.StickyFeature;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.thetbw.blog.util.MarkDownUtils;

@SpringBootTest
public class MarkdownTest {

    @Test
    void test1(){
        Parser parser = Parser.builder().build();
        Node document = parser.parse("**哈哈哈**\n\n[打开百度](https://baidu.com)");
        HtmlRenderer renderer = HtmlRenderer.builder().attributeProviderFactory(new AttributeProviderFactory() {
            @Override
            public AttributeProvider create(AttributeProviderContext attributeProviderContext) {
                return new LinkAttributeProvider();
            }
        }).build();
        document.accept(new TextAbstractVisitor());
        System.out.println(renderer.render(document));
    }


    @Test
    void utilTest(){
        MarkDownUtils utils = MarkDownUtils.getInstance();
        String markdown = "### 这是一个标题 \n aaa \n\n #### 这是子标题" +
                "\n [打开百度](http://www.baidu.com)";
        String text = utils.getMarkdownPlainText(markdown);
        System.out.println(text);
        System.out.println("------------------------");
        String html = utils.markdown2Html(markdown);
        System.out.println(html);

    }
}
