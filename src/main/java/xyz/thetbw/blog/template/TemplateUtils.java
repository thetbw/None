package xyz.thetbw.blog.template;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;
import freemarker.template.TemplateScalarModel;
import xyz.thetbw.blog.data.constant.ConstValue;
import xyz.thetbw.blog.util.ReflectUtils;

import java.util.List;

/**
 * 模板工具函数集
 */
public class TemplateUtils {

    public static class Random implements TemplateMethodModelEx{
        @Override
        public Object exec(List list) throws TemplateModelException {
            if (list.size()==0) return new TemplateModelException("错误的参数");
            TemplateNumberModel arg1 = (TemplateNumberModel) list.get(0);
            TemplateNumberModel arg2 = (TemplateNumberModel) list.get(1);
            TemplateNumberModel arg3=null;
            if (list.size()==3)
                arg3 = (TemplateNumberModel) list.get(2);
            if (arg1.getAsNumber().intValue()==0){
                int v2 = arg2.getAsNumber().intValue();
                if (arg3!=null){
                    int v3 = arg3.getAsNumber().intValue();
                    double r = Math.random()*(v3+1)+v2;
                    return Math.floor(r);
                }else {
                    double r = Math.random()*(v2+1);
                    return Math.floor(r);
                }
            }else {
                double v2 = arg2.getAsNumber().doubleValue();
                if (arg3!=null){
                    double v3 = arg3.getAsNumber().doubleValue();
                    double r = Math.random()*v3+v2;
                    return r;
                }else {
                    double r = Math.random()*v2;
                    return r;
                }
            }
        }
    }
}
