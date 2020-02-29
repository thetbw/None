package xyz.thetbw.blog.util;

import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.Document;
import org.commonmark.node.Node;
import org.commonmark.node.Text;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.ArrayList;
import java.util.Map;

/**
 * markdown工具
 */
public class MarkDownUtils {
    private static MarkDownUtils ourInstance = new MarkDownUtils();
    public static MarkDownUtils getInstance() {
        return ourInstance;
    }

    private Parser parser;
    private HtmlRenderer htmlRenderer;

    private MarkDownUtils() {
        parser = Parser.builder().build();
        htmlRenderer = HtmlRenderer.builder().attributeProviderFactory(new AttributeProviderFactory() {
            @Override
            public AttributeProvider create(AttributeProviderContext context) {
                return new LinkProvider();
            }
        }).build();
    }


    /**
     * 将markdown转换为html
     * @param markdown
     *
     * @return 转换后的html
     */
    public String markdown2Html(String markdown){
        Node node = parser.parse(markdown);
        String html = htmlRenderer.render(node);
        return html;
    }

    /**
     * 获取markdown除了标记外的普通字符
     * @return
     */
    public String getMarkdownPlainText(String markdown){
        Node node = parser.parse(markdown);
        TextVisitor visitor = new TextVisitor();
        node.accept(visitor);
        return visitor.getText();
    }


    /**
     * 访问节点中的文本
     */
    private class TextVisitor extends AbstractVisitor{
        private StringBuilder stringBuilder=new StringBuilder();
        private int line=0;

        @Override
        public void visit(Text text) {
            super.visit(text);
            stringBuilder.append(text.getLiteral()).append("\n");
            line++;
        }

        public String getText(){
            if (line>0)
                stringBuilder.deleteCharAt(stringBuilder.length()-1);
            return stringBuilder.toString();
        }
    }

    /**
     * 修改默认链接的显示方式
     */
    private class LinkProvider implements AttributeProvider{
        @Override
        public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
            attributes.put("target","_blank");
        }
    }
}
