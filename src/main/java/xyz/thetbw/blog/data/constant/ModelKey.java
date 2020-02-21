package xyz.thetbw.blog.data.constant;

/**
 * 存放模板中用到的字段名称
 */
public class ModelKey {
    /**
     * 全局方法
     */
    public static final String BLOG_INFO="blogInfo"; //博客信息
    public static final String BLOG_PROPERTY ="blogProperty";
    public static final String THEME_INFO="themeInfo";
    public static final String USER_INFO="userInfo";
    public static final String NAV_PAGES="getPages";

    public static final String TIME_UTIL="toTime";

    /**
     * 工具方法
     */
    public static final String Random = "random";


    public static final String PAGINATION="pagination";

    //html头相关 所有模板中都会存在
//    public static final String TITLE="title"; //网站标题
//    public static final String DESCRIPTION="description"; //网站描述
//    public static final String COPYRIGHT="copyright"; //版权信息
//    public static final String VERSION = "version"; //博客版本
//    public static final String BLOG_NAME="blog_name"; //博客名称
//    public static final String THEME_NAME="theme_name"; //主题名称
//    public static final String KEY_WORDS="key_words";//网站关键词
//    public static final String FAVICON="favicon";//网站图标


    //错误页面 只在错误页面中存在 下面的由springboot自动添加
    public static final String ERROR_TIME="timestamp";//错误发生时间 Type<Date>
    public static final String ERROR_TYPE="error";//同错误消息
    public static final String ERROR_STATUS_CODE="status";//错误状态码
    public static final String ERROR_URL="path";//错误的访问地址
    public static final String ERROR_MASSAGE="message";//错误消息
    public static final String ERROR_TIP="error_tip";//错误解决提示

    //页头相关 所有模板都会存在
    public static final String PAGE_NAVS = "page_navs"; //网站导航页面 List<Page>
    public static final String NOTICE="notice";//网站公告


    //个人相关 所有模板都会存在
    public static final String USER_NAME="user_name";
    public static final String USER_AVATAR="user_avatar";



    //分页相关 只会再特定模板存在

    //文章相关
    public static final String ARTICLE_LIST="article_list";//文章列表 List<Article> 只有特定页面才会存在（首页，分类）
    public static final String NOW_PAGE="now_page";//当前页面编号

    //一下只有文章页面才会存在
    public static final String ARTICLE="article";//Type<Article>
    public static final String ARTICLE_PASS="required_pass"; //需不需要密码
    public static final String ARTICLE_CONTENT="article_content";//文章内容
    public static final String ARTICLE_PAGINATION="article_pagination"; //文章分页 List<Integer>
    public static final String ARTICLE_CATEGORY="article_category";//文章分类信息
    public static final String ARTICLE_TAG_LIST="article_tag_list";//文章标签数组
    public static final String ARTICLE_AUTHOR="article_author";//文章作者信息 Type<User>
    public static final String PREVIOUS_ARTICLE_TITLE="previous_article_title";//上一篇文章 Type<Article>
    public static final String PREVIOUS_ARTICLE_LINK="previous_article_link";
    public static final String NEXT_ARTICLE_TITLE="next_article";//下一篇文章
    public static final String NEXT_ARTICLE_LINK="next_article_link";


    //评论相关 只有文章页面和具体页面内容页面才会出现
    public static final String COMMENT_LIST="comment_list";


    /**
     * 分类页面
     */
    public static final String CATEGORY_LIST="category_list";
    public static final String NOW_CATEGORY ="now_category";
    public static final String CATEGORY_PATH="category_path";
    public static final String CATEGORY_PAGINATION ="category_pagination";
    public static final String CATEGORY_TREE = "category_tree";
    public static final String CATEGORY_ARTICLES="category_articles";

    /**
     * 标签页面
     */
    public static final String TAG_LIST="tag_list";
    public static final String TAG_ARTICLES="tag_articles";
    public static final String TAG_PAGINATION="tag_pagination";
    public static final String NOW_TAG="now_tag";


    /**
     * 页面
     */

    public static final String  PAGE_CONTENT="page_content";


}
