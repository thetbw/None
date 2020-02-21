package xyz.thetbw.blog.data.entity;

/**
 * @author thetbw
 */
public class Content {

    public static final String TABLE_NAME="blog_content";
    /**
     * 历史记录
     */
    public static final int CONTENT_TYPE_HIS=0;
    /**
     * 现在内容
     */
    public static final int CONTENT_TYPE_NOW=1;

    private int content_id;
    private String content_body;
    private String content_body_text;
    private int content_type=1;
    private int content_rollback_flag;
    private String content_describe;

    public int getContent_id() {
        return content_id;
    }

    public void setContent_id(int content_id) {
        this.content_id = content_id;
    }

    public String getContent_body() {
        return content_body;
    }

    public void setContent_body(String content_body) {
        this.content_body = content_body;
    }

    public String getContent_body_text() {
        return content_body_text;
    }

    public void setContent_body_text(String content_body_text) {
        this.content_body_text = content_body_text;
    }

    public int getContent_type() {
        return content_type;
    }

    public void setContent_type(int content_type) {
        this.content_type = content_type;
    }

    public int getContent_rollback_flag() {
        return content_rollback_flag;
    }

    public void setContent_rollback_flag(int content_rollback_flag) {
        this.content_rollback_flag = content_rollback_flag;
    }

    public String getContent_describe() {
        return content_describe;
    }

    public void setContent_describe(String content_describe) {
        this.content_describe = content_describe;
    }
}
