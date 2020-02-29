package xyz.thetbw.blog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.thetbw.blog.data.constant.ConstValue;
import xyz.thetbw.blog.data.constant.ModelKey;
import xyz.thetbw.blog.data.dao.ArticleDao;
import xyz.thetbw.blog.data.dao.ArticleTagDao;
import xyz.thetbw.blog.data.dao.TagDao;
import xyz.thetbw.blog.data.entity.Article;
import xyz.thetbw.blog.data.entity.Tag;
import xyz.thetbw.blog.service.ArticleService;
import xyz.thetbw.blog.util.PageHelper;

import java.util.HashMap;
import java.util.List;

@Controller
public class TagController extends BaseController {


    @Autowired
    TagDao tagDao;

    @Autowired
    ArticleService articleService;

    @Autowired
    ArticleDao articleDao;

    @Autowired
    ArticleTagDao articleTagDao;

    @GetMapping("/tag")
    public String tag(Model model){
        List<Tag> tags =tagDao.getAll();
        model.addAttribute(ModelKey.TAGS,tags);
        return this.render(ConstValue.VIEW_TAG,model);
    }

    @GetMapping("/tag/{tag_id}")
    public String tag_articles(@PathVariable int tag_id, @RequestParam(defaultValue = "1") int page,Model model){
        Tag tag=tagDao.get(tag_id);
        if (tag==null)
            throw new ResourceNotFoundException("没有该标签");
        int length = ConstValue.MAX_TAG_ARTICLE_LENGTH;
        int start = (page-1)*length;
        List<Article> articles = articleTagDao.getArticleByTag(start,length,tag_id);
        PageHelper pageHelper = new PageHelper(articles,page,length,articleTagDao.getArticleCountByTag(tag_id));

        model.addAttribute(ModelKey.ARTICLES,articles);
        model.addAttribute(ModelKey.PAGINATION,pageHelper.genPaginations("/tag/"+tag_id,null,"page"));
        model.addAttribute(ModelKey.TAG,tag);
        return this.render(ConstValue.VIEW_TAG,model);
    }
}
