package xyz.thetbw.blog.web.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import xyz.thetbw.blog.data.constant.ConstValue;
import xyz.thetbw.blog.data.constant.FieldKey;
import xyz.thetbw.blog.data.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 阻止非admin用户访问后台
 */
public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (
                request.getSession().getAttribute(FieldKey.USER_ACCESS)==null||
                ((User)request.getSession().getAttribute(FieldKey.USER_ACCESS)).getUser_role()!=User.USER_ROLE_ADMIN
        ) {
            response.sendRedirect("/login?referer=/admin");
            return false;
        }
        return true;

    }
}
