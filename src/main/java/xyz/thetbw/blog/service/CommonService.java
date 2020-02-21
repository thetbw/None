package xyz.thetbw.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.thetbw.blog.data.constant.FieldKey;
import xyz.thetbw.blog.data.dao.*;
import xyz.thetbw.blog.data.entity.*;
import xyz.thetbw.blog.data.model.admin.CategoryOptionModel;
import xyz.thetbw.blog.data.model.admin.GeneralInfoModel;
import xyz.thetbw.blog.util.PageHelper;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommonService {

    @Autowired
    ArticleDao articleDao;

    @Autowired
    CommentDao commentDao;

    @Autowired
    ContentDao contentDao;

    @Autowired
    PageDao pageDao;

    @Autowired
    CategoryDao categoryDao;

    @Autowired UserDao userDao;

    @Autowired
    PropertyDao propertyDao;

    @Autowired
    BrowseService browseService;

    /**
     * 获取博客基本信息
     */
    public GeneralInfoModel generalInfoModel(){
        int article_count = articleDao.getCount();
        int comment_count = commentDao.getCount();
        int browsed_count =browseService.count();
        GeneralInfoModel generalInfoModel = new GeneralInfoModel();
        generalInfoModel.setArticle_count(article_count);
        generalInfoModel.setComment_count(comment_count);
        generalInfoModel.setBrowsed_count(browsed_count);
        return generalInfoModel;
    }

    /**
     * 获取文章 按照时间排序
     * @param max_length
     * @return
     */
    public PageHelper<Article> getArticles(int index,int max_length){
        return getArticles(index,max_length,-1);
    }

    public PageHelper<Article> getArticles(int index,int max_length,int filter){
        int start = (index-1)*max_length;
        List<Article> articles;
        if (filter==-1)
            articles = articleDao.getAllPaging(start,max_length);
        else
            articles = articleDao.getAllPagingAndFilter(start,max_length,filter);
        for(Article article:articles){
            Category category = categoryDao.get(article.getArticle_category_id());
            if (category==null) continue;
            article.setArticle_category_name(category.getCategory_name());
        }
        PageHelper<Article> pageHelper= new PageHelper<>(articles,index,max_length,articleDao.getCount());
        return pageHelper;
    }





}
