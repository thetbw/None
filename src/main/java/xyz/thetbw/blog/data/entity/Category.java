package xyz.thetbw.blog.data.entity;

import java.util.List;

/**
 * @author thetbw
 */
public class Category {

    public static final String TABLE_NAME="blog_category";

    private int category_id;
    private String category_name;
    private int category_order;
    private int category_parent_id;
    private int category_article_num;

    private Category category_parent;

    private List<Article> category_articles;
    private List<Category> category_children;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\t").append("category_id: ").append(category_id).append("\tcategory_name: ")
                .append(category_name);
        if (category_children!=null){
            for (Category category:category_children){
                builder.append("\n\t").append(category.toString());
            }
        }
        return builder.toString();
    }

    public List<Category> getCategory_children() {
        return category_children;
    }

    public void setCategory_children(List<Category> category_children) {
        this.category_children = category_children;
    }

    public List<Article> getCategory_articles() {
        return category_articles;
    }

    public void setCategory_articles(List<Article> category_articles) {
        this.category_articles = category_articles;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public int getCategory_order() {
        return category_order;
    }

    public void setCategory_order(int category_order) {
        this.category_order = category_order;
    }

    public int getCategory_parent_id() {
        return category_parent_id;
    }

    public void setCategory_parent_id(int category_parent_id) {
        this.category_parent_id = category_parent_id;
    }

    public int getCategory_article_num() {
        return category_article_num;
    }

    public void setCategory_article_num(int category_article_num) {
        this.category_article_num = category_article_num;
    }

    public Category getCategory_parent() {
        return category_parent;
    }

    public void setCategory_parent(Category category_parent) {
        this.setCategory_parent_id(category_parent.getCategory_id());
        this.category_parent = category_parent;
    }
}
