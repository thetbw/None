package xyz.thetbw.blog.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import xyz.thetbw.blog.data.AppContext;
import xyz.thetbw.blog.data.constant.ConstValue;
import xyz.thetbw.blog.data.constant.ModelKey;
import xyz.thetbw.blog.data.dao.ArticleDao;
import xyz.thetbw.blog.data.entity.Article;
import xyz.thetbw.blog.util.PageHelper;

import java.util.List;

@Controller
public class IndexController extends BaseController {

    @Autowired
    ArticleDao articleDao;



   @GetMapping("/")
    public String index(Model model, Integer page){
       if (page==null) page=1;
       PageHelper<Article> articles = getIndexArticles(page);
       model.addAttribute(ModelKey.ARTICLE_LIST,articles.getPageItems());
       model.addAttribute(ModelKey.ARTICLE_PAGINATION,articles.getPageIndexes());
       model.addAttribute(ModelKey.NOW_PAGE,articles.getCurrentPageNum());

       return this.render(ConstValue.VIEW_INDEX);
    }

    @GetMapping("/index")
    public String getIndex(Model model,@RequestParam(defaultValue = "1") int page){
       return index(model,page);
    }

    @PostMapping("/index")
    @ResponseBody
    public PageHelper<Article> postIndex(@RequestParam(defaultValue = "1") int page){
       return getIndexArticles(page);
    }


    /**
     * 获取首页要展示的文章
     * @param page 分页
     * @return
     */
    public PageHelper<Article> getIndexArticles(int page){
        int lenght = AppContext.getInstance().setting.getIndexPagingSize();
        int start = (page-1)*lenght;
        List<Article> articles = articleDao.getAllPagingAndFilter(start,lenght,Article.ARTICLE_STATUS_SHOW);
        PageHelper<Article> pageHelper = new PageHelper<>(articles,page,lenght,articleDao.getCountByStatus(Article.ARTICLE_STATUS_SHOW));
        return pageHelper;
    }


}
