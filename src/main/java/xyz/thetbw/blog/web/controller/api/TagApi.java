package xyz.thetbw.blog.web.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.thetbw.blog.data.entity.Tag;
import xyz.thetbw.blog.service.TagService;

import java.util.List;

@RestController
@RequestMapping("/api/tag")
public class TagApi {

    @Autowired
    TagService tagService;

    /**
     * 获取所有标签
     * @return
     */
    @GetMapping("/tags")
    public List<Tag> tags(){
        return tagService.tags();
    }
}
