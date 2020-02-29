package xyz.thetbw.blog.data.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import xyz.thetbw.blog.data.entity.Property;

import java.util.List;

@Component
@Mapper
public interface PropertyDao  {


     int getCount() ;


     @Select("select * from blog_property")
     List<Property> getAll();


     List<Property> getAllPaging(int index, int length);


     List<Property> getAllPagingAndFilter(int index, int length, int filter) ;


     List<Property> getAllPagingOrderAndFilter(int index, int length, int filter, String order);


    @Insert("Insert into blog_property(property_key,property_value) values(" +
            "#{property_key},#{property_value})")
    @Options(useGeneratedKeys = true,keyProperty = "property_id")
     void add(Property o);


    @Update("update blog_property set property_key=#{property_key},property_value=#{property_value}" +
            "where property_id=#{property_id}")
     void update(Property o);


    @Delete("delete from blog_property where property_id=#{id}")
     void delete(int id) ;


    @Select("select * from blog_property where property_id=#{id}")
     Property get(int id) ;

    /**
     * 暂时用不到
     * @return
     */
    List<Property> selectAll();

    /**
     * 根据key获取值
     * @param property_key
     * @return
     */
    @Select("select * from blog_property where property_key=#{property_key}")
    Property getPropertyByKey(String property_key);


    @Delete("delete from blog_property where property_key=#{property_key}")
    void deletePropertyBYKey(String property_key);



}
