package xyz.thetbw.blog.data.entity;

public class Property {

    public static final String TABLE_NAME="blog_value";

    private int property_id;
    private String property_key;
    private String property_value;

    public int getProperty_id() {
        return property_id;
    }

    public void setProperty_id(int property_id) {
        this.property_id = property_id;
    }

    public String getProperty_key() {
        return property_key;
    }

    public void setProperty_key(String property_key) {
        this.property_key = property_key;
    }

    public String getProperty_value() {
        return property_value;
    }

    public void setProperty_value(String property_value) {
        this.property_value = property_value;
    }
}
