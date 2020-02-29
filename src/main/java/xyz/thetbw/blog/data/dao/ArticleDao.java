package xyz.thetbw.blog.data.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import xyz.thetbw.blog.data.entity.Article;

import javax.annotation.sql.DataSourceDefinition;
import java.util.List;

/**
 *
 */
@Component
@Mapper
public interface ArticleDao {


    @Update("update blog_article set article_browsed_count=article_browsed_count+1 where article_id=#{article_id}")
    void addBrowsedCount(int article_id);


    @Select("select count(1) from blog_article")
    int getCount();

    @Select("select count(1) from blog_article where article_status=#{status}")
    int getCountByStatus(int status);

    @Select("select count(1) from blog_article where article_status=1 and article_category_id=#{article_category_id}")
    int getCountByCategory(int article_category_id);


    @Select("select * from blog_article")
     List<Article> getAll() ;

    @Select("select * from blog_article where article_status=1 and article_category_id=#{article_category_id} limit #{index},#{length}")
    List<Article> getAllByCategory(int index ,int length,int article_category_id);





    @Select("select * from blog_article order by article_create_time desc" +
            " limit #{index},#{length}")
     List<Article> getAllPaging(int index, int length);


    @Select("select * from blog_article where article_status=#{filter} order by article_is_top desc,article_create_time desc" +
            " limit #{index},#{length}")
     List<Article> getAllPagingAndFilter(int index, int length, Integer filter) ;


    @Select("select * from blog_article where article_status=#{filter} order by #{order}" +
            " limit #{index},#{length}")
     List<Article> getAllPagingOrderAndFilter(int index, int length, Integer filter, String order);


    @Insert("insert into blog_article(article_preview,article_preview_text,article_title,article_create_time," +
            "article_update_time,article_user_id,article_browsed_count,article_comment_count,article_access_pass,article_is_top,article_cover," +
            "article_content_id,article_status,article_custom_url,article_category_id) " +
            "values(#{article_preview},#{article_preview_text},#{article_title},#{article_create_time},#{article_update_time}," +
            "#{article_user_id},#{article_browsed_count},#{article_comment_count},#{article_access_pass},#{article_is_top},#{article_cover}," +
            "#{article_content_id},#{article_status},#{article_custom_url},#{article_category_id})")
    @Options(useGeneratedKeys = true,keyProperty = "article_id")
    void add(Article o);


    @Update("update blog_article set article_preview=#{article_preview},article_preview_text=#{article_preview_text},article_title=#{article_title}," +
            "article_create_time=#{article_create_time},article_update_time=#{article_update_time},article_user_id=#{article_user_id}," +
            "article_browsed_count=#{article_browsed_count},article_comment_count=#{article_comment_count},article_access_pass=#{article_access_pass}," +
            "article_is_top=#{article_is_top},article_cover=#{article_cover},article_content_id=#{article_content_id},article_status=#{article_status}, " +
            "article_custom_url=#{article_custom_url},article_category_id=#{article_category_id} " +
            "where " +
            "article_id=#{article_id}")
    void update(Article o);


    @Update("update blog_article set article_comment_count=" +
            "(select count(1) from blog_comment where comment_article_id=#{article_id} and comment_status=1) where " +
            "article_id=#{article_id}")
    void updateArticleCommentNum(int article_id);



    @Delete("delete from blog_article where article_id=#{id}")
    void delete(int id);


    @Select("select * from blog_article where article_id=#{id}")
    Article get(int id);


    /**
     * 根据自定义url查询文章
     * @param article_custom_url
     * @return
     */
    @Select("select * from blog_article where article_custom_url=#{article_custom_url}")
    Article getByURL(String article_custom_url);



}
