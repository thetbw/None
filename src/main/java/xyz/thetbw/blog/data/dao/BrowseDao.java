package xyz.thetbw.blog.data.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import xyz.thetbw.blog.data.entity.Browse;

@Component
@Mapper
public interface BrowseDao {

    @Insert("insert into blog_browsed(browsed_id,browsed_ip,browsed_position,browsed_user_id,browsed_time) " +
            "values(#{browsed_id},#{browsed_ip},#{browsed_position},#{browsed_user_id},#{browsed_time})")
    @Options(useGeneratedKeys = true,keyProperty = "browsed_id")
    void add(Browse browse);

    @Update("update blog_browsed set browsed_user_id=#{browsed_user_id} where browsed_id=#{browsed_id}")
    void update(Browse browse);



    @Select("select count(1) from blog_browsed where browsed_time between #{began} and #{end}")
    int getCount(long began,long end);

    @Select("select count(1) from blog_browsed")
    int getAllCount();

}
