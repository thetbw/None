package xyz.thetbw.blog.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;

/**
 * 文件监视器
 * @author thetbw
 */
public class FileMonitor {
    private static final Logger LOG = LoggerFactory.getLogger(FileMonitor.class);
    private static FileMonitor instance;
    private HashMap<File,FileListener> fileListeners;
    private HashMap<File,Long> fileStates;
    private Thread pollingThred;
    private boolean stared;
    private long sleepTime=2000;

    public static synchronized FileMonitor getInstance(){
        if (instance==null)
            instance = new FileMonitor();
        return instance;
    }

    private FileMonitor(){
        fileListeners = new HashMap<>();
        fileStates = new HashMap<>();
        pollingThred = new Thread(new CheckPolling());
    }


    /**
     * 开始监听文件变动，如果已经调用add方法，则无需再次调用本方法
     */
    public synchronized void start(){
        stared=true;
        if (!pollingThred.isAlive()){
            pollingThred.start();
        }
    }

    /**
     * 停止文件监视，可能不会立马停止
     */
    public synchronized void stop(){
        stared = false;
    }


    /**
     * 设置轮询线程的休眠时间
     * @param time 休眠的时间
     */
    public void setSleepTime(long time){
        this.sleepTime = time;
    }

    /**
     * 添加一个需要监视的文件,并且自动开启轮询
     * @param file 需要监视的文件
     * @param listener 监听器
     */
    public synchronized void add(File file,FileListener listener){
        fileListeners.put(file,listener);
        fileStates.put(file,file.lastModified());
        if (stared)
            start();
    }

    /**
     * 移除一个需要监视的文件
     * @param file 待移除的文件
     */
    public synchronized void remove(File file){
        fileListeners.remove(file);
        fileStates.remove(file);
    }

    private class CheckPolling implements Runnable {

        @Override
        public void run() {
            while (stared){
                LOG.debug("正在扫描文件变动");
                fileStates.forEach((f, l)->{
                    long n = f.lastModified();
                    if (n>l){
                        FileListener listener = fileListeners.get(f);
                        LOG.info("文件发生变动:"+f.getName());
                        if (listener==null) return;
                        if (f.exists()){
                            if (f.isDirectory())
                                listener.onDirChange(f);
                            else
                                listener.onFileChange(f);
                        }else {
                            listener.onDelete(f);
                        }
                    }
                });
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    LOG.error("发生错误：文件监视线程sleep error");
                    LOG.error(e.getMessage());
                }
            }
        }
    }


    public  interface FileListener{

        public void onDelete(File file);

        public void onDirChange(File file);

        public void onFileChange(File file);
    }

}
