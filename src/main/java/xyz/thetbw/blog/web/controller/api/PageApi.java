package xyz.thetbw.blog.web.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.thetbw.blog.data.entity.Page;
import xyz.thetbw.blog.service.PageService;

@RestController
@RequestMapping("/api/page")
public class PageApi {

    @Autowired
    PageService pageService;

    /**
     * 获取页面内容
     * @param page_url 页面的url
     * @return
     */
    @GetMapping("/{page_url}")
    public Page page(@PathVariable String page_url){
        Page page = pageService.getPage(page_url);
        if (page==null)
            throw new ResourceNotFoundException("没有找到此页面");
        pageService.addPageContent(page);
        return page;
    }
}
