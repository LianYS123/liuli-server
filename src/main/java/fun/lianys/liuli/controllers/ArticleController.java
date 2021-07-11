package fun.lianys.liuli.controllers;

import fun.lianys.liuli.pojo.Article;
import fun.lianys.liuli.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/article")
@Validated // 校验需要标注这个
public class ArticleController {

    @Autowired
    private final ArticleService articleService = null;

    @GetMapping("/list")
    public List<Article> getArticles(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize) {
        return articleService.getArticles(page, pageSize);
    }

    @GetMapping("/test")
    public String test(@NotNull String name){
        System.out.println(name);
        return name;
    }

    @GetMapping("/{id}")
    public Article getArticleById(@PathVariable("id") @Min(value = 1) Integer id) {
        System.out.println(id);
        return articleService.getArticleById(id);
    }

}
