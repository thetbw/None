package xyz.thetbw.blog.web.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.thetbw.blog.data.AppContext;
import xyz.thetbw.blog.data.entity.Article;
import xyz.thetbw.blog.data.entity.Category;
import xyz.thetbw.blog.data.entity.Tag;
import xyz.thetbw.blog.exception.ArticleContentNotFountException;
import xyz.thetbw.blog.exception.ArticleNotFountException;
import xyz.thetbw.blog.exception.RequestException;
import xyz.thetbw.blog.exception.RequestParameterException;
import xyz.thetbw.blog.service.ArticleService;
import xyz.thetbw.blog.service.CategoryService;
import xyz.thetbw.blog.service.TagService;
import xyz.thetbw.blog.service.UserService;
import xyz.thetbw.blog.util.PageHelper;
import xyz.thetbw.blog.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController()
@RequestMapping("/api/article")
public class ArticleApi {

    @Autowired
    ArticleService articleService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    TagService tagService;

    @Autowired
    UserService userService;

    @Autowired
    HttpServletRequest request;


    /**
     * 获取文章列表,只能获取已发表文章
     * @param page 分页，默认1
     * @param category_id 根据分类获取文章列表，默认无， 和tag_id只能选择一个
     * @param tag_id 根据标签获取文章列表
     * @return 文章列表
     * @throws RequestException
     */
    @GetMapping("/articles")
    public PageHelper<Article> articles(@RequestParam(defaultValue = "1") int page,
                                        @RequestParam Integer category_id,
                                        @RequestParam Integer tag_id) throws RequestException {
        if (category_id!=null&&tag_id!=null) throw new RequestParameterException("错误的参数");
        List<Article> articles = null;
        int length = AppContext.getInstance().setting.getIndexPagingSize();
        int count=0;
        if (category_id!=null){
            Category category =categoryService.getCategory(category_id);
            if (category==null) throw new RequestParameterException("分类不存在");
            articles = articleService.articles(page,length,category);
            count = articleService.count(category);
        }else if (tag_id!=null){
            Tag tag = tagService.get(tag_id);
            if (tag==null) throw new RequestParameterException("标签不存在");
            articles = articleService.articles(page,length,tag);
            count = articleService.count(tag);
        }else {
            articles = articleService.articles(page,length,Article.ARTICLE_STATUS_SHOW);
            count = articleService.count(Article.ARTICLE_STATUS_SHOW);
        }
        articleService.clearPass(articles);
        PageHelper<Article> pageHelper = new PageHelper<>(articles,page,length,count);
        return pageHelper;
    }

    /**
     * 获取单个文章
     * @param article_id 文章id
     * @param access_pass 文章的访问密码，如果需要的话
     * @param pass_only 为true的话，仅验证密码，不返回文章
     * @return Article
     * @throws ArticleContentNotFountException
     * @throws ArticleNotFountException
     */
    @RequestMapping("/{article_id}")
    public Article article(@PathVariable int article_id,
                           @RequestParam(defaultValue = "") String access_pass,
                           @RequestParam(defaultValue = "false") boolean pass_only,
                           HttpServletResponse response) throws ArticleNotFountException, ArticleContentNotFountException, IOException {
        Article article = articleService.getArticle(article_id);
        if (article.getArticle_status()==Article.ARTICLE_STATUS_DRAFT) throw new ArticleNotFountException();
        if (article.getArticle_access_pass()==null||"".equals(article.getArticle_access_pass())){ //如果密码为空
             article.setArticle_access_pass("");
             articleService.addArticleContent(article);
             return f(article,pass_only,response);
        }
        if (articleService.checkArticleAccessAuthorized(article,access_pass)){
            articleService.setAccessAuthorized(article,access_pass,response);
            article.setArticle_access_pass("");
            articleService.addArticleContent(article);
            return f(article,pass_only,response);
        }else {
            article.setArticle_content(null);
            response.setStatus(403);
            article.setArticle_access_pass("");
            return f(article,pass_only,response);
        }
    }

    private Article f(Article article,boolean option,HttpServletResponse response) throws IOException {
        if (option){
            String referer = request.getHeader("Referer");
            if (StringUtils.getInstance().checkValidity(referer)){
                response.sendRedirect(referer);
            }
            return null;
        }
        return article;
    }
}
