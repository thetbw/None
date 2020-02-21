package xyz.thetbw.blog.service;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.thetbw.blog.data.AppContext;
import xyz.thetbw.blog.data.constant.ConstValue;
import xyz.thetbw.blog.data.constant.FieldKey;
import xyz.thetbw.blog.data.dao.PropertyDao;
import xyz.thetbw.blog.data.entity.Property;
import xyz.thetbw.blog.data.model.ThemeModel;
import xyz.thetbw.blog.exception.ThemeException;
import xyz.thetbw.blog.exception.ThemeNotFountException;
import xyz.thetbw.blog.exception.ThemeTypeErrorException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * 主题相关
 */
@Service
public class ThemeService {

    private static final Logger LOG = LoggerFactory.getLogger(ThemeService.class);

    Gson gson = new Gson();

    @Autowired PropertyDao propertyDao;

    /**
     * 读取指定主题
     * @param themePathName
     * @return
     * @throws ThemeException
     */
    public ThemeModel loadTheme(String themePathName) throws ThemeException {
        File f = getThemeFolder();
        File themeFolder = new File(f,themePathName);
        ThemeModel themeModel = getThemeModel(themeFolder);
        return themeModel;
    }

    /**
     *获取当前主题
     * @return
     */
    public ThemeModel getTheme(){
        return AppContext.getInstance().theme;
    }


    /**
     * 设置为当前主题
     * @param themePathName
     */
    public void setTheme(String themePathName) throws ThemeException {
        try {
            ThemeModel theme = loadTheme(themePathName);
            AppContext.getInstance().theme = theme;
            Property p = propertyDao.getPropertyByKey(FieldKey.THEME_PROPERTY_KEY);
            if (p==null){
                p=new Property();
                p.setProperty_key(FieldKey.THEME_PROPERTY_KEY);
                p.setProperty_value(themePathName);
                propertyDao.add(p);
            }else {
                p.setProperty_value(themePathName);
                propertyDao.update(p);
            }
        } catch (ThemeException e) {
            LOG.error(e.getMessage());
            throw e;
        }
    }


    /**
     * 获取当前主题列表
     * @return
     */
    public ArrayList<ThemeModel> getThemes(){
        File f = null;
        try {
            f = getThemeFolder();
        } catch (ThemeException e) {

        }
        ArrayList<ThemeModel> themeModels = new ArrayList<>();
        File[] files = f.listFiles();
        for (File file:files){
            ThemeModel themeModel = null;
            try {
                themeModel = getThemeModel(file);
            } catch (ThemeException e) {
                LOG.warn(e.getMessage());
                LOG.warn("读取主题配置文件错误 "+file.getPath());
            }
            if (themeModel!=null){
                themeModels.add(themeModel);
            }
        }
        return themeModels;
    }


    /**
     * 主题错误的处理方法
     */
    public void HandleThemeError(){
        LOG.warn("主题出现错误，正在应用默认设置");
    }


    /**
     * 获取主题文件夹
     * @return
     * @throws ThemeException
     */
    private File getThemeFolder() throws ThemeException {
        File f = new File(ConstValue.THEMES_PATH);
        if (!f.exists()||!f.isDirectory()) {
            throw new ThemeException("主题目录错误，请检查配置文件或者文件目录");
        }
        return f;
    }

    /**
     * 读取文件夹中的主题配置文件
     * @param file
     * @return
     * @throws ThemeException
     */
    private ThemeModel getThemeModel(File file) throws  ThemeException {
        if (!file.exists()){
            throw new ThemeException("文件夹不存在");
        }
        File jsonFile = new File(file,ConstValue.THEME_CONFIG_NAME);
        if (!jsonFile.exists()||jsonFile.isDirectory()){
            throw new ThemeTypeErrorException("配置文件错误或不存在");
        }
        try {
            ThemeModel themeModel = gson.fromJson(new FileReader(jsonFile),ThemeModel.class);
            themeModel.setThemeFilePath(file.getName());
            themeModel.setThemePreviewImagePath("/"+themeModel.getThemeFilePath()+"/"+themeModel.getThemePreviewImagePath());
            if (file.getName().equals(AppContext.getInstance().theme.getThemeFilePath()))
                themeModel.setIdEnabled(true);
            return themeModel;
        } catch (FileNotFoundException e) {
            throw new ThemeTypeErrorException("配置文件读取错误");
        }
    }


}
