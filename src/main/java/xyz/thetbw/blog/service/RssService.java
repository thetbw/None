package xyz.thetbw.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.thetbw.blog.data.AppContext;
import xyz.thetbw.blog.data.dao.ArticleDao;
import xyz.thetbw.blog.data.dao.CategoryDao;
import xyz.thetbw.blog.data.dao.ContentDao;
import xyz.thetbw.blog.data.dao.UserDao;
import xyz.thetbw.blog.data.entity.Article;
import xyz.thetbw.blog.data.entity.Category;
import xyz.thetbw.blog.data.entity.Content;
import xyz.thetbw.blog.util.MarkDownUtils;
import xyz.thetbw.blog.util.RssUtil;
import xyz.thetbw.blog.util.StringUtils;
import xyz.thetbw.blog.util.TimeUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RssService {

    @Autowired
    HttpServletRequest request;
    @Autowired
    ArticleDao articleDao;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    UserDao userDao;

    @Autowired
    ContentDao contentDao;

    private int maxArticlesLength=20;
    private int ttl=60*6;

    private TimeUtils timeUtils = TimeUtils.getInstance();

    /**
     * 生成rss
     * @return
     */
    public String getRss(){
        RssUtil rssUtil = RssUtil.newInstance();
        if (rssUtil==null)
            return rssErrorString();

        List<Article> articles = articleDao.getAllPagingAndFilter(0,maxArticlesLength,Article.ARTICLE_STATUS_SHOW);

        String link = getHost();
        String title = AppContext.getInstance().setting.getSiteTitle();
        String description = AppContext.getInstance().setting.getSiteDescription();
        String copyright = timeUtils.getNowYear()+" "+AppContext.getInstance().setting.getSiteTitle();
        String managingEditor = userDao.getAdminUser().getUser_email();


        String lastBuildDate=null;
        if (articles!=null&&articles.size()!=0){
            Article article =articles.get(0);
            if (article!=null){
                lastBuildDate = rssUtil.getRfc822Time(article.getArticle_update_time());
            }
        }

        RssUtil.Category category = new RssUtil.Category(link,"blog");
        String docs = link+"/rss";
        RssUtil.Image image;

        List<RssUtil.Item> items = articles2items(articles,link);
        RssUtil.Channel channel = rssUtil.getChannel();

        /**
         * 根据网站favicon创建rss图像
         */
        String favicon = AppContext.getInstance().setting.getSiteFavicon();
        if (StringUtils.getInstance().checkValidity(favicon)){
            image = new RssUtil.Image();
            image.setTitle(title);
            image.setLink(link);
            image.setUrl(favicon);
            channel.setImage(image);
        }
        channel.setTitle(title);
        channel.setDescription(description);
        channel.setCopyright(copyright);
        channel.setLastBuildDate(lastBuildDate);
        channel.setCategory(category);
        channel.setDocs(docs);
        channel.setManagingEditor(managingEditor);
        channel.setItem(items);

        /**
         * 生成rss文本
         */
        String result = rssUtil.generateXML();
        if (result==null){
            return rssErrorString();
        }

        return result;
    }

    private List<RssUtil.Item> articles2items(List<Article> articles,String link){
        if (articles==null||articles.size()==0){
            return null;
        }
        List<RssUtil.Item> items = new ArrayList<>();
        MarkDownUtils markDownUtils = MarkDownUtils.getInstance();
        articles.forEach(a->{
            if (a.getArticle_access_pass()!=null&&a.getArticle_access_pass().length()>0){
                return;
            }
            RssUtil.Item item = new RssUtil.Item();
            item.setTitle(a.getArticle_title());

            Content content =contentDao.get(a.getArticle_content_id());
            if (content!=null){
                item.setDescription(markDownUtils.markdown2Html(content.getContent_body()));
            }
            item.setLink(link+"/article/"+a.getArticle_id());
            Category category = categoryDao.get(a.getArticle_category_id());
            if (category!=null){
                RssUtil.Category cate = new RssUtil.Category();
                cate.setValue(category.getCategory_name());
                cate.setDomain(link+"/category/"+category.getCategory_id());
                item.setCategory(cate);
            }
            item.setAuthor(userDao.getAdminUser().getUser_nickname());
            item.setPubDate(RssUtil.getRfc822Time(a.getArticle_update_time()));
            RssUtil.Guid guid = new RssUtil.Guid(true,a.getArticle_id()+"");
            item.setGuid(guid);
            items.add(item);
        });
        return items;
    }


    /**
     * 获取当前访问域名
     * @return
     */
    private String getHost(){
        String host = request.getServerName();
        int port = request.getServerPort();
        if (port!=80&&port!=443){
            host+=":"+port;
        }
        host+=request.getContextPath();


        return "http://"+host;
    }


    /**
     * 生成rss格式错误文档
     * @return
     */
    private String rssErrorString(){
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\"?>\n");
        builder.append("<rss version=\"2.0\">\n");
        builder.append("\t<channel>\n");
        builder.append("\t\t<title>None：Rss error</title>\n");
        builder.append("\t\t<link>https://github.com/thetbw/None</link>\n");
        builder.append("\t\t<description>Rss error,If you are an administrator, please check the log</link>\n");
        builder.append("\t</channel>\n");
        builder.append("</rss>\n");
        return builder.toString();
    }

}
