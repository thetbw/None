package xyz.thetbw.blog.util;

import xyz.thetbw.blog.exception.StringIsNoneException;
import xyz.thetbw.blog.exception.StringNotExistException;
import xyz.thetbw.blog.exception.StringToShortException;

/**
 *
 */
public class StringUtils {
    private static StringUtils ourInstance = new StringUtils();

    public static StringUtils getInstance(){
        return ourInstance;
    }
    private StringUtils(){}


    /**
     * 检查字符串
     * @param s
     */
    public String rightString(String s) throws StringNotExistException, StringIsNoneException {
        if (s==null)
            throw new StringNotExistException("字符串不存在");
        String s1 = s.trim();
        if (s1.equals(""))
            throw new StringIsNoneException("字符串不能为空");
        return s1;
    }

    public Boolean checkString(String s){
        if (s==null)
            return false;
        String s1 = s.trim();
        if (s1.equals(""))
            return false;
        return true;
    }


    public String rightString(String s, int length) throws StringNotExistException, StringIsNoneException, StringToShortException {
        String s1 = rightString(s);
        if (s1.length()<length)
            throw new StringToShortException("字符太短");
        return s1;
    }


}
