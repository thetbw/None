package xyz.thetbw.blog.template;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import xyz.thetbw.blog.data.constant.ConstValue;
import xyz.thetbw.blog.util.ReflectUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GetBlogInfo implements TemplateMethodModelEx {



    @Override
    public Object exec(List list) throws TemplateModelException {
        if (list.size()==0) return null;
        TemplateScalarModel templateScalarModel = (TemplateScalarModel) list.get(0);
        String value = ReflectUtils.getInstance().getFieldValue(templateScalarModel.getAsString(),ConstValue.class,String.class);
        if (value!=null) return value;
        return "";
    }
}
