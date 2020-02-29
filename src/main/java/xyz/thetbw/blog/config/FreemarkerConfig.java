package xyz.thetbw.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import xyz.thetbw.blog.data.constant.ModelKey;
import xyz.thetbw.blog.template.*;

import javax.annotation.PostConstruct;

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
        configuration.setSharedVariable(ModelKey.F_INFO, getBlogInfo);
        configuration.setSharedVariable(ModelKey.F_PROPERTY, getBlogProperty);
        configuration.setSharedVariable(ModelKey.F_THEME,getThemeInfo);
        configuration.setSharedVariable(ModelKey.F_USER,getUserInfo);
        configuration.setSharedVariable(ModelKey.F_PAGE,getPages);
        configuration.setSharedVariable(ModelKey.U_TIME, timeTrans);
        configuration.setSharedVariable(ModelKey.U_RANDOM,new TemplateUtils.Random());
    }
}
