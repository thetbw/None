package xyz.thetbw.blog.template;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.springframework.stereotype.Component;
import xyz.thetbw.blog.data.entity.Category;

import java.util.List;

@Component
public class GetCategory  implements TemplateMethodModelEx {



    @Override
    public Object exec(List list) throws TemplateModelException {
        return null;
    }
}
