package xyz.thetbw.blog.template;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.thetbw.blog.service.PageService;

import java.util.List;

@Component
public class GetPages implements TemplateMethodModelEx {

    @Autowired
    PageService pageService;

    @Override
    public Object exec(List list) throws TemplateModelException {
        return pageService.getPages();
    }
}
