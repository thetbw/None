package xyz.thetbw.blog.markdown;

import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.Text;

public class TextAbstractVisitor extends AbstractVisitor {

    @Override
    public void visit(Text text) {
        System.out.println(text.getLiteral());
        super.visit(text);
    }
}
