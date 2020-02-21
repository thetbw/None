package xyz.thetbw.blog;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication()
@EnableTransactionManagement //开启事务注解支持
public class MyBlogApplication {
    private static final Logger LOG = LoggerFactory.getLogger(MyBlogApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MyBlogApplication.class,args);
        LOG.info("服务器已启动");
        LOG.info("初次启动请访问 /install 进行后续配置");
//        SpringApplication app = new SpringApplication(MyBlogApplication.class);
//        app.addInitializers(new InitApp());
//
//        app.run(args);
    }

}
