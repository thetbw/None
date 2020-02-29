package xyz.thetbw.blog.web.controller;

import org.springframework.ui.Model;
import xyz.thetbw.blog.data.AppContext;
import xyz.thetbw.blog.data.constant.ModelKey;


public abstract class BaseController {
    /**
     * 根据主题返回页面
     * @param page
     * @return
     */
    protected String render(String page, Model model){
        String themeName = AppContext.getInstance().theme.getThemeFilePath(); //获得当前主题名称
        //根据主题返回对应页面
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(themeName).append("/").append(page);
        if (model!=null){
            model.addAttribute(ModelKey.CURRENT,page);
        }
        return stringBuilder.toString();
    }


    /**
     * 跳转
     * @param page
     * @return
     */
    protected String redirect(String page){
        return "redirect:"+page;
    }
}
