package xyz.thetbw.blog.data.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import xyz.thetbw.blog.data.entity.Comment;
import xyz.thetbw.blog.data.entity.User;

import java.util.List;

@Component
@Mapper
public interface CommentDao {


    @Select("select count(1) from blog_comment")
    int getCount();

    @Select("select count(1) from blog_comment where comment_status=#{filter}")
    int getCountByFilter(int filter);

    /**
     * 查询评论的子评论数量
     * @param root_comment_id
     * @return
     */
    @Select("select count(1) from blog_comment where comment_root_id=#{root_comment_id} and comment_status=1")
    int getCountOfRoot(int root_comment_id);

    /**
     * 查询文章的根评论数量
     * @param article_id
     * @return
     */
    @Select("select count(1) from blog_comment where comment_article_id=#{article_id}")
    int getCountOfArticleRoot(int article_id);



    @Select("select * from blog_comment")
    List<Comment> getAll();


    @Select("select * from blog_comment order by comment_create_time desc limit #{index},#{length} ")
     List<Comment> getAllPaging(int index, int length);

    @Select("select * from blog_comment where comment_parent_id=0  order by comment_create_time desc limit #{index},#{length} ")
     List<Comment> getAllRootPaging(int index, int length);

    @Select("select * from blog_comment where comment_status=#{filter} order by comment_create_time desc limit #{index},#{length} ")
     List<Comment> getAllPagingFilter(int index, int length, int filter);

    /**
     * 查询某个父评论下子评论
     * @param root_comment_id
     * @return
     */
    @Select("select * from blog_comment where comment_root_id=#{root_comment_id} and comment_status=1" +
            " order by comment_create_time desc limit #{index},#{length}")
    List<Comment> getCommentByRootPaging(int index, int length, int root_comment_id);


    /**
     * 查询某个父评论下子评论 和评论用户
     * @param index
     * @param length
     * @param root_comment_id
     * @return
     */
    @Select("select * from blog_comment where comment_root_id=#{root_comment_id} and comment_status=1" +
            " order by comment_create_time desc limit #{index},#{length}")
    @Results({
            @Result(property = "comment_id",column = "comment_id"),
            @Result(property = "comment_user",javaType = User.class,column = "comment_user_id",
            many = @Many(select = "xyz.thetbw.blog.data.dao.UserDao.get")),
            @Result(property = "comment_parent",javaType = Comment.class,column = "comment_id",
            many = @Many(select= "xyz.thetbw.blog.data.dao.CommentDao.getWithUser"))
    })
    List<Comment> getCommentByRootPagingWithUserAndParent(int index, int length, int root_comment_id);

    @Select("select * from blog_comment where comment_root_id=#{root_comment_id} and comment_status=1" +
            " order by comment_create_time desc")
    List<Comment> getCommentByRoot(int root_comment_id);

    @Insert("insert into blog_comment(comment_body,comment_user_id,comment_parent_id,comment_article_id,comment_status,comment_create_time,comment_root_id) values(" +
            "#{comment_body},#{comment_user_id},#{comment_parent_id},#{comment_article_id},#{comment_status},#{comment_create_time},#{comment_root_id})")
    @Options(useGeneratedKeys = true,keyProperty = "comment_id")
    void add(Comment o);

    /**
     * 暂不打算提供修改评论功能
     */
    @Update("update blog_comment set comment_status=#{comment_status} where comment_id=#{comment_id}")
    void update(Comment o);


    @Delete("delete from blog_comment where comment_id=#{comment_id}")
    void delete(int id);

    @Select("select * from blog_comment where comment_id=#{id}")
    Comment get(int id);


    @Select("select * from blog_comment where comment_id=#{id}")
    @Results({
            @Result(property = "comment_id", column = "comment_id"),
            @Result(property = "comment_user", javaType = User.class, column = "comment_user_id",
                    many = @Many(select = "xyz.thetbw.blog.data.dao.UserDao.get"))
    })
    Comment getWithUser(int id);



    /**
     * 查询某个文章下的所有评论
     * @param comment_article_id
     * @return
     */
    @Select("select * from blog_comment where comment_article_id=#{comment_article_id}" +
            " order by comment_create_time desc limit #{index},#{length}")
    List<Comment> getCommentByArticleID(int index, int length, int comment_article_id);

    /**
     * 查询某个文章的根评论
     * @param comment_article_id
     * @return
     */
    @Select("select * from blog_comment where comment_article_id=#{comment_article_id} and comment_root_id=0 and comment_status=1" +
            " order by comment_create_time desc limit #{index},#{length}")
    List<Comment> getRootCommentsByArticleID(int index, int length, int comment_article_id);



    /**
     * 获取根评论，并填充子评论
     *
     * @param index 根评论起始位置
     * @param length 分页长度
     * @param article_id 评论所属 文章id
     * @param child_index 子评论起始位置
     * @param children_length 子评论长度
     * @return
     */
    @Select("select #{child_index} arg1,#{children_length} arg2," +
            " comment_id,comment_body,comment_user_id,comment_root_id,comment_parent_id,comment_article_id,comment_status,comment_create_time " +
            " from blog_comment " +
            " where comment_article_id=#{article_id} and comment_root_id=0 and comment_status=1" +
            " order by comment_create_time desc limit #{index},#{length}")
    @Results(
            {
                @Result(property = "comment_id",column = "comment_id"),
                @Result(property = "comment_children",
                        column = "{index=arg1,length = arg2,root_comment_id=comment_id}",
                        javaType = List.class,
                        many = @Many(select = "xyz.thetbw.blog.data.dao.CommentDao.getCommentByRootPagingWithUserAndParent")),
//                @Result(property = "comment_user",javaType = User.class,column = "comment_user_id",
//                            many = @Many(select = "xyz.thetbw.blog.data.dao.UserDao.get"))
            }
    )
    List<Comment> getRootCommentsByArticleIDWithChildren(int index,int length,int article_id,int child_index,int children_length);




    /**
     * 查询未审核的评论
     * @return
     */
    @Select("select * from blog_comment where comment_status=0")
    List<Comment> getCheckedComments();


    /**
     * 删除所有审核中评论
     */
    @Delete("delete from blog_comment where comment_status=0")
    void deleteAllCheckedComments();

    /**
     * 删除某个文章下所有评论   暂保留
     * @param article_id
     */
    void deleteAllCommentsFromArticle(int article_id);



}
