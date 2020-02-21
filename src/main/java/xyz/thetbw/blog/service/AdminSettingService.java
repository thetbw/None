package xyz.thetbw.blog.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.thetbw.blog.data.AppContext;
import xyz.thetbw.blog.data.constant.ConstValue;
import xyz.thetbw.blog.data.constant.FieldKey;
import xyz.thetbw.blog.data.constant.SettingKey;
import xyz.thetbw.blog.data.dao.PropertyDao;
import xyz.thetbw.blog.data.entity.Property;
import xyz.thetbw.blog.data.model.ThemeModel;
import xyz.thetbw.blog.util.ReflectUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class AdminSettingService {

    @Autowired
    PropertyDao propertyDao;

    /**
     * 返回设置内容
     * @return
     */
    public HashMap<String,String> getSettings(){
        HashMap<String,String> hashMap = new HashMap<>();
        List<Property> properties = propertyDao.getAll();
        properties.forEach(p->{
            if (ReflectUtils.getInstance().checkField(p.getProperty_key(),AppContext.getInstance().setting)){
                hashMap.put(p.getProperty_key(),p.getProperty_value());
                ReflectUtils.getInstance().setField(p.getProperty_key(),p.getProperty_value(),AppContext.getInstance().setting);
            }
        });
        return hashMap;
    }

    /**
     * 检查字段是不是设置的字段
     * @param field
     * @return
     * @throws IllegalAccessException
     */
    private boolean checkField(String field)throws IllegalAccessException{
        Field[] fields =SettingKey.class.getFields();
        for (Field f:fields){
            if (f.get(String.class).equals(field)){
                return true;
            }
        }
        return false;
    }

    /**
     * 系统设置
     * @param hashMap 要设置的值
     * @return
     */
    public Object setting(HashMap<String,String> hashMap){
        hashMap.forEach((key,value)->{
            Property property=propertyDao.getPropertyByKey(key);
            if (property==null){
                property=new Property();
                property.setProperty_key(key);
                property.setProperty_value(value);
                propertyDao.add(property);
            }else {
                property.setProperty_value(value);
                propertyDao.update(property);
            }
        });
        ReflectUtils.getInstance().setAllField(hashMap,AppContext.getInstance().setting);
        return null;
    }



}
