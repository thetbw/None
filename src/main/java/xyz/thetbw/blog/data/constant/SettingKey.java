package xyz.thetbw.blog.data.constant;

/**
 * 存放设置用到的各个字段名称
 */
public class SettingKey {
    /**
     * 网站基本信息
     */
    public static final String SITE_TITLE="siteTitle"; //网站名称
    public static final String SITE_DESCRIPTION="siteDescription"; //网站描述
    public static final String SITE_KEYWORDS="siteKetwords"; //网站关键字
    public static final String SITE_FAVICON="siteFavicon"; //网站favicon
    public static final String SITE_NOTICE="siteNotice";//网站公告
    public static final String SITE_HEADER="siteHeader"; //自定义页眉
    public static final String SITE_FOOTER="siteFooter"; //自定义页脚

    /**
     * 基本设置
     */
    public static final String IS_CAN_REGISTER="isCanRegister"; //是否允许注册
    public static final String INDEX_PAGE_NAME="indexPageName";//网站首页页面
    public static final String INDEX_PAGING_SIZE="indexPagingSize"; //首页分页大小
    public static final String LOG_WITH_EMAIL = "logWithEmail";//邮件发送日志

    /**
     * 评论相关
     */
    public static final String COMMENT_OPENED="commentOpened";//开启评论
    public static final String COMMENT_LIMIT="commentLimit"; //同一ip评论限制
    public static final String COMMENT_MUST_LOGIN="commentMustLogin";//必须登陆才能评论？
    public static final String COMMENT_WITH_CHCKED="commentWithChcked";//评论需要审核？



    /**
     * 用户相关
     */
    public static final String USER_NICK_NAME="user_nickname";
    public static final String USER_EMAIL="user_email";
    //
    public static final String COMMENT_WITH_EMAIL="commentWithEmail"; //收到评论后邮件通知
    public static final String USER_AVATAR_URL="user_avatar_url";


}
