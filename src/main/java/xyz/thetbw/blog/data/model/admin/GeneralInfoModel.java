package xyz.thetbw.blog.data.model.admin;

/**
 * 系统基本信息模型
 */
public class GeneralInfoModel {
    private int article_count;
    private int comment_count;
    private int browsed_count;

    public int getArticle_count() {
        return article_count;
    }

    public void setArticle_count(int article_count) {
        this.article_count = article_count;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public int getBrowsed_count() {
        return browsed_count;
    }

    public void setBrowsed_count(int browsed_count) {
        this.browsed_count = browsed_count;
    }
}
