package fun.lianys.liuli.services;

import fun.lianys.liuli.pojo.Article;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

public interface ArticleService {

    public List<Article> getArticles(Integer page, Integer pageSize);

    public Article getArticleById(long id);

    public String test();
}
