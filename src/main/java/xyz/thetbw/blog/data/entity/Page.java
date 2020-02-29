package xyz.thetbw.blog.data.entity;

public class Page {

    public static final String TABLE_NAME="blog_page";
    /**
     * 页面包含具体内容
     */
    public static final int PAGE_TYPE_CONTENT=0;
    /**
     * 页面指向一个url
     */
    public static final int PAGE_TYPE_URL=1;

    private int page_id;
    private String page_name;
    private int page_content_id;
    private int page_order;
    private int page_type;
    private String page_url; //页面链接链接


    private Content page_content;


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Page))
            return false;
        Page page = (Page) obj;
        if (page.getPage_id()!=this.getPage_id())
            return false;
        else return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getPage_id()).append("\t")
                .append(getPage_name()).append("\t")
                .append(getPage_url()).append("\t")
                .append(getPage_order());
        return builder.toString();
    }

    public int getPage_id() {
        return page_id;
    }

    public void setPage_id(int page_id) {
        this.page_id = page_id;
    }

    public String getPage_name() {
        return page_name;
    }

    public void setPage_name(String page_name) {
        this.page_name = page_name;
    }

    public int getPage_content_id() {
        return page_content_id;
    }

    public void setPage_content_id(int page_content_id) {
        this.page_content_id = page_content_id;
    }

    public int getPage_order() {
        return page_order;
    }

    public void setPage_order(int page_order) {
        this.page_order = page_order;
    }

    public int getPage_type() {
        return page_type;
    }

    public void setPage_type(int page_type) {
        this.page_type = page_type;
    }

    public String getPage_url() {
        return page_url;
    }

    public void setPage_url(String page_url) {
        this.page_url = page_url;
    }

    public Content getPage_content() {
        return page_content;
    }

    public void setPage_content(Content page_content) {
        this.page_content = page_content;
    }
}
