package xyz.thetbw.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.thetbw.blog.data.constant.ConstValue;
import xyz.thetbw.blog.data.dao.*;
import xyz.thetbw.blog.data.entity.*;
import xyz.thetbw.blog.exception.ArticleContentNotFountException;
import xyz.thetbw.blog.exception.ArticleException;
import xyz.thetbw.blog.util.MarkDownUtils;
import xyz.thetbw.blog.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Vector;

@Service
@Lazy
public class ArticleService {

    @Autowired ArticleDao articleDao;
    @Autowired ContentDao contentDao;
    @Autowired TagDao tagDao;
    @Autowired ArticleTagDao articleTagDao;
    @Autowired HttpServletRequest httpServletRequest;
    @Autowired UserService userService;
    @Autowired CategoryDao categoryDao;


    MarkDownUtils markDownUtils = MarkDownUtils.getInstance();

    private static final String ARTICLE_ACCESS_COOKIE="article_access_";

    /**
     * 当文章被浏览时递增浏览次数
     * @param artcile_id
     * @param data
     */
    @Async
    public void addBrowsedCount(int artcile_id,Vector<Integer> data){
        Vector<Integer> vector = data;
        synchronized (vector){
            if (vector.contains(artcile_id)) return;
            vector.add(artcile_id);
        }
        articleDao.addBrowsedCount(artcile_id);

    }

    /**
     * 获取文章总数
     * @return
     */
    public int count(){
        return articleDao.getCount();
    }

    /**
     * 根据状态获取文章总数
     * @param status 状态，草稿还是已发布 参见Article
     * @return
     */
    public int count(int status){
        return articleDao.getCountByStatus(status);
    }

    /**
     * 根据分类获取文章总数
     * @param category
     * @return
     */
    public int count(Category category){
        if (category==null) return 0;
        return articleDao.getCountByCategory(category.getCategory_id());
    }

    /**
     * 根据标签获取文章总数
     * @param tag 标签
     * @return
     */
    public int count(Tag tag){
        if (tag==null) return 0;
        return articleTagDao.getArticleCountByTag(tag.getTag_id());

    }


    /**
     * 获取文章列表
     * @param page 分页
     * @param length 分页长度
     * @param status 文章状态
     * @return
     */
    public List<Article> articles(int page,int length,int status){
        int start = (page-1)*length;
        List<Article> articles = articleDao.getAllPagingAndFilter(start,length,status);
        return articles;
    }


    /**
     * 根据分类获取文章列表,只能获取已发布文章
     * @param page
     * @param length
     * @param category
     * @return
     */
    public List<Article> articles(int page,int length, Category category){
        int start = (page-1)*length;
        List<Article> articles = articleDao.getAllByCategory(start,length,category.getCategory_id());
        return articles;
    }

    /**
     * 根据标签获取文章列表，只能获取已发布文章
     * @param page
     * @param length
     * @param tag
     * @return
     */
    public List<Article> articles(int page,int length,Tag tag){
        if (tag==null) return null;
        int start = (page-1)*length;
        List<Article> articles = articleTagDao.getArticleByTag(start,length,tag.getTag_id());
        return articles;
    }


    /**
     * 检查是否有权限访问文章
     * @param article
     * @param pass
     * @return
     */
    public boolean checkArticleAccessAuthorized(Article article, String pass){
        if (!StringUtils.getInstance().checkValidity(article.getArticle_access_pass())) return true;
        User user =userService.getUser();
        if (user!=null&&user.getUser_role()==User.USER_ROLE_ADMIN) return true;
        String s = getAccessAuthorizedFromCookie(article);
        if (s!=null&&s.equals(article.getArticle_access_pass()))
            return true;
        if (pass!=null){
            if (pass.equals(article.getArticle_access_pass()))
                return true;
            else
                return false;
        }
        return false;
    }

    /**
     * 从cookie获取文章的访问密码
     * @param article
     * @return
     */
    private String getAccessAuthorizedFromCookie(Article article){
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies==null) return null;
        for (Cookie cookie:cookies){
            if (cookie.getName().equals(ARTICLE_ACCESS_COOKIE+article.getArticle_id())){
                return  cookie.getValue();
            }
        }
        return null;
    }

    /**
     * 回写cookie,使拥有文章访问权限
     * @param article
     * @param pass
     * @param response
     */
    public void setAccessAuthorized(Article article, String pass, HttpServletResponse response){
        if (getAccessAuthorizedFromCookie(article)!=null) return;
        Cookie cookie = new Cookie(ARTICLE_ACCESS_COOKIE+article.getArticle_id(),pass);
        cookie.setMaxAge(ConstValue.MAX_COOKIE_AGE);
        cookie.setPath("/");
        response.addCookie(cookie);
    }


    /**
     * 获取文章
     * @param article_id 文章的id
     * @return
     */
    public Article getArticle(int article_id){
        Article article = articleDao.get(article_id);
        return article;
    }



    /**
     * 将文章内容添加到文章
     * @param article 文章
     * @throws ArticleContentNotFountException 但找不到文章内容时抛出异常
     */
    public void addArticleContent(Article article) throws ArticleContentNotFountException {
        Content content = contentDao.get(article.getArticle_content_id());
        if (content==null)
            throw new ArticleContentNotFountException();
        article.setArticle_content(content);
    }



    /**
     * 将标签添加到文章
     * @param article
     */
    public void addArticleTags(Article article){
        List<Tag> tags = articleTagDao.getByArticleID(article.getArticle_id());
        article.setArticle_tags(tags);
    }

    public void addArticleCategory(Article article){
        Category category =categoryDao.get(article.getArticle_category_id());
        article.setArticle_category(category);
    }


    @Async
    @Transactional
    public void handleArticlesChange(Article old,Article article){
        if (article.getArticle_category_id()!=0)
            categoryDao.updateCategoryArticleNum(article.getArticle_category_id());
        List<Tag> nTags = articleTagDao.getByArticleID(article.getArticle_id());
        nTags.forEach(tag -> {
            tagDao.updateTagArticleNum(tag.getTag_id());
        });
        if (old!=null){
            if (old.getArticle_category_id()!=article.getArticle_category_id())
                categoryDao.updateCategoryArticleNum(old.getArticle_category_id());
        }
    }




    /**
     * 发表文章
     */
    @Transactional(rollbackFor = {ArticleException.class})
    public void publishArticle(Article article) throws ArticleException {
        User user = userService.getUser();
        genArticlePreview(article);
        article.setArticle_comment_count(0);
        article.setArticle_browsed_count(0);
        article.setArticle_create_time(System.currentTimeMillis());
        article.setArticle_update_time(article.getArticle_create_time());
        article.setArticle_user_id(1);
        if (user!=null)
            article.setArticle_user_id(user.getUser_id());
        saveArticle(article);
    }

    /**
     *生成文章预览  (同时处理了文章内容)
     * @param article 要生成预览的文章
     */
    private void genArticlePreview(Article article){
        if (article.getArticle_access_pass()!=null&&!article.getArticle_access_pass().equals("")){
            article.setArticle_preview_text("需要密码访问");
            article.setArticle_preview("#### 需要密码访问");
            return;
        }
        Content content = article.getArticle_content();
        content.setContent_body_text(markDownUtils.getMarkdownPlainText(content.getContent_body()));
        int max = ConstValue.MAX_ARTICLE_PREVIEW_WORDS;
        String pre=null;
        if (content.getContent_body().length()>max)
            pre=content.getContent_body().substring(0,max);
        else pre = content.getContent_body();
        String pre_plain = MarkDownUtils.getInstance().getMarkdownPlainText(pre);

        article.setArticle_preview(pre);
        article.setArticle_preview_text(pre_plain);
    }

    /**
     * 更新文章 
     */
    @Transactional(rollbackFor = {ArticleException.class})
    public void updateArticle(Article article)throws Exception{
        Article oldArticle = articleDao.get(article.getArticle_id());
        if (oldArticle==null)
            throw new RuntimeException("找不到要修改的文章");
        article.setArticle_user_id(oldArticle.getArticle_user_id());
        article.setArticle_create_time(oldArticle.getArticle_create_time());
        article.setArticle_browsed_count(oldArticle.getArticle_browsed_count());
        article.setArticle_comment_count(oldArticle.getArticle_comment_count());
        article.setArticle_content_id(oldArticle.getArticle_content_id());
        article.setArticle_update_time(System.currentTimeMillis());

        genArticlePreview(article);

        articleDao.update(article);
        contentDao.update(article.getArticle_content());
        saveArticleTag(article);
    }

    /**
     * 后台加载文章 （暂时这么放这）
     * @param article_id
     * @return
     */
    public Article getArticleWithAdmin(int article_id){
        Article article =articleDao.get(article_id);
        if (article==null)
            throw  new RuntimeException("未找到指定文章");
        Content content=contentDao.get(article.getArticle_content_id());
        article.setArticle_content(content);
        List<Tag> tags =articleTagDao.getByArticleID(article_id);
        article.setArticle_tags(tags);
        return article;
    }


    /**
     * 保存文章                 publishArticle
     * @param article
     * @throws ArticleException
     */
    private void saveArticle(Article article) throws ArticleException{
        contentDao.add(article.getArticle_content());
        if (article.getArticle_content().getContent_id()==0){
            throw new ArticleException("文章内容保存失败");
        }
        article.setArticle_content_id(article.getArticle_content().getContent_id());
        saveArticleTag(article);
        articleDao.add(article);
    }

    /**
     * 保存文章和标签的对应关系
     * @param article
     */
    private void saveArticleTag(Article article)throws ArticleException{
        List<Tag> old = articleTagDao.getByArticleID(article.getArticle_id());
        List<Tag> n = article.getArticle_tags();

        if (article.getArticle_tags()!=null){
            old.forEach(old_tag->{
                boolean has =false;
                for (Tag new_tag:n){
                    if (old_tag.getTag_name().equals(new_tag.getTag_name()))
                        has =true;
                }
                if (!has){
                    articleTagDao.deleteArticleTagWithTag(article.getArticle_id(),old_tag.getTag_id());
                    tagDao.updateTagArticleNum(old_tag.getTag_id());
                }
            });

            for (Tag tag:n){
                boolean has = false;
                for(Tag old_tag:old){
                    if (old_tag.getTag_name().equals(tag.getTag_name()))
                        has=true;
                }
                if (!has){
                    int tag_id = saveTag(tag);
                    articleTagDao.addArticleTag(article.getArticle_id(),tag_id);
                }
            }
        }

    }

    /**
     * 保存标签
     * @param tag
     * @return
     * @throws ArticleException
     */
    private int saveTag(Tag tag)throws ArticleException{
        Tag t = tagDao.getTagByName(tag.getTag_name());
        if (t==null){
            tagDao.add(tag);
            if (tag.getTag_id()==0)
                throw new ArticleException("标签添加失败");
            return tag.getTag_id();
        }else {
            tag.setTag_id(t.getTag_id());
            return t.getTag_id();
        }

    }


    public void deleteArticle(Article article){
        contentDao.delete(article.getArticle_content_id());
        articleDao.delete(article.getArticle_id());
        articleTagDao.deleteArticleTagByArticle(article.getArticle_id());
    }

    /**
     * 清除文章的密码，防止前端获取到
     * @param articles
     */
    public void clearPass(List<Article> articles){
        if (articles==null) return;
        for (Article a:articles) {
            a.setArticle_access_pass(null);
        }
    }

}
