package xyz.thetbw.blog.util;

/**
 * 用来计数
 */
public class CountUtils {
    private int mun;

    public CountUtils(){
        mun=0;
    }
    public CountUtils(int mun){
        this.mun=mun;
    }

    public void add(){
        mun++;
    }

    public void add(int value){
        mun+=value;
    }

    public int get(){
        return mun;
    }

}
