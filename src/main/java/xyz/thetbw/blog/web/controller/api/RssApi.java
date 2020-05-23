package xyz.thetbw.blog.web.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.thetbw.blog.service.RssService;

import javax.servlet.http.HttpServletResponse;

@RestController()
@RequestMapping("/rss")
public class RssApi {

    @Autowired
    RssService rssService;



    @RequestMapping(produces = "application/xml")
    public Object rss(){
        return rssService.getRss();
    }
}
