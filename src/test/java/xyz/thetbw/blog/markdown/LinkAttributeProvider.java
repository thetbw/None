package xyz.thetbw.blog.markdown;

import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.node.Text;
import org.commonmark.renderer.html.AttributeProvider;

import java.util.Map;

public class LinkAttributeProvider implements AttributeProvider {

    @Override
    public void setAttributes(Node node, String s, Map<String, String> map) {
        if (node instanceof Link){
            map.replace("href","http://asas");
        }

    }
}
