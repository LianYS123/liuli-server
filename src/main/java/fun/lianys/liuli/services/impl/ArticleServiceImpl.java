package fun.lianys.liuli.services.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import fun.lianys.liuli.dao.ArticleDao;
import fun.lianys.liuli.pojo.Article;
import fun.lianys.liuli.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao = null;

    @Override
    public List<Article> getArticles(Integer page, Integer pageSize) {
        Page p = PageHelper.startPage(page, pageSize);
        List<Article> articles = articleDao.getArticles();
        PageInfo info = new PageInfo(p.getResult());
        System.out.println(info);
        return articles;
    }

    @Override
    public Article getArticleById(long id) {
        return articleDao.getArticleById(id);
    }

    @Override
    public String test(){
        return "test";
    }
}
