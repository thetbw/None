package xyz.thetbw.blog.data.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import xyz.thetbw.blog.data.entity.User;

import java.util.List;

@Component
@Mapper
public interface UserDao {

     int getCount();

     @Select("select * from blog_user where user_role=0 order by user_id limit 1")
     User getAdminUser();

     List<User> getAll();


     List<User> getAllPaging(int index, int length) ;


     List<User> getAllPagingAndFilter(int index, int length, int filter);


     List<User> getAllPagingOrderAndFilter(int index, int length, int filter, String order);


    @Insert("insert into blog_user(user_name,user_nickname,user_pass,user_role,user_avatar_url,user_email) values(" +
            "#{user_name},#{user_nickname},#{user_pass},#{user_role},#{user_avatar_url},#{user_email})")
    @Options(useGeneratedKeys = true,keyProperty = "user_id")
     void add(User o);


    @Update("update blog_user set user_name=#{user_name},user_nickname=#{user_nickname},user_pass=#{user_pass}," +
            "user_role=#{user_role},user_avatar_url=#{user_avatar_url},user_email=#{user_email} where user_id=#{user_id}")
     void update(User o) ;


    @Delete("delete from blog_user where user_id=#{id}")
     void delete(int id) ;


    @Select("select * from blog_user where user_id =#{id}")
     User get(int id);

    @Select("select * from blog_user where user_role=#{user_role}")
    List<User> selectAllUserByRole(int user_role);

    @Select("select * from blog_user where user_name =#{user_name}")
    User getUserByName(String user_name);

    @Select("select * from blog_user where user_nickname =#{user_nickname}")
    User getUserByNickName(String user_nickname);

    /**
     * 根据角色来批量删除用户
     */
    @Delete("delete from blog_user where user_role=#{user_role}")
    void deleteUserByRole(int user_role);


}
