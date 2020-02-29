package xyz.thetbw.blog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


public class InitApp implements ApplicationContextInitializer {
    private static final Logger LOG = LoggerFactory.getLogger(InitApp.class);


    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        File file = new File("application.properties");
        if (!file.exists()) {
            LOG.info("配置文件不存在，正在初始化");
            doInit(false);
            LOG.info("初始化成功，请重新启动，并修改 application.properties 进行你的配置");
            LOG.info("启动成功后可以访问 /install 进行管理员注册");
            System.exit(1);
        }
    }


    public static void doInit(boolean force) {

        LOG.info("正在初始化资源文件");
        try {
            URL url = new ClassPathResource("application.properties").getURL();  //如果是jar包，从jar包中读取
            if (url.getProtocol().equals("jar")){
                initWithJar(url,force);
                return ;
            }
            File themeDir = new ClassPathResource("themes").getFile();
            File adminDir = new ClassPathResource("admin").getFile();
            File prop = new ClassPathResource("application.properties").getFile();

            if (!adminDir.exists() || !themeDir.exists()) {
                LOG.error("初始化错误,资源文件错误");
                System.exit(1);
            }
            if (!prop.exists()) {
                LOG.error("配置文件丢失");
                System.exit(1);
            }
            try {
                LOG.info("正在复制配置文件");
                copyFile(prop, "application.properties");
                LOG.info("正在复制资源文件");
                copyDir(adminDir, "admin", force);
                copyDir(themeDir, "themes", force);
            } catch (IOException e) {
                LOG.error("复制文件失败:" + e.getMessage());
                System.exit(1);
            }
        } catch (Exception e) {
            LOG.error("初始化错误:" + e.getMessage());
        }

    }

    /**
     * 从jar包初始化数据
     * @param url jar包的url
     * @param force 是否强制覆盖文件
     * @throws IOException
     */
    private static void initWithJar(URL url,boolean force) throws IOException {
        LOG.info("正在以jar包形式初始化");
        JarFile jarFile = getJarFile(url);
        LOG.info("当前jar size:"+jarFile.size()+" name:"+jarFile.getName());
        Enumeration<JarEntry> jarEntries = jarFile.entries();
        while (jarEntries.hasMoreElements()){
            JarEntry entry = jarEntries.nextElement();
            String name = entry.getName();
            if (name.startsWith("admin")||name.startsWith("themes")){
                if (entry.isDirectory()){
                    makeJarDir(entry,force);
                }else {
                    copyJarFile(jarFile,entry,force);
                }
            }else if (name.equals("application.properties")){
                copyJarFile(jarFile,entry,force);
            }

        }

    }

    /**
     * 创建文件夹
     */
    private static void makeJarDir(JarEntry entry,boolean force){
        LOG.info("正在创建文件夹："+entry.getName());
        File file = new File(entry.getName());
        if (file.exists()){
            LOG.info("文件夹"+entry.getName()+" 已存在");
            if (force){
                LOG.info("正在覆盖文件夹："+entry.getName());
                file.delete();
                file.mkdirs();
            }
        }else {
            file.mkdirs();
        }
    }

    /**
     * 从jar包复制文件
     */
    private static void copyJarFile(JarFile jarFile,JarEntry entry,boolean force) throws IOException {
        LOG.info("正在复制文件："+entry.getName());
        File file =new File(entry.getName());
        if (file.exists()){
            if (!force){
                LOG.info("文件已存在，跳过复制");
                return;
            }
        }
        FileOutputStream outputStream = new FileOutputStream(file);
        InputStream inputStream = jarFile.getInputStream(entry);
        i2o(inputStream,outputStream);

    }

    /**
     * 把inputStream的数据放入outputStream
     * @param inputStream 输入
     * @param outputStream 输出
     * @throws IOException
     */
    private static void i2o(InputStream inputStream,OutputStream outputStream) throws IOException {
        byte[] temp = new byte[1024];
        int size;
        while ((size = inputStream.read(temp)) != -1) {
            outputStream.write(temp, 0, size);
            outputStream.flush();
        }
        inputStream.close();
        outputStream.close();
    }

    /**
     * 将文件夹复制到指定位置
     *
     * @param dir   File,必须为文件夹
     * @param path  要复制的路径
     * @param force 是否要强制复制
     * @throws IOException
     */
    private static void copyDir(File dir, String path, boolean force) throws IOException {
        File file = new File(path);
        file.mkdirs();
        File[] children =dir.listFiles();
        LOG.debug("当前文件夹："+file);
        LOG.debug("当前文件夹子文件数量："+children.length);
        for (File child:children){
            if (child.isDirectory()){
                copyDir(child,path+"/"+child.getName(),force);
            }else {
                copyFile(child,path+"/"+child.getName());
            }
        }
    }

    /**
     * 将一个文件复制到指定路劲
     * @param file
     * @param path
     * @throws IOException
     */
    private static void copyFile(File file, String path) throws IOException {
        LOG.debug("正在复制文件："+file);
        FileOutputStream outputStream = new FileOutputStream(path);
        InputStream inputStream = new FileInputStream(file);
        i2o(inputStream,outputStream);

    }

    private static JarFile getJarFile(URL url) throws IOException {
        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
        JarFile jarFile = jarURLConnection.getJarFile();
        return jarFile;

    }


}
