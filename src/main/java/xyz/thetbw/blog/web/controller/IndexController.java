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
import xyz.thetbw.blog.service.ArticleService;
import xyz.thetbw.blog.service.CategoryService;
import xyz.thetbw.blog.util.PageHelper;

import java.util.List;

@Controller
public class IndexController extends BaseController {

    @Autowired
    ArticleDao articleDao;

    @Autowired
    ArticleService articleService;

    @Autowired
    CategoryService categoryService;



   @GetMapping("/")
    public String index(Model model, @RequestParam(defaultValue = "1") int page){
       int length = AppContext.getInstance().setting.getIndexPagingSize();
       List<Article> articles =articleService.articles(page,length,Article.ARTICLE_STATUS_SHOW);
       articles.forEach(a->articleService.addArticleCategory(a));
       PageHelper<Article> helper = new PageHelper<>(articles,page,length,articleService.count(Article.ARTICLE_STATUS_SHOW));
       model.addAttribute(ModelKey.ARTICLES,articles);
       model.addAttribute(ModelKey.PAGINATION,helper.genPaginations("/",null,"page"));

       return this.render(ConstValue.VIEW_INDEX,model);
    }

    @GetMapping("/index")
    public String getIndex(Model model,@RequestParam(defaultValue = "1") int page){
       return index(model,page);
    }

}
