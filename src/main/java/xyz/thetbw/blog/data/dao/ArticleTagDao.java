package xyz.thetbw.blog.data.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import xyz.thetbw.blog.data.entity.Article;
import xyz.thetbw.blog.data.entity.Tag;

import java.util.List;

@Component
@Mapper
public interface ArticleTagDao {


    /**
     * 根据标签获取文章 只能获取已发布的文章，不能获取草稿文章
     * @param tag_id 标签id
     * @return
     */
    @Select("select count(1) from blog_article where article_id in( " +
            "select article_id from blog_article_tag where tag_id=#{tag_id})" +
            " and article_status=1 ")
    int getArticleCountByTag(int tag_id);

    /**
     *
     * @param article_id
     * @param tag_id
     * @return
     */
    @Select("select * from blog_tag where tag_id=( " +
            " select tag_id from blog_article_tag where article_id=#{article_id} and tag_id=#{tag_id} )")
    Tag getByArticleAndTag(int article_id, int tag_id);

    /**
     * 根据文章获取标签
     * @return
     */
    @Select("select * from blog_tag where  tag_id in(" +
            "select tag_id from blog_article_tag where article_id=#{article_id})")
    List<Tag> getByArticleID(int article_id);

    @Insert("insert into blog_article_tag(article_id,tag_id) values(#{article_id},#{tag_id})")
    void addArticleTag(int article_id, int tag_id);

    /**
     * 根据标签获取文章
     * @param index
     * @param length
     * @param article_tag_id
     * @return
     */
    @Select("select * from blog_article where article_id in " +
            "(select article_id from blog_article_tag where tag_id=#{article_tag_id}) " +
            "order by article_id limit #{index},#{length}")
    List<Article> getArticleByTag(int index , int length, int article_tag_id);

//    /**
//     * 删除某个文章标签对应
//     * @param article_tag_id
//     */
//    @Delete("delete from blog_article_tag where article_tag_id=#{article_tag_id}")
//    void deleteArticleTag(int article_tag_id);


    /**
     * 根据某个文章下某个标签
     * @param article_id
     * @param tag_id
     */
    @Delete("delete from blog_article_tag where article_id=#{article_id} and tag_id=#{tag_id}")
    void deleteArticleTagWithTag(int article_id, int tag_id);

    /**
     * 删除某个文章下所有标签
     * @param article_id
     */
    @Delete("delete from blog_article_tag where article_id=#{article_id}")
    void deleteArticleTagByArticle(int article_id);

    /**
     * 删除某个标签
     * @param tag_id
     */
    @Delete("delete from blog_article_tag where tag_id=#{tag_id}")
    void deleteArticleTagByTag(int tag_id);
}
