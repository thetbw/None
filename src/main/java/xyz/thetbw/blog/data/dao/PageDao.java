package xyz.thetbw.blog.data.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import xyz.thetbw.blog.data.entity.Page;

import java.util.List;

@Component
@Mapper
public interface PageDao {


    @Select("select count(1) from blog_page")
     int getCount();


    @Select("select * from blog_page order by page_order")
     List<Page> getAll();

     List<Page> getAllPaging(int index, int length);


     List<Page> getAllPagingAndFilter(int index, int length, Integer filter);


     List<Page> getAllPagingOrderAndFilter(int index, int length, int filter, String order);


    @Insert("insert into blog_page(page_name,page_content_id,page_order,page_type,page_url) values(" +
            "#{page_name},#{page_content_id},#{page_order},#{page_type},#{page_url})")
    @Options(useGeneratedKeys = true,keyProperty = "page_id")
     void add(Page o) ;


    @Update("update blog_page set page_name=#{page_name},page_content_id=#{page_content_id},page_order=#{page_order}," +
            "page_type=#{page_type},page_url=#{page_url} " +
            "where page_id=#{page_id}")
     void update(Page o) ;



    @Update("<script> " +
            "<foreach collection='pages' item='item' index='index' separator=';'>" +
            "update blog_page " +
            "set page_name=#{item.page_name},page_content_id=#{item.page_content_id},page_order=#{item.page_order}," +
            "page_type=#{item.page_type},page_url=#{item.page_url} " +
            "where page_id=#{item.page_id}" +
            "</foreach>" +
            "</script>")
    void batchUpdate(@Param("pages") List<Page> pages);


    @Delete("delete from blog_page where page_id=#{id}")
     void delete(int id) ;


    @Select("select * from blog_page where page_id=#{id}")
     Page get(int id);

    @Select("select * from blog_page where page_url=#{page_url}")
    Page getPageByURL(String page_url);




}
