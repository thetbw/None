package xyz.thetbw.blog.web.controller.api;

import org.commonmark.node.AbstractVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.thetbw.blog.data.AppContext;
import xyz.thetbw.blog.data.entity.Page;
import xyz.thetbw.blog.data.entity.User;
import xyz.thetbw.blog.data.model.SettingModel;
import xyz.thetbw.blog.data.model.ThemeModel;
import xyz.thetbw.blog.data.model.admin.GeneralInfoModel;
import xyz.thetbw.blog.service.CommonService;
import xyz.thetbw.blog.service.PageService;
import xyz.thetbw.blog.service.UserService;

import java.util.List;

@RestController()
@RequestMapping("/api/blog")
public class BlogApi {

    @Autowired
    CommonService commonService;

    @Autowired
    UserService userService;

    @Autowired
    PageService pageService;

    /**
     * 获取博客所有页面
     * @return
     */
    @GetMapping("/pages")
    public List<Page> pages(){
        return pageService.getPages();
    }

    /**
     * 获取博客设置
     * @return
     */
    @GetMapping("/setting")
    public SettingModel setting(){
        return AppContext.getInstance().setting;
    }

    /**
     * 获取当前主题信息
     * @return
     */
    @GetMapping("/theme")
    public ThemeModel theme(){
        return AppContext.getInstance().theme;
    }

    /**
     * 获取当前管理员信息
     * @return
     */
    @GetMapping("/admin")
    public User admin(){
        return userService.getAdminUser();
    }

    /**
     * 获取博客基本信息
     * @return
     */
    @GetMapping("/info")
    public GeneralInfoModel info(){
        return commonService.generalInfoModel();
    }
}
