package xyz.thetbw.blog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.thetbw.blog.data.AppContext;
import xyz.thetbw.blog.data.constant.ConstValue;
import xyz.thetbw.blog.data.constant.ModelKey;
import xyz.thetbw.blog.data.dao.ArticleDao;
import xyz.thetbw.blog.data.entity.Article;
import xyz.thetbw.blog.data.entity.Category;
import xyz.thetbw.blog.exception.CategoryNotFountException;
import xyz.thetbw.blog.service.ArticleService;
import xyz.thetbw.blog.service.CategoryService;
import xyz.thetbw.blog.util.PageHelper;

import java.util.HashMap;
import java.util.List;

@Controller
public class CategoryController extends BaseController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ArticleService articleService;


    @Autowired
    ArticleDao articleDao;

    @GetMapping("/category")
    public String category(@RequestParam(defaultValue = "0") int parent, @RequestParam(defaultValue = "1") int page, Model model){
        int length = ConstValue.MAX_CATEGORY_LENGTH;
        List<Category> categories = categoryService.categories(page,length);
        Category category = categoryService.getCategory(parent);
        PageHelper<Category> pageHelper = new PageHelper<>(categories,page,length,categoryService.count());

        List<Category> categories_tree = categoryService.getCategoriesTree();
        model.addAttribute(ModelKey.CATEGORY_TREE,categories_tree);
        model.addAttribute(ModelKey.CATEGORY_PAGINATION,pageHelper.genPaginations("/category",null,"page"));
        model.addAttribute(ModelKey.CATEGORY_LIST,categories);
        model.addAttribute(ModelKey.NOW_CATEGORY,category);

        return this.render(ConstValue.VIEW_CATEGORY);
    }

    @GetMapping("/category/{category_id}")
    public String category_articles(@PathVariable int category_id,@RequestParam(defaultValue = "1") int page,Model model) throws CategoryNotFountException {
        Category category = categoryService.getCategory(category_id);
        if (category==null)
            throw new CategoryNotFountException("没有该目录");
        int length = AppContext.getInstance().setting.getIndexPagingSize();
        List<Article> articles =articleService.articles(page,length,category);
        PageHelper<Article> pageHelper = new PageHelper<>(articles,page,length,articleDao.getCountByCategory(category_id));

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("category_id",category_id);

        model.addAttribute(ModelKey.NOW_CATEGORY,category);
        model.addAttribute(ModelKey.CATEGORY_ARTICLES,articles);
        model.addAttribute(ModelKey.CATEGORY_PAGINATION,pageHelper.genPaginations("/category",hashMap,"page"));
        return this.render(ConstValue.VIEW_CATEGORY);
    }
}
