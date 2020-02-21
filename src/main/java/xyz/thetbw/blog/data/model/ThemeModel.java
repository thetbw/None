package xyz.thetbw.blog.data.model;

public class ThemeModel {
    private String themeName; //主题名称
    private String themePreviewImagePath; //主题预览图片地址
    private String themeDescription; //主题描述
    private String themeVersion;//主题版本
    private String themeAuthor;//主题作者
    private String themeAuthorLink;//主题作者的链接
    private String themeLink;//主题介绍链接
    private Boolean idEnabled=false;//是否未当前启用主题
    private String themeFilePath;//主题本地路径名

    public String getThemeFilePath() {
        return themeFilePath;
    }

    public void setThemeFilePath(String themeFilePath) {
        this.themeFilePath = themeFilePath;
    }

    public String getThemeVersion() {
        return themeVersion;
    }

    public void setThemeVersion(String themeVersion) {
        this.themeVersion = themeVersion;
    }

    public String getThemeAuthor() {
        return themeAuthor;
    }

    public void setThemeAuthor(String themeAuthor) {
        this.themeAuthor = themeAuthor;
    }

    public String getThemeAuthorLink() {
        return themeAuthorLink;
    }

    public void setThemeAuthorLink(String themeAuthorLink) {
        this.themeAuthorLink = themeAuthorLink;
    }

    public String getThemeLink() {
        return themeLink;
    }

    public void setThemeLink(String themeLink) {
        this.themeLink = themeLink;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getThemePreviewImagePath() {
        return themePreviewImagePath;
    }

    public void setThemePreviewImagePath(String themePreviewImagePath) {
        this.themePreviewImagePath = themePreviewImagePath;
    }

    public String getThemeDescription() {
        return themeDescription;
    }

    public void setThemeDescription(String themeDescription) {
        this.themeDescription = themeDescription;
    }

    public Boolean getIdEnabled() {
        return idEnabled;
    }

    public void setIdEnabled(Boolean idEnabled) {
        this.idEnabled = idEnabled;
    }
}
