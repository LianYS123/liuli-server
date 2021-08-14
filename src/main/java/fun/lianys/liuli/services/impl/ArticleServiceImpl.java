package fun.lianys.liuli.services.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import fun.lianys.liuli.dao.ArticleDao;
import fun.lianys.liuli.pojo.Article;
import fun.lianys.liuli.services.ArticleService;
import fun.lianys.liuli.vo.ArticleParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao = null;

    @Override
    public PageInfo getArticles(ArticleParams params) {
        Page p = PageHelper.startPage(params.getPage(), params.getPageSize());
        List<Article> articles = articleDao.getArticles(params);
        PageInfo info = new PageInfo(p.getResult());
        return info;
    }

    @Override
    public Article getArticleById(long id) {
        return articleDao.getArticleById(id);
    }

}
