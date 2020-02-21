package xyz.thetbw.blog.web.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import xyz.thetbw.blog.exception.UserException;
import xyz.thetbw.blog.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户根据cookie自动登陆
 */
public class UserInterceptor implements HandlerInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(UserInterceptor.class);

    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        try {
            userService.findUser();
        }catch (UserException e){
            LOG.warn(e.getMessage());
        }
        return true;
    }
}
