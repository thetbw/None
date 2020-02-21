package xyz.thetbw.blog.util;

import org.apache.ibatis.plugin.Signature;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * 简单的反射工具
 */
public class ReflectUtils {
    private static ReflectUtils ourInstance = new ReflectUtils();

    public static ReflectUtils getInstance(){
        return ourInstance;
    }
    private ReflectUtils(){}


    /**
     * 批量设置对象的值
     * @param map 对象属性和值的键值对
     * @param o 要设置的对象
     * @return 设置成功的数量
     */
    public int setAllField(HashMap<String,String> map,Object o){
        CountUtils countUtils = new CountUtils();
        map.forEach((key,value)->{
           if (setField(key,value,o)) countUtils.add();
        });
        return countUtils.get();
    }
    /**
     * 将指定的值设置到指定对象的指定字段上
     * @param fieldName 要设置的字段
     * @param fieldValue 要设置的值
     * @param object 要设置的对象
     * @return 是否设置成功
     */
    public boolean setField(String fieldName,String fieldValue,Object object){
        try{
            Field  field= object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            Class c = field.getType();
            Object obj = c.getConstructor(String.class).newInstance(fieldValue);
            field.set(object,obj);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 检查对象有没有该字段
     * @param fieldName 要检查的字段
     * @param o 要检查的对象
     * @return
     */
    public boolean checkField(String fieldName,Object o){
        try {
            Field field = o.getClass().getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
           return false;
        }
        return true;
    }


    public <T> T getFieldValue(String fieldName,Object target,Class<T> type){
        try {
            Field field=null;
            if (target instanceof Class){
                field=((Class) target).getDeclaredField(fieldName);
            }else {
                field=target.getClass().getDeclaredField(fieldName);
            }
            field.setAccessible(true);
            T instance = type.cast(field.get(target));
            return instance;
        } catch (Exception e){

        }
        return null;
    }



}
