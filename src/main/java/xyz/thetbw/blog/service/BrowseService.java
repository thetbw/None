package xyz.thetbw.blog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import xyz.thetbw.blog.data.constant.FieldKey;
import xyz.thetbw.blog.data.dao.BrowseDao;
import xyz.thetbw.blog.data.entity.Browse;
import xyz.thetbw.blog.data.entity.User;

import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 浏览记录相关
 */
@Service
public class BrowseService {
    private static final Logger LOG = LoggerFactory.getLogger(BrowseService.class);

    @Autowired
    BrowseDao browseDao;


    /**
     * 添加浏览记录，如果已经有则不会再次记录，记录保存在session
     * @param session 提供session以供保存
     * @param ip 访问的ip地址
     */
    @Async
    public void addBrowse(HttpSession session,String ip){
        User user = null;
        synchronized (session) {
            try {
                user = (User) session.getAttribute(FieldKey.USER_ACCESS);
            } catch (Exception e) {
            }
            if (user != null)
                if (user.getUser_role() == User.USER_ROLE_ADMIN) return;
            Browse browse = null;
            try {
                browse = (Browse) session.getAttribute(FieldKey.BLOG_BROWSED);
            } catch (Exception e) {
            }
            if (browse != null) {
                if (browse.getBrowsed_user_id() == 0 && user != null) {
                    browse.setBrowsed_user_id(user.getUser_id());
                    browseDao.update(browse);
                } else return;
            } else {
                browse = new Browse();
                session.setAttribute(FieldKey.BLOG_BROWSED, browse);
                if (user != null)
                    browse.setBrowsed_user_id(user.getUser_id());
                browse.setBrowsed_ip(ip);
                browse.setBrowsed_time(System.currentTimeMillis());
                browseDao.add(browse);
            }
        }

    }


    /**
     * 获取博客访问次数
     * @return
     */
    public int count(){
//        long[] date = getTodayTime();
        return browseDao.getAllCount();
    }


    /**
     * 获取今天的开始和结束 (单位，毫秒) (可能有问题)
     * @return index 0:开始时间 index 1:结束时间
     */
    public long[] getTodayTime(){
        long[] date =new long[2];
        DateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd");
        Calendar calendar = Calendar.getInstance();
        String began = calendar.get(Calendar.YEAR)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DATE,1);
        String after = calendar.get(Calendar.YEAR)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.DAY_OF_MONTH);
        try {
            Date b = dateFormat.parse(began);
            Date a =dateFormat.parse(after);
            date[0] = b.getTime();
            date[1]=a.getTime();
            return date;
        } catch (ParseException e) {
            LOG.error("获取日期失败->格式化日期字符串失败："+began+"\t"+after);
        }
        return date;
    }

}
