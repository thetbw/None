package xyz.thetbw.blog.data.constant;

/**
 * 系统常量，未开放设置，所以暂时放这里
 */
public class ConstValue {
    public static final String BLOG_NAME="none"; //博客名称
    public static final String VERSION="0.1"; //版本
    public static final String ADMIN_LOGIN_PATH="/admin/login"; //登陆页面地址

    public static final String THEMES_PATH ="themes"; //主题路径

    public static final String THEME_CONFIG_NAME="theme.json"; //主题配置文件名称

    /**
     * 默认视图
     */
    public static final String VIEW_INDEX="index";
    public static final String VIEW_ARTICLE="article";
    public static final String VIEW_CATEGORY="category";
    public static final String VIEW_TAG="tag";
    public static final String VIEW_PAGE="page";

    /**
     * 参数相关
     */
    public static final int MAX_USER_NAME_LENGTH=12; //最大名称大小
    public static final int MAX_USER_PASS_LENGTH=32; //最大密码大小
    public static final int MIN_USER_PASS_LENGTH=6;//最小密码大小
    public static final int MAX_COOKIE_AGE=3600*24*30;//cookie默认一个月过期

    public static final int MAX_COMMENTS_LENGTH=10;//评论的分页大小
    public static final int MAX_CATEGORY_LENGTH=30;//分类的分页大小
    public static final int MAX_TAG_ARTICLE_LENGTH =20; //标签的分页大小


    public static final int MAX_ARTICLE_PREVIEW_WORDS =200; //文章预览最大字数


    public static final String DEFAULT_THEME="simple"; //默认主题


    /**
     *
     */


}
