package xyz.thetbw.blog.data.constant;

/**
 * 存放模板中用到的字段名称
 */
public class ModelKey {

    public static final int VERSION=0;

    /**
     * 模板变量
     */
    public static final String ARTICLES="articles";  //文章列表
    public static final String PAGINATION="pagination"; //分页
    public static final String ARTICLE="article"; //文章
    public static final String COMMENTS="comments";
    public static final String NEED_PASS="need_pass"; //是不是需要密码
    public static final String CATEGORIES="categories"; //分类列表
    public static final String TAGS="tags"; //标签列表
    public static final String CATEGORY="category"; //当前分类
    public static final String TAG="tag"; //当前标签
    public static final String PAGE="page"; //自定义页面内容

    public static final String CURRENT="current";//当前所处页面

    /**
     * 模板方法
     */

    public static final String F_INFO="_info"; //获取博客基本幸喜
    public static final String F_PROPERTY="_property"; //获取博客设置
    public static final String F_THEME="_theme"; //获取主题信息
    public static final String F_USER="_user"; //获取用户信息

    public static final String F_PAGE="_pages"; //获取博客的所有页面
    public static final String F_CATEGORY="_categories"; //获取博客的所有分类

    public static final String U_TIME="_u_time"; //时间转换
    public static final String U_RANDOM="_u_random"; //随机数





}
