package xyz.thetbw.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.thetbw.blog.data.dao.ContentDao;
import xyz.thetbw.blog.data.dao.PageDao;
import xyz.thetbw.blog.data.entity.Comment;
import xyz.thetbw.blog.data.entity.Content;
import xyz.thetbw.blog.data.entity.Page;
import xyz.thetbw.blog.data.entity.User;

import java.util.Comparator;
import java.util.List;

/**
 * 页面相关
 */
@Service
public class PageService {


    @Autowired
    ContentDao contentDao;

    @Autowired
    PageDao pageDao;


    /**
     *根据url获取页面
     */
    public Page getPage(String page_url){
        Page p = pageDao.getPageByURL(page_url);
        addContent(p);
        return p;
    }

    /**
     * 给页面设置内容
     * @param p 页面
     */
    private void addContent(Page p){
        if (p==null) return;
        if (p.getPage_type()==Page.PAGE_TYPE_CONTENT&&p.getPage_content_id()!=0){
            Content content = contentDao.get(p.getPage_content_id());
            if (content!=null)
                p.setPage_content(content);
        }
    }


    /**
     *获取所有页面
     * @return
     */
    public List<Page> getPages(){
        List<Page> pages = pageDao.getAll();
        pages.forEach(p->{
            addContent(p);
        });

        return pages;
    }


    /**
     * 添加页面
     * @param page
     */
    @Transactional
    public void addPage(Page page){
        addPageContent(page);
        pageDao.add(page);
    }

    public void addPageContent(Page page){
        if (page.getPage_type()==Page.PAGE_TYPE_CONTENT) {
            Content content = page.getPage_content();
            if (content==null||content.getContent_body()==null) return;
            contentDao.add(content);
            if (content.getContent_id()==0)
                throw new RuntimeException("内容添加失败");
            page.setPage_content_id(content.getContent_id());
        }
    }

    @Transactional
    public void updatePage(Page page){
        Page oldPage=pageDao.get(page.getPage_id());
        if (oldPage==null)
            throw  new RuntimeException("未找到页面");
        if (page.getPage_type()==Page.PAGE_TYPE_CONTENT) {
            Content content = page.getPage_content();
            if (content!=null){
                if (page.getPage_content_id()==0){
                    addPageContent(page);
                }else {
                    contentDao.update(content);
                }
            }
        }
        pageDao.update(page);
    }

    /**
     * 对页面排序
     * @param page
     * @param index
     */
    public void sortPage(Page page,int index){
        List<Page> pages = pageDao.getAll();
        pages.sort(Comparator.comparing(Page::getPage_order)); //排序
        pages.remove(page);
        pages.add(index,page);
        //重置序号
        for (int i=1;i<=pages.size();i++){
            pages.get(i-1).setPage_order(i);
        }
        pageDao.batchUpdate(pages);
    }




    @Transactional
    public void deletePage(int page_id){
        Page page = pageDao.get(page_id);
        Content content = contentDao.get(page.getPage_content_id());
        if (content!=null)
            contentDao.delete(content.getContent_id());
        pageDao.delete(page_id);
    }
}
