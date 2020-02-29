package xyz.thetbw.blog.data.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import xyz.thetbw.blog.data.entity.Category;

import java.util.List;

@Component
@Mapper
public interface CategoryDao  {


    @Select("select count(1) from blog_category")
     int getCount();

    @Select("select count(1) from blog_category where category_parent_id=#{category_parent_id}")
    int getCountOfParent(int category_parent_id);

    @Select("select count(1) from blog_category where category_parent_id=0")
    int getCountOfRoot();





    @Select("select * from blog_category order by category_order")
    List<Category> getAll();

    @Select("select * from blog_category order by category_order limit #{index},#{length}")
     List<Category> getAllPaging(int index, int length);

//
//     List<Category> getAllPagingAndFilter(int index, int length, Integer filter);


    @Select("select * from blog_category where category_parent_id=0 order by order limit #{index},#{length}")
     List<Category> getAllPagingOrderAndFilter(int index, int length, Integer filter, String order);


    @Insert("insert into blog_category(category_name,category_order,category_parent_id,category_article_num) values(" +
            "#{category_name},#{category_order},#{category_parent_id},#{category_article_num})")
    @Options(useGeneratedKeys = true,keyProperty = "category_id")
    void add(Category o) ;


    @Update("update blog_category set category_name=#{category_name},category_order=#{category_order},category_parent_id=#{category_parent_id}," +
            "category_article_num=#{category_article_num} where category_id=#{category_id} ")
     void update(Category o) ;


    @Update("update blog_category set category_article_num=" +
            "(select count(1) from blog_article where article_category_id=#{category_id}) where " +
            "category_id = #{category_id}")
    void updateCategoryArticleNum(int category_id);


    @Delete("delete from blog_category where category_id=#{id}")
     void delete(int id);


    @Select("select * from blog_category where category_id=#{id} ")
     Category get(int id);


    @Select("select * from blog_category where category_name=#{category_name}")
    Category getByName(String category_name);



    /**
     * 查询根分类
     * @return
     */
    @Select("select * from blog_category where category_parent_id=0 order by category_order  limit #{index},#{length}")
    List<Category> selectRootCategory(int index,int length);


    @Select("select * from blog_category where category_parent_id=0")
    @Results({
            @Result(property = "category_id",column = "category_id"),
            @Result(property = "category_children",javaType = List.class,
                    column = "category_id" ,many =@Many(select="xyz.thetbw.blog.data.dao.CategoryDao.getCategoryTreeByParent"))
    })
    List<Category> getCategoryTree();

    @Select("select * from blog_category where category_parent_id=#{category_parent_id} order by category_order ")
    @Results({
            @Result(property = "category_id",column = "category_id"),
            @Result(property = "category_children",javaType = List.class,
                    column = "category_id" ,many =@Many(select="xyz.thetbw.blog.data.dao.CategoryDao.getCategoryTreeByParent"))
    })
    List<Category> getCategoryTreeByParent(int category_parent_id);

    @Select("select * from blog_category where category_parent_id=#{category_parent_id} order by category_order  limit #{index},#{length} ")
    List<Category> getCategoryByParentPaging(int index, int length, int category_parent_id);

    @Select("select * from blog_category where category_parent_id=#{category_parent_id} order by category_order ")
    List<Category> getCategoryByParent(int category_parent_id);








}
