package xyz.thetbw.blog.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import xyz.thetbw.blog.annotation.RequestLimit;
import xyz.thetbw.blog.exception.CommentException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


@Aspect
@Component
public class CommentLimit {
    private static final Logger LOG = LoggerFactory.getLogger(CommentLimit.class);

    private HashMap<String,LeakyBucket> limitPool = new HashMap<>();

    @Autowired
    HttpServletRequest request;

    @Before("@annotation(xyz.thetbw.blog.annotation.RequestLimit)")
    public void limit(JoinPoint joinPoint) throws CommentException {
        LOG.debug("评论计数，当前池容量："+limitPool.size());
        if (request==null) throw new RuntimeException("request 为空");
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        RequestLimit limit = methodSignature.getMethod().getAnnotation(RequestLimit.class);
        if (limit==null) throw new RuntimeException("aspect 错误，RequestLimit 为空");
        String ip = request.getRemoteAddr();
        synchronized (limitPool){
            LeakyBucket bucket = limitPool.get(ip);
            if (bucket==null){
                double speed = (double) limit.count()/(double) limit.time();
                bucket=new LeakyBucket(limit.count(),speed);
                limitPool.put(ip,bucket);
            }
            LOG.debug("当前计数器信息：value:"+bucket.getValue()+" size:"+bucket.getSize());
            if (!bucket.add()){
                throw new CommentException("评论过于频繁");
            }
        }
    }



    @Scheduled(fixedRate = 15000)
    public void refreshPool(){
        synchronized (limitPool){
            LOG.debug("正在刷新评论计数池，当前池容量："+limitPool.size());
            if (limitPool.isEmpty()) return;
            Iterator<Map.Entry<String,LeakyBucket>> iterator = limitPool.entrySet().iterator();
            Map.Entry<String,LeakyBucket> entry;
            while (iterator.hasNext()){
                entry = iterator.next();
                LOG.debug("当前计数器信息：value:"+entry.getValue().getValue()+" size:"+entry.getValue().getSize());
                if (!entry.getValue().refresh()){
//                    limitPool.remove(entry.getKey()); //ConcurrentModificationException异常
                    iterator.remove();
                }
            }
        }
    }






    public static class LeakyBucket{
        private double size;  //水桶大小
        private double value; //当前值
        private long refreshTime; //上次刷新时间 单位 ms
        private double speed;  //水流速，每ms流走的水大小

        LeakyBucket(int size){
            this(size,10);
        }

        /**
         *
         * @param size 水桶大小
         * @param speed 每ms流走的水个数
         */
        LeakyBucket(int size,double speed){
            this.size =size;
            this.speed = speed;
            this.refreshTime = System.currentTimeMillis();
        }


        public double getSize() {
            return size;
        }

        public double getValue() {
            return value;
        }


        public boolean add(){
            synchronized (this){
                if ((value+1)>size)
                    return false;
                else{
                    value++;
                    return true;
                }
            }
        }


        public boolean refresh(){
            synchronized (this){
                long time = System.currentTimeMillis();
                double flowed =(time-refreshTime)*speed;
                this.refreshTime = time;
                if (value-flowed<0)
                    return false;
                else {
                    value-=flowed;
                    return true;
                }

            }
        }




    }
}
