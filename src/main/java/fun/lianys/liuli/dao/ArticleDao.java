package fun.lianys.liuli.dao;

import fun.lianys.liuli.pojo.Article;
import fun.lianys.liuli.vo.ArticleParams;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleDao {
    public List<Article> getArticles(ArticleParams params);
    public Article getArticleById(long id);
}
