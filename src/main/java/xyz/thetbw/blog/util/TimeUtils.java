package xyz.thetbw.blog.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间时区转换
 */
public class TimeUtils {
    private static final Logger LOG = LoggerFactory.getLogger(TimeUtils.class);
    private static TimeUtils ourInstance = new TimeUtils();

    public static TimeUtils getInstance(){
        return ourInstance;
    }
    private TimeUtils(){}



    public String format(String format,Long time){
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = new Date(time);
        return dateFormat.format(date);
    }

    /**
     * 获取某天的开始和结束 (单位，毫秒) (可能有问题)
     * @return index 0:开始时间 index 1:结束时间
     */
    public long[] getDayTimeRange(Date time){
        long[] date =new long[2];
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
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
