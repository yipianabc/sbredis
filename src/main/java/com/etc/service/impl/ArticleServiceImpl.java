package com.etc.service.impl;

import com.etc.dao.ArticleDao;
import com.etc.entity.Article;
import com.etc.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private ArticleDao articleDao;


    @Override
    public void add(Article a) {
        articleDao.save(a);
    }

    @Override
    public void mod(Article a) {
        articleDao.save(a);
    }

    @Override
    public void del(Integer articleId) {
        articleDao.deleteById(articleId);
    }

    @Override
    public Article get(Integer articleId) {
        Article a = articleDao.getOne(articleId);
        return a;
    }

    @Override
    @Cacheable(value = "getAllArticles")
    public Page<Article> query(Pageable pageable) {
        logger.info("第一次执行后将会开启缓存-------------");
        return articleDao.findAll(pageable);
    }

    @Override
    public Page<Article> query(String key, Pageable pageable) {
        return articleDao.findByLikeOrderPage(key, pageable);
    }

    //多条件动态组合查询 a为封装的查询条件
    @Override
    public Page<Article> query(Article a, Pageable pageable) {
        return articleDao.queryConDo(a, pageable);
    }
}
