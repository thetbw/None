package xyz.thetbw.blog.data.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import xyz.thetbw.blog.data.entity.UserSetting;

import java.util.List;

@Mapper
@Component
public interface UserSettingDao {

    @Select("select * from blog_user_setting where user_id=#{user_id}")
    List<UserSetting> getAllWithUser(int user_id);

    @Select("select * from blog_user_setting where user_id=#{user_id} and user_setting_key=#{key}")
    UserSetting getWithUserAndKey(int user_id, String key);

    @Insert("insert into blog_user_setting(user_id,user_setting_key,user_setting_value) values(" +
            "#{user_id},#{user_setting_key},#{user_setting_value})")
    void add(UserSetting userSetting);

    @Update("update blog_user_setting set user_setting_value=#{user_setting_value} where " +
            "user_id=#{user_id} and user_setting_key=#{user_setting_key}")
    void update(UserSetting userSetting);


    @Delete("delete from blog_user_setting where user_id=#{user_id} and user_setting_key=#{user_setting_key}")
    void delete(UserSetting userSetting);



}
