package xyz.thetbw.blog.web.controller;

import xyz.thetbw.blog.data.AppContext;


public abstract class BaseController {
    /**
     * 根据主题返回页面
     * @param page
     * @return
     */
    protected String render(String page){
        String themeName = AppContext.getInstance().theme.getThemeFilePath(); //获得当前主题名称
        //根据主题返回对应页面
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(themeName).append("/").append(page);
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
