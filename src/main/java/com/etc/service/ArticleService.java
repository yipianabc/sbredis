package com.etc.service;

import com.etc.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleService {

    public void add(Article a);
    public void mod(Article a);
    public void del(Integer articleId);
    public Article get(Integer articleId);
    public Page<Article> query(Pageable pageable);
    Page<Article> query(String key, Pageable pageable);
    //多条件动态组合查询,带分页排序
    public Page<Article> query(Article a,Pageable pageable);
}
