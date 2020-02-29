package xyz.thetbw.blog.data.model;

import java.lang.reflect.Field;

public class SettingModel {
    /**
     * 网站基本信息
     */
    private String siteTitle="None";
    private String siteDescription="a simple blog";
    private String siteKeywords ="";
    private String siteFavicon="";
    private String siteNotice="";
    private String siteHeader="";
    private String siteFooter="";

    /**
     * 基本设置
     */

    private Boolean isCanRegister=false;
    private String indexPageName="index";
    private Integer indexPagingSize=20;
    private Boolean logWithEmail=false;

    /**
     * 评论相关
     */
    private Boolean commentOpened=false;
//    private String commentLimit;
    private Boolean commentMustLogin=true;
    private Boolean commentWithChecked =true;


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field:fields){
            try {
                builder.append("\t").append(field.getName()).append(":").append(field.get(this)).append("\t\n");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return builder.toString();
    }

    public String getSiteTitle() {
        return siteTitle;
    }

    public void setSiteTitle(String siteTitle) {
        this.siteTitle = siteTitle;
    }

    public String getSiteDescription() {
        return siteDescription;
    }

    public void setSiteDescription(String siteDescription) {
        this.siteDescription = siteDescription;
    }

    public String getSiteKeywords() {
        return siteKeywords;
    }

    public void setSiteKeywords(String siteKeywords) {
        this.siteKeywords = siteKeywords;
    }

    public String getSiteFavicon() {
        return siteFavicon;
    }

    public void setSiteFavicon(String siteFavicon) {
        this.siteFavicon = siteFavicon;
    }

    public String getSiteNotice() {
        return siteNotice;
    }

    public void setSiteNotice(String siteNotice) {
        this.siteNotice = siteNotice;
    }

    public String getSiteHeader() {
        return siteHeader;
    }

    public void setSiteHeader(String siteHeader) {
        this.siteHeader = siteHeader;
    }

    public String getSiteFooter() {
        return siteFooter;
    }

    public void setSiteFooter(String siteFooter) {
        this.siteFooter = siteFooter;
    }

    public Boolean getCanRegister() {
        return isCanRegister;
    }

    public void setCanRegister(Boolean canRegister) {
        isCanRegister = canRegister;
    }

    public String getIndexPageName() {
        return indexPageName;
    }

    public void setIndexPageName(String indexPageName) {
        this.indexPageName = indexPageName;
    }

    public Integer getIndexPagingSize() {
        return indexPagingSize;
    }

    public void setIndexPagingSize(Integer indexPagingSize) {
        this.indexPagingSize = indexPagingSize;
    }

    public Boolean getLogWithEmail() {
        return logWithEmail;
    }

    public void setLogWithEmail(Boolean logWithEmail) {
        this.logWithEmail = logWithEmail;
    }

    public Boolean getCommentOpened() {
        return commentOpened;
    }

    public void setCommentOpened(Boolean commentOpened) {
        this.commentOpened = commentOpened;
    }

    public Boolean getCommentMustLogin() {
        return commentMustLogin;
    }

    public void setCommentMustLogin(Boolean commentMustLogin) {
        this.commentMustLogin = commentMustLogin;
    }

    public Boolean getCommentWithChecked() {
        return commentWithChecked;
    }

    public void setCommentWithChecked(Boolean commentWithChecked) {
        this.commentWithChecked = commentWithChecked;
    }


}
