package xyz.thetbw.blog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.thetbw.blog.data.dao.PageDao;
import xyz.thetbw.blog.data.dao.UserDao;
import xyz.thetbw.blog.data.entity.Category;
import xyz.thetbw.blog.data.entity.Page;
import xyz.thetbw.blog.data.entity.User;
import xyz.thetbw.blog.exception.StringIsNoneException;
import xyz.thetbw.blog.util.StringUtils;

@Controller
public class InstallController extends BaseController {


    @Autowired
    UserDao userDao;

    @Autowired
    PageDao pageDao;

    @GetMapping("/install")
    public String install(){
        User user = userDao.getAdminUser();
        if (user!=null)
            return "finish";
        return "install";
    }

    @PostMapping("/install")
    public String install(@RequestParam String  user_name, @RequestParam String user_pass) throws StringIsNoneException {
        User user = userDao.getAdminUser();
        if (user!=null)
            return "finish";
        if (!StringUtils.getInstance().checkValidity(user_name)||!StringUtils.getInstance().checkValidity(user_pass)){
            throw new StringIsNoneException("不允许空字符");
        }
        user = new User();
        user.setUser_name(user_name);
        user.setUser_pass(user_pass.hashCode());
        user.setUser_role(User.USER_ROLE_ADMIN);
        user.setUser_nickname("admin");
        userDao.add(user);
        addPage();
        return "finish";
    }


    private void addPage(){
        Page index = new Page();
        index.setPage_name("首页");
        index.setPage_url("/");
        index.setPage_type(Page.PAGE_TYPE_URL);
        Page category = new Page();
        category.setPage_name("分类");
        category.setPage_url("/category");
        category.setPage_type(Page.PAGE_TYPE_URL);
        Page tag = new Page();
        tag.setPage_type(Page.PAGE_TYPE_URL);
        tag.setPage_url("/tag");
        tag.setPage_name("标签");


        try{
            pageDao.add(index);
            pageDao.add(category);
            pageDao.add(tag);
        }catch (Exception e){}
    }



}
