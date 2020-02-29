package xyz.thetbw.blog.web.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.thetbw.blog.data.entity.Article;
import xyz.thetbw.blog.data.entity.Comment;
import xyz.thetbw.blog.data.model.admin.GeneralInfoModel;
import xyz.thetbw.blog.service.ArticleService;
import xyz.thetbw.blog.service.CommonService;
import xyz.thetbw.blog.service.CommentService;
import xyz.thetbw.blog.util.PageHelper;

import java.util.List;

@RestController
@RequestMapping("/admin/api/overview")
public class AdminOverViewController {

    @Autowired
    CommonService commonService;

    @Autowired
    CommentService commentService;

    @Autowired
    ArticleService articleService;

    @GetMapping("/getGeneralInfo")
    public Object getGeneralInfo(){
        GeneralInfoModel generalInfoModel = commonService.generalInfoModel();
        return generalInfoModel;
    }

    @GetMapping("/getNearArticles")
    public Object getNearArticles(@RequestParam(defaultValue = "10") Integer max_length){
        if (max_length==0)
            return null;
//        List<Article> articles =articleService.articles()
        PageHelper<Article> articlePageHelper = commonService.getArticles(1,max_length);
        return articlePageHelper;
    }

    @GetMapping("/getNearComments")
    public Object getNearComments(@RequestParam(defaultValue = "10") Integer max_length){
        List<Comment> comments=commentService.comments(1,max_length);
        PageHelper pageHelper = new PageHelper(comments,1,max_length,commentService.count());
        return pageHelper;
    }


}
