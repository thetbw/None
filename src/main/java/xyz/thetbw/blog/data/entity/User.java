package xyz.thetbw.blog.data.entity;

public class User {
    public static final String TABLE_NAME="blog_user";

    /**
     * 管理员
     */
    public static final int USER_ROLE_ADMIN=0;
    /**
     * 注册用户
     */
    public static final int USER_ROLE_MENMBER=1;
    /**
     * 访客
     */
    public static final int USER_ROLE_GUEST=2;

    private int user_id;
    private String user_name;
    private String user_nickname;
    private int user_pass;
    private int user_role;
    private String user_avatar_url;
    private String user_email;
    private Long github_id;

    public User clearPass(){
        this.user_pass=0;
        return this;
    }

    public Long getGithub_id() {
        return github_id;
    }

    public void setGithub_id(Long github_id) {
        this.github_id = github_id;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("user_id:").append(this.user_id).append("\n")
                .append("nick_name:").append(this.user_nickname).append("\n")
                .append("user_name:").append(user_name).append("\n")
                .append("user_pass:").append(user_pass).append("\n")
                .append("email:").append(user_email).append("\n");

        return builder.toString();
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public int getUser_pass() {
        return user_pass;
    }

    public void setUser_pass(int user_pass) {
        this.user_pass = user_pass;
    }

    public int getUser_role() {
        return user_role;
    }

    public void setUser_role(int user_role) {
        this.user_role = user_role;
    }

    public String getUser_avatar_url() {
        return user_avatar_url;
    }

    public void setUser_avatar_url(String user_avatar_url) {
        this.user_avatar_url = user_avatar_url;
    }
}
