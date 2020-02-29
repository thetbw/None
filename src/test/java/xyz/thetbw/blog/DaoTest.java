package xyz.thetbw.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.thetbw.blog.data.dao.*;
import xyz.thetbw.blog.data.entity.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@SpringBootTest
public class DaoTest {
    @Autowired
    ArticleDao articleDao;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    UserDao userDao;

    @Autowired
    PageDao pageDao;

    @Autowired
    CommentDao commentDao;


    @Test
    void addUser(){
//        User user = new User();
//        user.setUser_name("thetbw");
//        user.setUser_nickname("黑羽");
//        user.setUser_pass(232473514);
//        user.setUser_avatar_url("/cover.png");
//        user.setUser_email("thetbw@outlook.com");
//        user.setUser_role(User.USER_ROLE_ADMIN);
//        userDao.add(user);
    }

    @Test
    void ArticleSelect(){
//        List<Article> articles = articleDao.getAll();
//        for (Article a:articles){
//            System.out.println(a);
//        }
    }

    @Test
    void categoryTreeTest(){
//        List<Category> categories = categoryDao.getCategoryTree();
//        for (Category c:categories){
//            System.out.println(c);
//        }
    }
    @Test
    void ArticleAdd(){
//        Article article = new Article();
//        article.setArticle_title("这是一个测试文章");
//        article.setArticle_create_time(System.nanoTime());
//        article.setArticle_browsed_count(0);
//        article.setArticle_comment_count(0);
//        article.setArticle_is_top(0);
//        article.setArticle_content_id(0);
//        articleDao.add(article);

    }

    @Test
    void CategoryAdd(){
//        Category category = new Category();
//        category.setCategory_name("父分类1");
//        category.setCategory_order(1);
//        categoryDao.add(category);
//        Category category1 = new Category();
//        category1.setCategory_name("父分类2");
//        category1.setCategory_order(1);
//        categoryDao.add(category1);
//        Category category2 = new Category();
//        category2.setCategory_name("子分类1");
//        category2.setCategory_order(1);
//        category2.setCategory_parent(category);
//        categoryDao.add(category2);
//        Category category3 = new Category();
//        category3.setCategory_name("子分类2");
//        category3.setCategory_parent(category);
//        category3.setCategory_order(1);
//        categoryDao.add(category3);
    }

    @Test
    void selectCategoryTree(){
//        List<Category> list = categoryDao.getRootCategoryWithChildren();
//        for (Category c :list) {
//            System.out.println(c);
//        }
    }


    @Test
    void batchUpdateTest(){
//        ArrayList<Page> pages = new ArrayList<>();
//        pages.add(pageDao.get(1));
//        pages.add(pageDao.get(2));
//        pages.add(pageDao.get(4));
//        pages.get(0).setPage_content_id(0);
//        pages.get(0).setPage_url("/");
//        pages.get(1).setPage_content_id(0);
//        pages.get(1).setPage_url("/categories");
//        pages.get(2).setPage_content_id(0);
//        pages.get(2).setPage_url("/tags");
//        pageDao.batchUpdate(pages);
    }

    @Test
    public void listSortTest(){
        List<Page> pages = pageDao.getAll();
        pages.forEach(System.out::println);
        System.out.println();
        pages.sort(Comparator.comparing(Page::getPage_order));
        pages.forEach(System.out::println);

    }


    @Test
    public void addComment(){
        Comment comment = new Comment();
        comment.setComment_status(Comment.COMMENT_STATUS_CHECKING);
        comment.setComment_article_id(21);
        comment.setComment_body("哎呦不错呦");
        comment.setComment_user_id(1);
        commentDao.add(comment);

    }

}
