package xyz.thetbw.blog.config;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import xyz.thetbw.blog.data.constant.ModelKey;
import xyz.thetbw.blog.service.PageService;
import xyz.thetbw.blog.template.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
public class FreemarkerConfig {

    @Autowired freemarker.template.Configuration configuration;

    @Autowired
    GetBlogInfo getBlogInfo;

    @Autowired
    GetBlogProperty getBlogProperty;

    @Autowired
    GetThemeInfo getThemeInfo;

    @Autowired
    GetUserInfo getUserInfo;

    @Autowired
    GetPages getPages;

    @Autowired
    TimeTrans timeTrans;

    @PostConstruct
    public void addTemplateMethod(){
        configuration.setSharedVariable(ModelKey.BLOG_INFO, getBlogInfo);
        configuration.setSharedVariable(ModelKey.BLOG_PROPERTY, getBlogProperty);
        configuration.setSharedVariable(ModelKey.THEME_INFO,getThemeInfo);
        configuration.setSharedVariable(ModelKey.USER_INFO,getUserInfo);
        configuration.setSharedVariable(ModelKey.NAV_PAGES,getPages);
        configuration.setSharedVariable(ModelKey.TIME_UTIL,timeTrans);
        configuration.setSharedVariable(ModelKey.Random,new TemplateUtils.Random());
    }
}
