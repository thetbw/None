package xyz.thetbw.blog.web.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import xyz.thetbw.blog.service.ArticleService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Vector;


public class ArticleCountInterceptor implements HandlerInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(ArticleCountInterceptor.class);

    private final static String BROWSE_FLAG="browse_flag";

    @Autowired
    ArticleService articleService;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (ex!=null)return;
        Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String s = (String) pathVariables.get("article_id");
        int id=0;
        try {
            id= Integer.parseInt(s) ;
        }catch (NumberFormatException e){
            LOG.warn("文章id获取失败 错误字符串："+s+"; 错误url:"+request.getRequestURI());
            return;
        }

        Vector<Integer> vector = getBrowsedArticleVector(request);
        articleService.addBrowsedCount(id,vector);
    }

    /**
     * java.lang.IllegalStateException: No thread-bound request found: Are you referring to request attributes outside of an actual web request, or processing a request outside of the originally receiving thread?
     * @return
     */
    private  Vector<Integer> getBrowsedArticleVector(HttpServletRequest request){
        Vector<Integer> articles=null;
        try{
            articles=(Vector<Integer>) request.getSession().getAttribute(BROWSE_FLAG);
        }catch (Exception e){}
        if (articles==null){
            articles=new Vector<>();
            request.getSession().setAttribute(BROWSE_FLAG,articles);
        }
        return articles;
    }

}
