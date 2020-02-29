package xyz.thetbw.blog.template;

import freemarker.template.SimpleNumber;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

@Component
public class TimeTrans implements TemplateMethodModelEx {
    @Override
    public Object exec(List list) throws TemplateModelException {
        if (list.size()<1) return "";
        SimpleNumber simpleNumber=null;
        try {
            simpleNumber = (SimpleNumber) list.get(0);
        }catch (Exception e){
            return "";
        }
        long time =simpleNumber.getAsNumber().longValue();
        DateFormat format = DateFormat.getDateInstance();
        return format.format(new Date(time));
    }
}
