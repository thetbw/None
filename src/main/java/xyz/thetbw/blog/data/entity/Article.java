package xyz.thetbw.blog.data.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author thetbw
 */
public class Article {
    /**
     * 表名
     */
    public static final String TABLE_NAME="blog_article";

    /**
     * 草稿
     */
    public static final int ARTICLE_STATUS_DRAFT=0;
    /**
     * 已发布的
     */
    public static final int ARTICLE_STATUS_SHOW=1;



    private int article_id;
    private String article_preview;
    private String article_preview_text;


    private String article_title;
    private long article_create_time;
    private long article_update_time;

    /**
     * 文章的作者，因为是个人博客，所以这个可能不太必要，
     * 只是为了后续可能的多人编辑
     */
    private int article_user_id;

    private int article_browsed_count;
    private int article_comment_count;
    private String article_access_pass;

    private int article_is_top;
    private String article_cover;

    private int article_content_id;
    private int article_status;

    private int article_category_id; //文章分类

    //-----------------------
    private String article_custom_url;  //博客自定义链接


    private User article_user;
    private Content article_content;
    private String article_category_name;

    private List<Comment> article_comments;
    private List<Tag> article_tags;


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("article_id:").append(article_id).append("\t")
                .append("article_title:").append(article_title).append("\t");
        return stringBuilder.toString();
    }

    public Article clearPass(){
        this.article_access_pass="";
        return this;
    }

    public String getArticle_category_name() {
        return article_category_name;
    }

    public void setArticle_category_name(String article_category_name) {
        this.article_category_name = article_category_name;
    }

    public int getArticle_category_id() {
        return article_category_id;
    }

    public void setArticle_category_id(int article_category_id) {
        this.article_category_id = article_category_id;
    }

    public String getArticle_custom_url() {
        return article_custom_url;
    }

    public void setArticle_custom_url(String article_custom_url) {
        this.article_custom_url = article_custom_url;
    }

    public List<Tag> getArticle_tags() {
        return article_tags;
    }

    public void setArticle_tags(List<Tag> article_tags) {
        this.article_tags = article_tags;
    }

    public List<Comment> getArticle_comments() {
        return article_comments;
    }

    public void setArticle_comments(List<Comment> article_comments) {
        this.article_comments = article_comments;
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public String getArticle_preview() {
        return article_preview;
    }

    public void setArticle_preview(String article_preview) {
        this.article_preview = article_preview;
    }

    public String getArticle_preview_text() {
        return article_preview_text;
    }

    public void setArticle_preview_text(String article_preview_text) {
        this.article_preview_text = article_preview_text;
    }

    public String getArticle_title() {
        return article_title;
    }

    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }

    public long getArticle_create_time() {
        return article_create_time;
    }

    public void setArticle_create_time(long article_create_time) {
        this.article_create_time = article_create_time;
    }

    public long getArticle_update_time() {
        return article_update_time;
    }

    public void setArticle_update_time(long article_update_time) {
        this.article_update_time = article_update_time;
    }

    public int getArticle_user_id() {
        return article_user_id;
    }

    public void setArticle_user_id(int article_user_id) {
        this.article_user_id = article_user_id;
    }

    public int getArticle_browsed_count() {
        return article_browsed_count;
    }

    public void setArticle_browsed_count(int article_browsed_count) {
        this.article_browsed_count = article_browsed_count;
    }

    public int getArticle_comment_count() {
        return article_comment_count;
    }

    public void setArticle_comment_count(int article_comment_count) {
        this.article_comment_count = article_comment_count;
    }

    public String getArticle_access_pass() {
        return article_access_pass;
    }

    public void setArticle_access_pass(String article_access_pass) {
        this.article_access_pass = article_access_pass;
    }

    public int getArticle_is_top() {
        return article_is_top;
    }

    public void setArticle_is_top(int article_is_top) {
        this.article_is_top = article_is_top;
    }

    public String getArticle_cover() {
        return article_cover;
    }

    public void setArticle_cover(String article_cover) {
        this.article_cover = article_cover;
    }

    public int getArticle_content_id() {
        return article_content_id;
    }

    public void setArticle_content_id(int article_content_id) {
        this.article_content_id = article_content_id;
    }

    public int getArticle_status() {
        return article_status;
    }

    public void setArticle_status(int article_status) {
        this.article_status = article_status;
    }

    public User getArticle_user() {
        return article_user;
    }

    public void setArticle_user(User article_user) {
        this.article_user = article_user;
    }

    public Content getArticle_content() {
        return article_content;
    }

    public void setArticle_content(Content article_content) {
        this.article_content = article_content;
    }
}
