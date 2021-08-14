package fun.lianys.liuli.services;

import com.github.pagehelper.PageInfo;
import fun.lianys.liuli.pojo.Article;
import fun.lianys.liuli.vo.ArticleParams;

public interface ArticleService {

    public PageInfo getArticles(ArticleParams params);

    public Article getArticleById(long id);

}
