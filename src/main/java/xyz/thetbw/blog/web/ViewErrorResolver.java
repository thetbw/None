package xyz.thetbw.blog.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import xyz.thetbw.blog.data.AppContext;
import xyz.thetbw.blog.data.constant.ConstValue;
import xyz.thetbw.blog.data.constant.ModelKey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Locale;
import java.util.Map;

/**
 * 处理视图错误
 */
@Component
public class ViewErrorResolver implements ErrorViewResolver {
    private static Logger LOG = LoggerFactory.getLogger(ViewErrorResolver.class);


    /**
     *处理视图错误，如果主题有错误对应的页面，则使用主题的页面，非则采用默认的
     */
    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request,HttpStatus status, Map<String, Object> model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addAllObjects(model);

        modelAndView.addObject("status",status.value());

        if (LOG.isDebugEnabled()){
            LOG.debug("错误status:"+status.value());
            model.forEach((s,o)->{
                LOG.debug("错误信息 "+s+":"+o);
            });
        }
        String themeName = AppContext.getInstance().theme.getThemeName();
        File codeFile = new File(ConstValue.THEMES_PATH+"/"+themeName+"/"+status.value()+".ftl");
        File errorFile = new File(ConstValue.THEMES_PATH+"/"+themeName+"/"+"error.ftl");
        if (codeFile.exists()){
            modelAndView.setViewName("/"+themeName+"/"+status.value());
            return modelAndView;
        }else if(errorFile.exists()){
            modelAndView.setViewName("/"+themeName+"/error");
            return modelAndView;
        }else {
            modelAndView.setViewName("/error");
            return modelAndView;
        }
    }
}
