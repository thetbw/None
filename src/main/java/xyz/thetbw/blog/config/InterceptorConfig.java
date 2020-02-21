package xyz.thetbw.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xyz.thetbw.blog.web.interceptor.AdminInterceptor;
import xyz.thetbw.blog.web.interceptor.ArticleCountInterceptor;
import xyz.thetbw.blog.web.interceptor.BlogBrowseInterceptor;
import xyz.thetbw.blog.web.interceptor.UserInterceptor;

/**
 * 拦截器配置
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    public UserInterceptor userLoginInterceptor(){
        return new UserInterceptor();
    }

    @Bean
    public AdminInterceptor adminInterceptor(){
        return new AdminInterceptor();
    }

    @Bean
    public ArticleCountInterceptor browseCountInterceptor(){ return new ArticleCountInterceptor();}

    @Bean
    public BlogBrowseInterceptor blogBrowseInterceptor(){return new BlogBrowseInterceptor();}

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userLoginInterceptor()).order(Ordered.HIGHEST_PRECEDENCE); //登陆拦截器
        registry.addInterceptor(adminInterceptor()).addPathPatterns("/admin/**"); //admin后台拦截器
        registry.addInterceptor(browseCountInterceptor()).addPathPatterns("/article/**");
        registry.addInterceptor(blogBrowseInterceptor()).addPathPatterns("/**");
    }
}
