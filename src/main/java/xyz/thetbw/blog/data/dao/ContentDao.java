package xyz.thetbw.blog.data.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import xyz.thetbw.blog.data.entity.Content;

import java.util.List;

@Component
@Mapper
public interface ContentDao {


    @Select("select count(1) from blog_content")
     int getCount();


     List<Content> getAll();


     List<Content> getAllPaging(int index, int length);


     List<Content> getAllPagingAndFilter(int index, int length, Integer filter);


     List<Content> getAllPagingOrderAndFilter(int index, int length, Integer filter, String order);


    @Insert("insert into blog_content(content_body,content_body_text,content_type,content_rollback_flag,content_describe) values(" +
            "#{content_body},#{content_body_text},#{content_type},#{content_rollback_flag},#{content_describe})")
    @Options(useGeneratedKeys = true,keyProperty ="content_id" )
     void add(Content o);


    @Update("update blog_content set content_body=#{content_body},content_body_text=#{content_body_text},content_type=#{content_type}," +
            "content_rollback_flag=#{content_rollback_flag},content_describe=#{content_describe} where content_id=#{content_id}")
     void update(Content o);


    @Delete("delete from blog_content where content_id=#{id}")
     void delete(int id) ;


    @Select("select * from blog_content where content_id=#{content_id}")
     Content get(int id);





}
