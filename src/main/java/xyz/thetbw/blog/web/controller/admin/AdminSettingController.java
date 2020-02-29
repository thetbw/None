package xyz.thetbw.blog.web.controller.admin;

import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.thetbw.blog.data.AppContext;
import xyz.thetbw.blog.exception.ThemeException;
import xyz.thetbw.blog.service.AdminSettingService;
import xyz.thetbw.blog.service.ThemeService;
import xyz.thetbw.blog.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.HashMap;


@RestController
@RequestMapping("/admin/api/setting")
public class AdminSettingController {
    private static final Logger LOG = LoggerFactory.getLogger(AdminSettingController.class);

    Gson gson = new Gson();


    @Autowired
    AdminSettingService settingService;

    @Autowired
    UserService userService;

    @Autowired
    ThemeService themeService;


    @PostMapping("/basic")
    public Object basicSetting(@RequestBody String json){
        Type type = new TypeToken<HashMap<String,String>>(){}.getType();
        HashMap<String,String> hashMap = gson.fromJson(json,type);
        settingService.setting(hashMap);
        return null;
    }

    @GetMapping("/basic")
    public Object getBasicSetting(){
       return AppContext.getInstance().setting;
    }

    @PostMapping("/comment")
    public Object commentSetting(@RequestBody String json){
        return basicSetting(json);
    }

    @GetMapping("/comment")
    public Object getCommentSetting(){

        return AppContext.getInstance().setting;
    }

    @PostMapping("/user")
    public Object userSetting(@RequestBody String json){
        Type type = new TypeToken<HashMap<String,String>>(){}.getType();
        HashMap<String,String> hashMap = gson.fromJson(json,type);
        userService.updateUserInfo(hashMap);
        return null;
    }

    @GetMapping("/user")
    public Object getUserSetting(){

        return userService.getUserInfo();
    }

    @PostMapping("/user/password")
    public Object userPassSetiing(@RequestParam String old_password, @RequestParam String new_password,HttpServletResponse httpServletResponse){
        userService.updateUserPass(old_password,new_password,httpServletResponse);
        return null;
    }

    @PostMapping("/theme/setTheme")
    public Object setTheme(@RequestParam String theme_name) throws ThemeException {
        try {
            themeService.setTheme(theme_name);
        } catch (ThemeException e) {
            LOG.error(e.getMessage());
            throw e;
        }
        return null;
    }

    @GetMapping("/theme/getThemes")
    public Object getThemes()  {
        return themeService.getThemes();
    }

}
