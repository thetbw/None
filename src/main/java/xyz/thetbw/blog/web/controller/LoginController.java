package xyz.thetbw.blog.web.controller;

import cn.hutool.core.util.StrUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xyz.thetbw.blog.data.AppContext;
import xyz.thetbw.blog.data.constant.FieldKey;
import xyz.thetbw.blog.data.entity.User;
import xyz.thetbw.blog.exception.*;
import xyz.thetbw.blog.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class LoginController extends BaseController {


    @Value("${auth.github.client-id}")
    private String githubAppId;

    @Value("${auth.siteurl}")
    private String siteUrl;

    @Autowired
    UserService userService;

    @Autowired
    HttpServletRequest request;

    @GetMapping("/login")
    public String l(@RequestParam(required = false) String referer, HttpServletResponse response) throws IOException {
        if (referer==null){
            String r = request.getHeader("Referer");
            if (r != null) {
                response.sendRedirect("/login?referer=" + r);
            }
        }
        return "login";
    }

    /**
     * github 登录
     */
    @GetMapping("/githubLogin")
    public String githubLogin(@RequestParam(required = false,name = "referer") String referer,HttpServletResponse response) throws IOException {
        response.sendRedirect(
                StrUtil.format("https://github.com/login/oauth/authorize?client_id={}&redirect_uri={}/githubRedirect?referer={}",githubAppId,siteUrl,referer));
        return "login";
    }

    @GetMapping("/githubRedirect")
    public String githubCallback(@RequestParam(required = false,name = "referer") String referer,@RequestParam("code") String code,HttpServletResponse response) throws RequestException, IOException {
        User user = userService.githubLogin(code);
        request.getSession().setAttribute(FieldKey.USER_ACCESS,user);
        if (referer!=null)
            response.sendRedirect(referer);
        return null;
    }

    @GetMapping("/register")
    public String r() {
        return "register";
    }


    @PostMapping("/register")
    public String register(@RequestParam String user_name,
                           @RequestParam String user_nickname,
                           @RequestParam String user_pass,
                           @RequestParam String user_email,
                           @RequestParam(required = false) String referer,
                           HttpServletResponse response) throws RequestException, IOException {
        User user;
        if (!AppContext.getInstance().setting.getCanRegister()){
            throw new RequestException("已关闭注册");
        }
        try {
            user = userService.creatMemberUser(user_name, user_nickname, user_pass, user_email);
        } catch (UserAlreadyExitsException e) {
            throw new RuntimeException("用户创建失败，已存在相同的用户，请改变用户名或昵称重试");
        }
        if (user == null) {
            throw new RuntimeException("用户创建失败");
        }
        userService.addUserToCookie(user,response);
        if (referer != null) {
            response.sendRedirect(referer);
            return null;
        }
        return this.redirect("index");
    }

    /**
     * 用户登陆
     *
     * @param response
     * @return
     * @throws IOException
     */
    @PostMapping("/login")
    public Object login(@RequestParam String user_name,
                        @RequestParam String user_pass,
                        @RequestParam(required = false) String auto_login,
                        @RequestParam(required = false) String referer,
                        HttpServletResponse response) throws Exception {
        userService.checkUser(user_name, user_pass);
        if (auto_login != null && auto_login.equals("on"))
            userService.addUserToCookie((User) request.getSession().getAttribute(FieldKey.USER_ACCESS), response);
        if (referer!=null)
            response.sendRedirect(referer);
        return null;
    }

    @RequestMapping("/exit")
    public Object exit(HttpServletRequest request, HttpServletResponse httpServletResponse) throws IOException {
        request.getSession().removeAttribute(FieldKey.USER_ACCESS);
        userService.deleteAccessFromCookie(httpServletResponse);
        String referer = request.getHeader("Referer");
        if (referer != null) {
            httpServletResponse.sendRedirect(referer);
            return null;
        } else return this.redirect("index");

    }


}
