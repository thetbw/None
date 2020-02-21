package xyz.thetbw.blog.data.entity;

import java.util.List;

public class Tag {

    public static final String TABLE_NAME="blog_tag";

    private int tag_id;
    private String tag_name;

    private int tag_article_num;
    private List<Article> tag_articles;

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public int getTag_article_num() {
        return tag_article_num;
    }

    public void setTag_article_num(int tag_article_num) {
        this.tag_article_num = tag_article_num;
    }

    public List<Article> getTag_articles() {
        return tag_articles;
    }

    public void setTag_articles(List<Article> tag_articles) {
        this.tag_articles = tag_articles;
    }

    public int getTag_id() {
        return tag_id;
    }

    public void setTag_id(int tag_id) {
        this.tag_id = tag_id;
    }
}
