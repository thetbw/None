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
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import xyz.thetbw.blog.util.FileMonitor;

import java.io.File;
import java.io.IOException;


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
            }else if (arg.equals("dev")){
                devMode();
            }
        }

        SpringApplication app = new SpringApplication(MyBlogApplication.class);
        app.addInitializers(new InitApp());
        app.run(args);
        LOG.info("服务器已启动");
    }

    public static void devMode() {
        LOG.info("正在以开发模式初始化");
        InitApp.doInit(true);
//        FileMonitor monitor = FileMonitor.getInstance();
//        File themeDir =null;
//        File adminDir=null;
//        File prop=null;
//        try {
//            themeDir = new ClassPathResource("themes").getFile();
//            adminDir = new ClassPathResource("admin").getFile();
//            prop = new ClassPathResource("application.properties").getFile();
//        }catch (IOException e){
//            LOG.error("开发模式启用失败，无法获取File对象");
//        }
//        FileMonitor.FileListener listener = new FileMonitor.FileListener() {
//            @Override
//            public void onDelete(File file) {
//                LOG.info("正在删除文件夹："+file.delete());
//                File f = new File(file.getName());
//                f.delete();
//            }
//
//            @Override
//            public void onDirChange(File file) {
//                LOG.info("正在复制文件夹:"+file.getName());
//                File f = new File(file.getName());
//                f.delete();
//                try {
//                    InitApp.copyDir(file,file.getName(),true);
//                } catch (IOException e) {
//                    LOG.error("文件夹复制失败");
//                }
//            }
//
//            @Override
//            public void onFileChange(File file) {
//                LOG.info("正在复制文件:"+file.getName());
//
//                try {
//                    InitApp.copyFile(file,file.getName());
//                } catch (IOException e) {
//                    LOG.error("文件复制失败");
//                }
//            }
//        };
//        monitor.add(themeDir,listener);
//        monitor.add(adminDir,listener);
//        monitor.add(prop,listener);
    }

}
