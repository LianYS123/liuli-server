package fun.lianys.liuli.controllers;

import com.github.pagehelper.PageInfo;
import fun.lianys.liuli.common.ApiResponse;
import fun.lianys.liuli.pojo.Article;
import fun.lianys.liuli.services.ArticleService;
import fun.lianys.liuli.vo.ArticleParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/article")
@Validated // 校验需要标注这个
public class ArticleController {

    @Autowired
    private final ArticleService articleService = null;

    @GetMapping("/list")
//    @PreAuthorize("hasAnyRole('admin')")  // 只有管理员才能访问
    public ApiResponse getArticles(ArticleParams params) {
        System.out.println(params);
        PageInfo articles = articleService.getArticles(params);
        return ApiResponse.ofSuccess(articles);
    }

    @GetMapping("/test")
    public String test(@NotNull String name) {
        return name;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('admin')")
    public Article getArticleById(@PathVariable("id") @Min(value = 1) Integer id) {
        System.out.println(id);
        return articleService.getArticleById(id);
    }

}
