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
     * 返回一个有效的字符串(除去两边空格)
     * @param s
     */
    public String validString(String s) throws StringNotExistException, StringIsNoneException {
        if (s==null)
            throw new StringNotExistException("字符串不存在");
        String s1 = s.trim();
        if (s1.equals(""))
            throw new StringIsNoneException("字符串不能为空");
        return s1;
    }

    /**
     * 检查字符串的有效性
     * @param s
     * @return
     */
    public Boolean checkValidity(String s){
        return checkValidity(s,0);
    }

    public Boolean checkValidity(String s,int length){
        if (s==null)
            return false;
        String s1 = s.trim();
        if (s1.equals(""))
            return false;
        if (s1.length()<length)
            return false;
        return true;
    }


    public String htmlTrans(String text){
        String value;
        value=text.replace("<","&lt");
        value=value.replace(">","&gt");
        return value;
    }



}
