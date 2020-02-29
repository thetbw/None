package xyz.thetbw.blog.data.entity;

public class UserSetting {
    private int user_id;
    private String user_setting_key;
    private String user_setting_value;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_setting_key() {
        return user_setting_key;
    }

    public void setUser_setting_key(String user_setting_key) {
        this.user_setting_key = user_setting_key;
    }

    public String getUser_setting_value() {
        return user_setting_value;
    }

    public void setUser_setting_value(String user_setting_value) {
        this.user_setting_value = user_setting_value;
    }
}
