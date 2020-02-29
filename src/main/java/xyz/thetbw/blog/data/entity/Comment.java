package xyz.thetbw.blog.data.entity;

import java.util.List;

/**
 * @author thetbw
 */
public class Comment {

    public static final String TABLE_NAME="blog_comment";
    /**
     * 审核中
     */
    public static final int COMMENT_STATUS_CHECKING = 0;
    /**
     * 显示
     */
    public static final int COMMENT_STATUS_SHOW = 1;

    private int comment_id;
    private String comment_body;
    private int comment_user_id;
    private int comment_root_id;
    private int comment_parent_id;
    private int comment_article_id;
    private int comment_status;
    private long comment_create_time;


    private User comment_user;
    private String comment_user_nickname;
    private Comment comment_parent;
    private Article comment_article;

    private int comment_children_mun;

    private List<Comment> comment_children;


    public int getComment_children_mun() {
        return comment_children_mun;
    }

    public void setComment_children_mun(int comment_children_mun) {
        this.comment_children_mun = comment_children_mun;
    }

    public int getComment_root_id() {
        return comment_root_id;
    }

    public void setComment_root_id(int comment_root_id) {
        this.comment_root_id = comment_root_id;
    }

    public long getComment_create_time() {
        return comment_create_time;
    }

    public void setComment_create_time(long comment_create_time) {
        this.comment_create_time = comment_create_time;
    }

    public String getComment_user_nickname() {
        return comment_user_nickname;
    }

    public void setComment_user_nickname(String comment_user_nickname) {
        this.comment_user_nickname = comment_user_nickname;
    }

    public List<Comment> getComment_children() {
        return comment_children;
    }

    public void setComment_children(List<Comment> comment_children) {
        this.comment_children = comment_children;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public String getComment_body() {
        return comment_body;
    }

    public void setComment_body(String comment_body) {
        this.comment_body = comment_body;
    }

    public int getComment_user_id() {
        return comment_user_id;
    }

    public void setComment_user_id(int comment_user_id) {
        this.comment_user_id = comment_user_id;
    }

    public int getComment_parent_id() {
        return comment_parent_id;
    }

    public void setComment_parent_id(int comment_parent_id) {
        this.comment_parent_id = comment_parent_id;
    }

    public int getComment_article_id() {
        return comment_article_id;
    }

    public void setComment_article_id(int comment_article_id) {
        this.comment_article_id = comment_article_id;
    }

    public int getComment_status() {
        return comment_status;
    }

    public void setComment_status(int comment_status) {
        this.comment_status = comment_status;
    }

    public User getComment_user() {
        return comment_user;
    }

    public void setComment_user(User comment_user) {
        this.comment_user = comment_user;
    }

    public Comment getComment_parent() {
        return comment_parent;
    }

    public void setComment_parent(Comment comment_parent) {
        this.comment_parent = comment_parent;
    }

    public Article getComment_article() {
        return comment_article;
    }

    public void setComment_article(Article comment_article) {
        this.comment_article = comment_article;
    }
}
