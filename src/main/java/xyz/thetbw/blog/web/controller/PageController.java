package xyz.thetbw.blog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import xyz.thetbw.blog.data.constant.ConstValue;
import xyz.thetbw.blog.data.constant.ModelKey;
import xyz.thetbw.blog.data.entity.Page;
import xyz.thetbw.blog.service.PageService;
import xyz.thetbw.blog.util.MarkDownUtils;

import javax.servlet.annotation.WebFilter;

@Controller
public class PageController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(PageController.class);
    @Autowired
    PageService pageService;

    @GetMapping("/{page_url}**")
    public String page(@PathVariable String page_url, Model model){
        Page page =pageService.getPage("/"+page_url);
        if (page==null||page.getPage_type()!=Page.PAGE_TYPE_CONTENT){
            LOG.warn("页面未找到："+page_url);
            throw new ResourceNotFoundException("页面未找到");
        }
        if (page.getPage_content()==null) throw new ResourceNotFoundException("页面内容为空");
        String html = MarkDownUtils.getInstance().markdown2Html(page.getPage_content().getContent_body());
        page.getPage_content().setContent_body(html);
        model.addAttribute(ModelKey.PAGE,page);
        return this.render(ConstValue.VIEW_PAGE,model);
    }
}
