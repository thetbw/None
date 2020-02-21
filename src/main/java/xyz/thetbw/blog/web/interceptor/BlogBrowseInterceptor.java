package xyz.thetbw.blog.web.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import xyz.thetbw.blog.service.BrowseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BlogBrowseInterceptor implements HandlerInterceptor {

    @Autowired
    BrowseService browseService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        browseService.addBrowse(request.getSession(),request.getRemoteAddr());
        return true;
    }
}
