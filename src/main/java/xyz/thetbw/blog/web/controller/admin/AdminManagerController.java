package xyz.thetbw.blog.web.controller.admin;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import xyz.thetbw.blog.data.dao.*;
import xyz.thetbw.blog.data.entity.*;
import xyz.thetbw.blog.exception.RequestException;
import xyz.thetbw.blog.service.*;
import xyz.thetbw.blog.util.PageHelper;

import java.util.List;


@RestController
@RequestMapping("/admin/api/manager")
public class AdminManagerController {

    @Autowired
    CommonService commonService;

    @Autowired ArticleService articleService;

    @Autowired CategoryService categoryService;

    @Autowired CommentService commentService;

    @Autowired ArticleDao articleDao;

    @Autowired PageService pageService;

    @Autowired TagDao tagDao;

    @Autowired ArticleTagDao articleTagDao;

    @Autowired CategoryDao categoryDao;

    @Autowired PageDao pageDao;

    @Autowired ContentDao contentDao;

    @Autowired UserDao userDao;

    @GetMapping("/article/getArticleList")
    public Object getArticleList(@RequestParam int page, @RequestParam int max_page_length, @RequestParam int filter){
        return commonService.getArticles(page,max_page_length,filter);
    }

    @PostMapping("/article/deleteArticle")
    @Transactional
    public Object deleteArticle(@RequestParam int article_id){
        contentDao.delete(articleDao.get(article_id).getArticle_content_id()); //存在空指针异常
        articleDao.delete(article_id);
        return null;
    }

    @PostMapping("/article/switchArticleStatus")
    public Object switchArticleStatus(@RequestParam int article_id,@RequestParam int article_status){
        Article article = articleDao.get(article_id);
        if (article==null)
            return new RuntimeException("未找到文章");
        article.setArticle_status(article_status);
        articleDao.update(article);
        return null;
    }

    @GetMapping("/comment/getCommentList")
    public Object getCommentList(@RequestParam int page, @RequestParam int max_page_length, @RequestParam int filter){
        PageHelper<Comment> pageHelper;
        int start = (page-1)*max_page_length;
        List<Comment> commentList = commentService.comments(page,max_page_length,filter);
        commentService.addCommentUserInfo(commentList);
        pageHelper = new PageHelper<>(commentList,page,max_page_length,commentService.count(filter));
        return pageHelper;
    }

    @PostMapping("/comment/deleteComment")
    public Object deleteComment(@RequestParam int comment_id){
        commentService.deleteComments(commentService.getComment(comment_id));
        return null;
    }

    /**
     * 注：性能问题？
     * @param comment_id
     * @param comment_status
     * @return
     */
    @PostMapping("/comment/switchCommentStatus")
    public Object switchCommentStatus(@RequestParam int comment_id,@RequestParam int comment_status){
        Comment comment =commentService.getComment(comment_id);
        if(comment!=null) {
            comment.setComment_status(comment_status);
            commentService.updateComment(comment);
        }
        return null;
    }

    @GetMapping("/category/getCategoryOptions")
    public Object getCategoryOptions(){
        return categoryService.getCategoryOptions();
    }

    /**
     * 获取类似 /编程/java/blog 的分类路径
     * @return
     */
    @GetMapping("/category/getCategoryPath")
    public Object getCategoryPath(@RequestParam int category_id,@RequestParam(defaultValue = "0") int showArray){
        return categoryService.getCategoryPath(category_id,showArray);
    }

    @GetMapping("/category/getCategoryList")
    public Object getCategoryList(@RequestParam int page, @RequestParam int max_page_length, @RequestParam int parent_id){
        PageHelper<Category> categoryPageHelper;
        List<Category> categories=categoryService.categories(page,max_page_length,parent_id);
        categoryPageHelper = new PageHelper<>(categories,page,max_page_length,categoryService.count());
        return categoryPageHelper;
    }

    @PostMapping("/category/alterCategory")
    public Object alterCategory(@RequestParam int category_id,@RequestParam String category_name){
        Category category = categoryService.getCategory(category_id);
        if (category==null)
            throw new RuntimeException("不存在该分类");
        category.setCategory_name(category_name);
        categoryService.updateCategory(category);
        return null;
    }

    @PostMapping("/category/addCategory")
    public Object addCategory(@RequestParam int parent_id,@RequestParam String category_name){
        categoryService.addCategory(parent_id,category_name);
        return null;
    }

    /**
     * 注：文章处理未完善
     * @param category_id
     * @return
     */
    @PostMapping("/category/deleteCategory")
    @Transactional
    public Object deleteCategory(int category_id) throws RequestException {
        Category category = categoryService.getCategory(category_id);
        if (category==null)
            throw new RequestException("分类不存在");
        categoryService.deleteCategory(category);
        return null;
    }

    @GetMapping("/tag/getTagList")
    public Object getTagList(int page,int max_page_length){
        int start = (page-1)*max_page_length;
        List<Tag> tags = tagDao.getAllPaging(start,max_page_length);
        PageHelper<Tag> pageHelper = new PageHelper<>(tags,page,max_page_length,tagDao.getCount());
        return pageHelper;
    }

    /**
     * 注：文章处理未完善
     * @param tag_id
     * @return
     */
    @PostMapping("/tag/deleteTag")
    public Object deleteTag(int tag_id){
        if (!articleTagDao.getArticleByTag(0,10,tag_id).isEmpty())
            throw new RuntimeException("该标签下有文章，无法删除该标签");
        tagDao.delete(tag_id);
        return null;
    }

    @GetMapping("/page/getPageList")
    public Object getPageList(){
        List<Page> pages = pageService.getPages();
        PageHelper<Page> pagePageHelper = new PageHelper<>(pages,1,100,100);
        return pagePageHelper;
    }


    @PostMapping("/page/addPage")
    public Object addPage(@RequestBody String json){
        Gson gson = new Gson();
        Page page = gson.fromJson(json,Page.class);
        pageService.addPage(page);
//        adminCommonService.addPage(page);
        return null;
    }

    @PostMapping("/page/updatePage")
    public Object updatePage(@RequestBody String json){
        Gson gson = new Gson();
        Page page = gson.fromJson(json,Page.class);
        pageService.updatePage(page);
//        adminCommonService.updatePage(page);
        return null;
    }

    @PostMapping("/page/sortPage")
    public Object sortPage(@RequestBody String json){
        Gson gson = new Gson();
        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
        int page_id = jsonObject.get("page_id").getAsInt();
        int page_index = jsonObject.get("page_index").getAsInt();
        Page page = pageDao.get(page_id);
        if (page==null)
            throw new RuntimeException("未找到page");
//        adminCommonService.sortPage(page,page_index);
        pageService.sortPage(page,page_index);
        return null;
    }

    @PostMapping("/page/deletePage")
    public Object deletePage(@RequestParam int page_id){
        pageService.deletePage(page_id);
        return null;
    }

    @GetMapping("getUser")
    public Object getUser(@RequestParam int user_id){
        User user =userDao.get(user_id);
        return user;

    }

}

