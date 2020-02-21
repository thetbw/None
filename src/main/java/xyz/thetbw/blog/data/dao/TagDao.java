package xyz.thetbw.blog.data.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import xyz.thetbw.blog.data.entity.Tag;

import java.util.List;

@Component
@Mapper
public interface TagDao  {

    @Select("select count(1) from blog_tag")
     int getCount();


    @Select("select * from blog_tag")
     List<Tag> getAll();


    @Select("select * from blog_tag limit #{index},#{length}")
     List<Tag> getAllPaging(int index, int length);


     List<Tag> getAllPagingAndFilter(int index, int length, int filter);


     List<Tag> getAllPagingOrderAndFilter(int index, int length, int filter, String order);


    @Insert("insert into blog_tag(tag_name) values(#{tag_name})")
    @Options(useGeneratedKeys = true,keyProperty = "tag_id")
     void add(Tag o);

    @Update("update blog_tag set tag_name=#{tag_name} where tag_id=#{tag_id}")
     void update(Tag o);


    @Delete("delete from blog_tag where tag_id=#{id}")
     void delete(int id);


    @Select("select * from blog_tag where tag_id=#{id}")
     Tag get(int id) ;

    @Select("select * from blog_tag where tag_name=#{tag_name}")
    Tag getTagByName(String tag_name);



}
