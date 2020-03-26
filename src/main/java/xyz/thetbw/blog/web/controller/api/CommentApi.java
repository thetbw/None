package xyz.thetbw.blog.web.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.thetbw.blog.annotation.RequestLimit;
import xyz.thetbw.blog.data.AppContext;
import xyz.thetbw.blog.data.constant.ConstValue;
import xyz.thetbw.blog.data.entity.Article;
import xyz.thetbw.blog.data.entity.Comment;
import xyz.thetbw.blog.data.entity.User;
import xyz.thetbw.blog.exception.*;
import xyz.thetbw.blog.service.ArticleService;
import xyz.thetbw.blog.service.CommentService;
import xyz.thetbw.blog.service.UserService;
import xyz.thetbw.blog.util.PageHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentApi {

    @Autowired
    ArticleService articleService;

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @Autowired
    HttpServletRequest request;

    /**
     * 获取评论
     * @param article_id 文章id
     * @param root_id 父分类id
     * @param page 分页
     * @return
     */
    @GetMapping("/comments")
    public PageHelper<Comment> comments(@RequestParam(required = false) Integer article_id,
                                        @RequestParam Integer root_id,
                                        @RequestParam(defaultValue = "1") int page) throws ArticleNotFountException, CommentException {
        Article article=null ;

        Comment comment = null;
        if (article_id!=null) article = articleService.getArticle(article_id);
        if (root_id!=null) comment = commentService.getComment(root_id);
        if (article==null&&comment!=null)
            article = articleService.getArticle(comment.getComment_article_id());

        if (article==null) throw new ArticleNotFountException();
        if (comment!=null)
            if (article.getArticle_id()!=comment.getComment_article_id())
                throw new CommentException("文章和评论不一致");

        int length = ConstValue.MAX_COMMENTS_LENGTH;
        List<Comment> comments;
        int count;
        if (comment!=null) {
            comments = commentService.comments(page, length, comment);
            count = commentService.count(comment);
        } else {
            comments = commentService.comments(page,length,article);
            count = commentService.count(article);
        }
        commentService.addCommentUserInfo(comments);
        PageHelper<Comment> commentPageHelper = new PageHelper<>(comments,page,length,count);
        return commentPageHelper;
    }

    /**
     * 发表评论
     * @param article_id 评论的文章id 和root_id必须有一个
     * @param parent_id 父评论id,没有的话默认为根评论
     * @param comment_body 评论内容
     * @param user_nickname 用户昵称，游客评论需要这个
     * @param user_email 用户邮箱，游客评论需要这个
     * @throws RequestException
     */
    @PostMapping("/comment")
    @RequestLimit(count = 2)
    public void publishComment(@RequestParam(required = false) Integer article_id,
                               @RequestParam(required = false) Integer parent_id,
                               @RequestParam String comment_body,
                               @RequestParam(defaultValue = "") String user_nickname,
                               @RequestParam(defaultValue = "") String user_email,
                               HttpServletResponse response) throws RequestException {
        if (!AppContext.getInstance().setting.getCommentOpened()) throw new CommentException("评论功能已关闭");
        User user = userService.getUser();
        if ((parent_id!=null&&user==null) ||
                (parent_id!=null&&user!=null&&user.getUser_role()==User.USER_ROLE_GUEST)){
            throw new CommentAcessException("禁止游客回复其他用户评论");
        }
        if (user==null){
            if (AppContext.getInstance().setting.getCommentMustLogin())
                throw new CommentAcessException("你没有足够的权限评论");
            else  {
                user = userService.createGuestUser(user_nickname,user_email);
            }
        }

        Article article = null;
        Comment comment = null;
        if (parent_id!=null){
            comment=commentService.getComment(parent_id);
            if (comment==null) throw new CommentException("没有此评论");
        }
        if (article_id!=null){
            article = articleService.getArticle(article_id);
            if (article==null) throw new ArticleNotFountException();
        }else {
            article = articleService.getArticle(comment.getComment_article_id());
            if (article==null) throw new ArticleNotFountException();
        }
        if (comment==null&&article==null)
            throw new RequestException("请求错误，必须提供正确的文章地址或父评论");

        commentService.publishComment(article,user,comment,comment_body);
        userService.addUserToCookie(user,response);
    }
}
