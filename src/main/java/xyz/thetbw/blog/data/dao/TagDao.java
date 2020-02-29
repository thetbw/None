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



    @Insert("insert into blog_tag(tag_name,tag_article_num) values(#{tag_name},#{tag_article_num})")
    @Options(useGeneratedKeys = true,keyProperty = "tag_id")
     void add(Tag o);

    @Update("update blog_tag set tag_name=#{tag_name} where tag_id=#{tag_id}")
     void update(Tag o);

    @Update("update blog_tag set tag_article_num=" +
            "(select count(1) from blog_article_tag where tag_id=#{tag_id}) where " +
            "tag_id = #{tag_id}")
    void updateTagArticleNum(int tag_id);

    @Delete("delete from blog_tag where tag_id=#{id}")
     void delete(int id);


    @Select("select * from blog_tag where tag_id=#{id}")
     Tag get(int id) ;

    @Select("select * from blog_tag where tag_name=#{tag_name}")
    Tag getTagByName(String tag_name);



}
