package xyz.thetbw.blog.data;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.thetbw.blog.data.constant.ConstValue;
import xyz.thetbw.blog.data.constant.FieldKey;
import xyz.thetbw.blog.data.constant.SettingKey;
import xyz.thetbw.blog.data.dao.PropertyDao;
import xyz.thetbw.blog.data.entity.Property;
import xyz.thetbw.blog.data.model.SettingModel;
import xyz.thetbw.blog.data.model.ThemeModel;
import xyz.thetbw.blog.exception.ThemeException;
import xyz.thetbw.blog.service.ThemeService;
import xyz.thetbw.blog.util.ReflectUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * 全局数据
 */
@Component
public class AppContext {
    private static final Logger LOG = LoggerFactory.getLogger(AppContext.class);

//    public String title="TheTBW";
//    public HashMap<String,String> settingMap = new HashMap<>(); //存放各设置数据
    public SettingModel setting;
    public ThemeModel theme;

    @Autowired private PropertyDao propertyDao;
    @Autowired private ThemeService themeService;


    private static AppContext ourInstance ;


    public static  AppContext getInstance() {
        return ourInstance;
    }

    private AppContext() {

    }

    @PostConstruct
    public void init(){
        LOG.info("正在初始化数据");
        ourInstance=this;
        setting = new SettingModel();
        theme = new ThemeModel();


        try {
            List<Property> properties = propertyDao.getAll();
            for (Property p : properties) {
                try {
                    ReflectUtils.getInstance().setField(p.getProperty_key(), p.getProperty_value(), setting);
                } catch (Exception e) {
                    LOG.error("从数据库加载设置值时发生错误,现在为默认值");
                }
            }
            Property theme = propertyDao.getPropertyByKey(FieldKey.THEME_PROPERTY_KEY);
            if (theme != null) {
                try {
                    this.theme = themeService.loadTheme(theme.getProperty_value());
                } catch (ThemeException e) {
                    LOG.warn("从数据库加载主题设置失败，可能主题已被删除，已恢复成默认");
                    themeService.HandleThemeError();
                }
            } else {
                try {
                    this.theme = themeService.loadTheme(ConstValue.DEFAULT_THEME);
                } catch (ThemeException e) {
                    LOG.warn("没有找到默认主题");
                    themeService.HandleThemeError();
                }
            }
        }catch (Exception e){
            LOG.error("从数据读取数据失败，请检查数据库:"+e.getMessage());
            System.exit(1);
        }
    }




}
