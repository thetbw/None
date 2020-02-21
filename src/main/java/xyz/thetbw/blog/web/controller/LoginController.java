package xyz.thetbw.blog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xyz.thetbw.blog.data.constant.ConstValue;
import xyz.thetbw.blog.data.constant.FieldKey;
import xyz.thetbw.blog.data.entity.User;
import xyz.thetbw.blog.data.model.DefaultMsgModel;
import xyz.thetbw.blog.exception.*;
import xyz.thetbw.blog.service.UserService;
import xyz.thetbw.blog.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class LoginController extends BaseController{

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String l(){
        return "login";
    }

    @GetMapping("/register")
    public String r(){
        return "register";
    }


    @PostMapping("/register")
    public String register(@RequestParam String user_name,
                           @RequestParam String user_nickname,
                           @RequestParam String user_pass,
                           @RequestParam String user_email,
                           HttpServletResponse response) throws StringNotExistException, StringIsNoneException, IOException {
        User user = null;
        try {
            user = userService.creatMemberUser(user_name,user_nickname,user_pass,user_email);
        } catch (UserAlreadyExitsException e) {
            throw new RuntimeException("用户创建失败，已存在相同的用户，请改变用户名或昵称重试");
        }
        if (user==null){
            throw new RuntimeException("用户创建失败");
        }
        response.sendRedirect("/");
        return null;
    }

    /**
     * 用户登陆
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @PostMapping("/login")
    @ResponseBody
    public Object login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String user_name = request.getParameter("user_name");
        String user_pass = request.getParameter("user_pass");
        String auto_login = request.getParameter("auto_login");
        userService.checkUser(user_name,user_pass);
        if (auto_login!=null&&auto_login.equals("on"))
            userService.addUserToCookie((User) request.getSession().getAttribute(FieldKey.USER_ACCESS),response);
//        String referer=request.getHeader("Referer");
//        if (referer!=null)
//            response.sendRedirect(referer);
//        else
//            response.sendRedirect("/");
        return "true";
    }

    @RequestMapping("/exit")
    public Object exit(HttpServletRequest request,HttpServletResponse httpServletResponse) throws IOException {
        request.getSession().removeAttribute(FieldKey.USER_ACCESS);
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie:cookies){
            if (cookie.getName().equals(FieldKey.USER_ACCESS)){
                cookie.setMaxAge(0);
                httpServletResponse.addCookie(cookie);
            }
        }
        String referer=request.getHeader("Referer");
        if (referer!=null)
            httpServletResponse.sendRedirect(referer);
        return this.render("index");
    }


}
