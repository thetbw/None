package xyz.thetbw.blog;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication(scanBasePackages = "xyz.thetbw.blog")
@EnableTransactionManagement //开启事务注解支持
@EnableAspectJAutoProxy //开启AspectJ支持
@EnableScheduling //开启计划任务支持
public class MyBlogApplication {
    private static final Logger LOG = LoggerFactory.getLogger(MyBlogApplication.class);

    public static void main(String[] args) {
        for (String arg:args){
            if (arg.equals("init")){
                LOG.info("正在初始化");
                InitApp.doInit(true);
                LOG.info("请重新启动");
                System.exit(1);
            }
        }

        SpringApplication app = new SpringApplication(MyBlogApplication.class);
        app.addInitializers(new InitApp());

        app.run(args);
        LOG.info("服务器已启动");
    }

}
