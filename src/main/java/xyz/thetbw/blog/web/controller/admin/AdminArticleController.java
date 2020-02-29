package xyz.thetbw.blog.web.controller.admin;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xyz.thetbw.blog.data.dao.ArticleDao;
import xyz.thetbw.blog.data.entity.Article;
import xyz.thetbw.blog.data.entity.Content;
import xyz.thetbw.blog.data.entity.Tag;
import xyz.thetbw.blog.exception.ArticleException;
import xyz.thetbw.blog.service.ArticleService;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/api/article")
public class AdminArticleController {

    @Autowired
    ArticleService articleService;

    /**
     *
     * @param json
     * @return
     */
    @PostMapping("/publishArticle")
    public Object publishArticle(@RequestBody  String json){
        Article article = getArticleFromJson(json);
        try {
            articleService.publishArticle(article);
            articleService.handleArticlesChange(null,article);
        } catch (ArticleException e) {
            System.out.println("保存失败");
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/getArticle")
    public Object getArticle(@RequestParam int article_id){
        return articleService.getArticleWithAdmin(article_id);
    }

    @PostMapping("/updateArticle")
    public Object updateArticle(@RequestBody  String json)throws Exception{
        Article article = getArticleFromJson(json);
        Article old = articleService.getArticle(article.getArticle_id());
        try {
            articleService.updateArticle(article);
            articleService.handleArticlesChange(old,article);
        } catch (ArticleException e) {
            e.printStackTrace();
        } catch (Exception e){
            throw e;
        }
        return null;
    }

    private Article getArticleFromJson(String json){
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        Article article = gson.fromJson(jsonObject.get("Article"),Article.class);
        List<Tag> tags = new ArrayList<>();
        Type type = new TypeToken<ArrayList<String>>(){}.getType(); //用来解决gson泛型问题
        List<String> tag_names=gson.fromJson(jsonObject.get("Tags"),type);
        for (String s:tag_names){
            Tag tag = new Tag();
            tag.setTag_name(s);
            tags.add(tag);
        }
        article.setArticle_tags(tags);
        return article;
    }
}
