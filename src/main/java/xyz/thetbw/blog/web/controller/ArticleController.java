package xyz.thetbw.blog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import xyz.thetbw.blog.data.AppContext;
import xyz.thetbw.blog.data.constant.ConstValue;
import xyz.thetbw.blog.data.constant.ModelKey;
import xyz.thetbw.blog.data.entity.Article;
import xyz.thetbw.blog.data.entity.Comment;
import xyz.thetbw.blog.data.entity.Content;
import xyz.thetbw.blog.data.entity.User;
import xyz.thetbw.blog.exception.*;
import xyz.thetbw.blog.service.ArticleService;
import xyz.thetbw.blog.service.CommentService;
import xyz.thetbw.blog.service.UserService;
import xyz.thetbw.blog.util.MarkDownUtils;
import xyz.thetbw.blog.util.PageHelper;
import xyz.thetbw.blog.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class ArticleController extends BaseController {

    @Autowired
    ArticleService articleService;

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;


    @GetMapping("/article/{article_id}")
    public String article(Model model,
                          @PathVariable int article_id,
                          @RequestParam(defaultValue = "1") int page ) throws ArticleContentNotFountException, ArticleNotFountException {
        Article article;
        article = articleService.getArticle(article_id);
        if (article==null)
            throw new ArticleNotFountException("没有找到该文章");
        if (articleService.checkArticleAccessAuthorized(article,"")) {
            articleService.addArticleContent(article);
            String html = MarkDownUtils.getInstance().markdown2Html(article.getArticle_content().getContent_body());
            article.getArticle_content().setContent_body(html);
            model.addAttribute(ModelKey.ARTICLE,article);
            model.addAttribute(ModelKey.NEED_PASS,false);
        } else {
            article.clearPass();
            model.addAttribute(ModelKey.ARTICLE,article);
            model.addAttribute(ModelKey.NEED_PASS,true);
        }
        articleService.addArticleTags(article);
        int length  = ConstValue.MAX_COMMENTS_LENGTH;
        List<Comment> comments = commentService.comments(page,length,article);
        commentService.addCommentUserInfo(comments);
        commentService.addChildCount(comments);
        PageHelper<Comment> helper = new PageHelper<>(comments,page,length,commentService.count(article));

        model.addAttribute(ModelKey.ARTICLE, article);
        model.addAttribute(ModelKey.COMMENTS,comments);
        model.addAttribute(ModelKey.PAGINATION,helper.genPaginations("/article/"+article_id,null,"page"));

        return this.render(ConstValue.VIEW_ARTICLE,model);
    }




//    @PostMapping("/article/{article_id}/comment")
//    @Transactional
//    public Object comment(@RequestParam(defaultValue = "") String email,
////                          @RequestParam(defaultValue = "") String nick_name,
//                          @RequestParam String text,
//                          @RequestParam(defaultValue = "0") int parent_id,
//                          @PathVariable int article_id,
//                          HttpServletResponse httpServletResponse)
//            throws CommentException, IOException, UserCreateException, ArticleContentNotFountException,
//            ArticleNotFountException, StringNotExistException, StringIsNoneException, UserAlreadyExitsException {
//        User user =userService.getUser();
//        if (parent_id!=0&&user==null)
//            throw new CommentException("非登陆用户禁止评论别人评论");
//        Article article =articleService.getArticle(article_id);
//        Comment parent  =commentService.getComment(parent_id);
//        String body = StringUtils.getInstance().validString(text);
//        if (user==null){
//            if (AppContext.getInstance().setting.getCommentMustLogin()){
//                throw new CommentAcessException();
//            }else {
//                handleGuestComment(parent,"",email,body,article,httpServletResponse);
//            }
//        }else {
//            handleUserComment(parent,body,user,article);
//        }
//        httpServletResponse.sendRedirect("/article/"+article_id);
//        return null;
//    }
//
//    @GetMapping("/article/{article_id}/comment")
//    @ResponseBody
//    public Object comments(@PathVariable int article_id,
//                           @RequestParam int page,
//                           @RequestParam int root_comment) throws ArticleNotFountException {
//        Article article = articleService.getArticle(article_id);
//        if (article==null) throw new ArticleNotFountException();
//        int length = ConstValue.MAX_COMMENTS_LENGTH;
//        Comment root = commentService.getComment(root_comment);
//        List<Comment> comments = commentService.comments(page,length,root);
//        commentService.addCommentUserInfo(comments);
//        comments.forEach(comment -> {
//            if (comment.getComment_parent_id()==0) return;
//            if (comment.getComment_parent_id()==root_comment) return;
//            Comment parent = commentService.getComment(comment.getComment_parent_id());
//            commentService.addCommentUserInfo(parent);
//            comment.setComment_parent(parent);
//        });
//
//
//
//        PageHelper<Comment> commentPageHelper = new PageHelper<>(comments,page,length,commentService.count(root));
//        return commentPageHelper;
//    }
//
//    /**
//     * 处理登陆用户的评论
//     * @return
//     */
//    private Object handleUserComment(Comment parent,String text,User user,Article article) throws CommentException {
//        commentService.publishComment(article,user,parent,text);
//        return null;
//    }
//    /**
//     * 处理游客评论
//     * @return
//     */
//    private Object handleGuestComment(Comment parent,String nick_name,String email,String text,Article article,HttpServletResponse response) throws UserCreateException, CommentException, UserAlreadyExitsException, StringNotExistException, StringIsNoneException {
//        User user = userService.createGuestUser(nick_name,email);
//        userService.addUserToCookie(user,response);
//        commentService.publishComment(article,user,parent,text);
//        return null;
//    }

}
