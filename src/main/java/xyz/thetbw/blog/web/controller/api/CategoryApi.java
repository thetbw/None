package xyz.thetbw.blog.web.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.thetbw.blog.data.constant.ConstValue;
import xyz.thetbw.blog.data.entity.Category;
import xyz.thetbw.blog.service.CategoryService;
import xyz.thetbw.blog.util.PageHelper;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryApi {


    @Autowired
    CategoryService categoryService;

    /**
     * 获取分类列表
     * @param page 分页
     * @param parent_id 父分页id
     * @return
     */
    @GetMapping("/categories")
    public PageHelper<Category> categories(@RequestParam int page,@RequestParam(defaultValue = "-1") int parent_id){
        int length = ConstValue.MAX_CATEGORY_LENGTH;
        List<Category> categories = categoryService.categories(page,length,parent_id);
        PageHelper<Category> categoryPageHelper = new PageHelper<>(categories,page,length,categoryService.count(parent_id));
        return categoryPageHelper;
    }
}
