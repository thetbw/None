package xyz.thetbw.blog.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.*;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * rss和java的映射转换以及一些其他工具
 */
public class RssUtil {
    public static final String VERSION="2.0";

    private List<Item> items=new ArrayList<>();
    private Rss rss = new Rss();
    private Channel channel = new Channel();
    private Marshaller marshaller;
    private MyWriter myWriter;

    private RssUtil(){
        rss.setChannel(channel);
        rss.setVersion(VERSION);
        myWriter = new MyWriter();
    }

    public String generateXML(){
        String value = null;
        myWriter.clear();
        try {
            marshaller.marshal(rss,myWriter);
            value = myWriter.getString();
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
        return value;
    }

    public static RssUtil newInstance(){
        RssUtil rssUtil = new RssUtil();
        try {
            JAXBContext context = JAXBContext.newInstance(Rss.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
            rssUtil.setMarshaller(marshaller);
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }

        return rssUtil;
    }


    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Rss getRss() {
        return rss;
    }

    public void setRss(Rss rss) {
        this.rss = rss;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    private void setMarshaller(Marshaller marshaller) {
        this.marshaller = marshaller;
    }


    private class MyWriter extends Writer{
        private StringBuilder builder = new StringBuilder();
        private boolean closed;

        @Override
        public void write(char[] cbuf, int off, int len) throws IOException {
            if (closed){
                throw new IOException("stream is closed");
            }
            builder.append(cbuf,off,len);
        }

        @Override
        public void flush() throws IOException {

        }

        @Override
        public void close() throws IOException {
            closed = true;
        }

        public void clear(){
            if (builder.length()>0)
                builder.delete(0,builder.length()-1);
            closed = false;
        }

        public String getString(){
            return builder.toString();
        }
    }


    @XmlRootElement(name = "rss")
    @XmlAccessorType(value = XmlAccessType.FIELD)
    public static class Rss{

        @XmlAttribute(name = "version",required = true)
        private String version;

        @XmlElement(name = "channel",required = true)
        private Channel channel;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public Channel getChannel() {
            return channel;
        }

        public void setChannel(Channel channel) {
            this.channel = channel;
        }
    }


    @XmlType(name = "channel")
    @XmlAccessorType(value = XmlAccessType.FIELD)
    public static class Channel{
        @XmlElement(name = "title",required = true)
        private String title;

        @XmlElement(name = "link",required = true)
        private String link;

        @XmlElement(name = "description",required = true)
        private String description;

        @XmlElement(name = "language")
        private String language;

        @XmlElement(name = "copyright")
        private String copyright;

        @XmlElement(name = "lastBuildDate")
        private String lastBuildDate;

        @XmlElement(name = "docs")
        private String docs;

        @XmlElement(name = "generator")
        private String generator;

        @XmlElement(name = "category")
        private Category category;

        @XmlElement(name = "managingEditor")
        private String managingEditor;

        @XmlElement(name = "webMaster")
        private String webMaster;

        @XmlElement(name = "ttl")
        private int ttl;

        @XmlElement(name = "item")
        private List<Item> item;

        @XmlElement(name = "image")
        private Image image;

        public void setCategory(Category category) {
            this.category = category;
        }

        public Image getImage() {
            return image;
        }

        public void setImage(Image image) {
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getCopyright() {
            return copyright;
        }

        public void setCopyright(String copyright) {
            this.copyright = copyright;
        }

        public String getLastBuildDate() {
            return lastBuildDate;
        }

        public void setLastBuildDate(String lastBuildDate) {
            this.lastBuildDate = lastBuildDate;
        }

        public String getDocs() {
            return docs;
        }

        public void setDocs(String docs) {
            this.docs = docs;
        }

        public String getGenerator() {
            return generator;
        }

        public void setGenerator(String generator) {
            this.generator = generator;
        }


        public String getManagingEditor() {
            return managingEditor;
        }

        public void setManagingEditor(String managingEditor) {
            this.managingEditor = managingEditor;
        }

        public String getWebMaster() {
            return webMaster;
        }

        public void setWebMaster(String webMaster) {
            this.webMaster = webMaster;
        }

        public int getTtl() {
            return ttl;
        }

        public void setTtl(int ttl) {
            this.ttl = ttl;
        }

        public List<Item> getItem() {
            return item;
        }

        public void setItem(List<Item> item) {
            this.item = item;
        }
    }


    @XmlType(name = "item")
    @XmlAccessorType(value = XmlAccessType.FIELD)
    public static class Item{

        @XmlElement(name = "title",required = true)
        private String title;

        @XmlElement(name = "link",required = true)
        private String link;

        @XmlElement(name = "description",required = true)
        private String description;

        @XmlElement(name = "author")
        private String author;

        @XmlElement(name = "category")
        private Category category;

        @XmlElement(name = "comments")
        private String comments;

        @XmlElement(name = "enclosure")
        private String enclosure;

        @XmlElement(name = "guid")
        private Guid guid;

        @XmlElement(name = "pubDate")
        private String pubDate;

        @XmlElement(name = "source")
        private String source;


        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public Category getCategory() {
            return category;
        }

        public void setCategory(Category category) {
            this.category = category;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public String getEnclosure() {
            return enclosure;
        }

        public void setEnclosure(String enclosure) {
            this.enclosure = enclosure;
        }

        public Guid getGuid() {
            return guid;
        }

        public void setGuid(Guid guid) {
            this.guid = guid;
        }

        public String getPubDate() {
            return pubDate;
        }

        public void setPubDate(String pubDate) {
            this.pubDate = pubDate;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }
    }

    @XmlType(name = "guid")
    @XmlAccessorType(value = XmlAccessType.FIELD)
    public static class Guid{

        @XmlAttribute(name = "isPermaLink")
        private boolean isPermaLink;

        @XmlValue
        private String value;
    }

    @XmlType(name = "category")
    @XmlAccessorType(value = XmlAccessType.FIELD)
    public static class Category{

        @XmlAttribute(name = "domain")
        private String domain;

        @XmlValue()
        private String value;

    }

    @XmlType(name = "image")
    @XmlAccessorType(value = XmlAccessType.FIELD)
    public static class Image{

        @XmlElement(name = "url")
        private String url;

        @XmlElement(name = "title")
        private String title;

        @XmlElement(name = "link")
        private String link;

        @XmlElement(name = "width")
        private int width;

        @XmlElement(name = "height")
        private int height;


        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }
}
