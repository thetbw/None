package xyz.thetbw.blog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.thetbw.blog.data.AppContext;
import xyz.thetbw.blog.data.dao.ArticleDao;
import xyz.thetbw.blog.data.dao.CommentDao;
import xyz.thetbw.blog.data.entity.Article;
import xyz.thetbw.blog.data.entity.Comment;
import xyz.thetbw.blog.data.entity.User;
import xyz.thetbw.blog.exception.CommentException;
import xyz.thetbw.blog.util.StringUtils;

import java.net.URL;
import java.util.List;

@Service
public class CommentService {
    private static final Logger LOG= LoggerFactory.getLogger(CommentService.class);

    @Autowired
    CommentDao commentDao;

    @Autowired
    ArticleDao articleDao;

    @Autowired
    UserService userService;

    /**
     * 发表评论，以当前登陆用户的身份
     */
    public void publishComment(Article article,Comment parent, String comment_body) throws CommentException{
        User user = userService.getUser();
        publishComment(article,user,parent,comment_body);
    }


    /**
     * 发表评论
     * @param article 评论所在文章，可无，和parent必须有一个
     * @param user 评论发表用户
     * @param parent 父评论，可无
     * @param comment_body 评论内容
     */
    public void publishComment(Article article, User user,Comment parent, String comment_body) throws CommentException {
        if (!AppContext.getInstance().setting.getCommentOpened())
            throw new CommentException("未开放评论");
        Comment comment = new Comment();
        comment.setComment_create_time(System.currentTimeMillis());
        if (user==null){
            if (AppContext.getInstance().setting.getCommentMustLogin())
                throw new CommentException("必须登陆才能评论");
        }else {
            comment.setComment_user_id(user.getUser_id());
            comment.setComment_user_nickname(user.getUser_nickname());
        }
        if (article!=null)
            comment.setComment_article_id(article.getArticle_id());
        else {
            throw new CommentException("没有为评论提供文章地址");
        }
        if (parent!=null) {
            comment.setComment_parent_id(parent.getComment_id());
            if (parent.getComment_root_id()==0)
                comment.setComment_root_id(parent.getComment_id());
            else comment.setComment_root_id(parent.getComment_root_id());
        }
        comment.setComment_body(StringUtils.getInstance().htmlTrans(comment_body));
        if (!AppContext.getInstance().setting.getCommentWithChecked())
            comment.setComment_status(Comment.COMMENT_STATUS_SHOW);
        else if (user!=null&&user.getUser_role()== User.USER_ROLE_ADMIN){
            comment.setComment_status(Comment.COMMENT_STATUS_SHOW);
        }
        /**
         * 逻辑检测，不能短时间发送多条评论
         */
        commentDao.add(comment);
        if (article!=null)
            articleDao.updateArticleCommentNum(article.getArticle_id());
        else
            articleDao.updateArticleCommentNum(parent.getComment_article_id());

    }

    public Comment getComment(int comment_id){
        return commentDao.get(comment_id);
    }

    /**
     * 获取所有评论的数量
     * @return
     */
    public int count(){
        return commentDao.getCount();
    }

    /**
     * 根据状态获取评论数量
     * @param filter 评论状态 (是否审核)
     * @return
     */
    public int count(int filter){
        return commentDao.getCountByFilter(filter);
    }

    /**
     * 根据父评论获取子评论数量
     * @param comment 父评论
     * @return
     */
    public int count(Comment comment){
        return commentDao.getCountOfRoot(comment.getComment_id());
    }

    /**
     * 获取文章下的根评论数量
     * @param article
     * @return
     */
    public int count(Article article){
        return commentDao.getCountOfArticleRoot(article.getArticle_id());
    }

    /**
     * 更新评论 (目前只更新评论状态)
     * @param comment
     */
    public void updateComment(Comment comment){
        commentDao.update(comment);
    }




    /**
     * 删除评论，子评论也会一并删除
     * @param comment
     */
    @Transactional
    public void deleteComments(Comment comment){
        if (comment==null) return;
        List<Comment> comments = comments(1, count(comment),comment);
        for (Comment c:comments){
            commentDao.delete(c.getComment_id());
            articleDao.updateArticleCommentNum(c.getComment_article_id());
        }
        commentDao.delete(comment.getComment_id());
        articleDao.updateArticleCommentNum(comment.getComment_article_id());
    }

    /**
     * 获取根评论的子评论
     * @param page
     * @param max_length
     * @param comment
     * @return
     */
    public List<Comment> comments(int page, int max_length, Comment comment) {
        if (comment==null) throw  new RuntimeException("根评论不能为空");
        int start = (page-1)*max_length;
        List<Comment> comments = commentDao.getCommentByRootPaging(start,max_length,comment.getComment_id());
        return comments;
    }

    /**
     * 获取文章下的根评论
     * @param page 分页
     * @param max_length 分页大小
     * @param article 文章
     * @return
     */
    public List<Comment> comments(int page, int max_length, Article article) {
        int start = (page-1)*max_length;
        List<Comment> comments = commentDao.getRootCommentsByArticleID(start,max_length,article.getArticle_id());
        return comments;
    }

    /**
     * 根据评论状态获取所有评论
     * @param page
     * @param max_length
     * @param status
     * @return
     */
    public List<Comment> comments(int page, int max_length, int status){
        int start = (page-1)*max_length;
        List<Comment> comments = commentDao.getAllPagingFilter(start,max_length,status);
        return comments;
    }

    /**
     * 获取所有评论
     * @param page
     * @param max_length
     * @return
     */
    public List<Comment> comments(int page, int max_length){
        int start = (page-1)*max_length;
        List<Comment> comments = commentDao.getAllPaging(start,max_length);
        return comments;
    }


    /**
     * 批量设置评论的子评论数量,不会迭代
     * @return comments的大小
     */
    public int addChildCount(List<Comment> comments){
        if (comments==null) return 0;
        for (Comment comment:comments){
            int mun = count(comment);
            comment.setComment_children_mun(mun);
        }
        return comments.size();
    }



    /**
     * 获取评论的用户信息
     * @param comment
     */
    public void addCommentUserInfo(Comment comment){
        User user =userService.getUser(comment.getComment_user_id());
        if (user==null){
            user = new User();
            user.setUser_nickname("user:null");
        }
        user.clearPass();
        comment.setComment_user(user);
        comment.setComment_user_nickname(user.getUser_nickname());
    }

    /**
     * 批量获取评论的用户信息
     * @param comments
     */
    public void addCommentUserInfo(List<Comment> comments){
        for (Comment comment:comments){
            addCommentUserInfo(comment);
        }
    }

}
