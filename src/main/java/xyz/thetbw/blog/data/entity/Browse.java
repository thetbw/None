package xyz.thetbw.blog.data.entity;

public class Browse {

    private int browsed_id;
    private String browsed_ip;
    private String browsed_position;
    private int browsed_user_id;
    private long browsed_time;


    public int getBrowsed_id() {
        return browsed_id;
    }

    public void setBrowsed_id(int browsed_id) {
        this.browsed_id = browsed_id;
    }

    public String getBrowsed_ip() {
        return browsed_ip;
    }

    public void setBrowsed_ip(String browsed_ip) {
        this.browsed_ip = browsed_ip;
    }

    public String getBrowsed_position() {
        return browsed_position;
    }

    public void setBrowsed_position(String browsed_position) {
        this.browsed_position = browsed_position;
    }

    public int getBrowsed_user_id() {
        return browsed_user_id;
    }

    public void setBrowsed_user_id(int browsed_user_id) {
        this.browsed_user_id = browsed_user_id;
    }

    public long getBrowsed_time() {
        return browsed_time;
    }

    public void setBrowsed_time(long browsed_time) {
        this.browsed_time = browsed_time;
    }
}
