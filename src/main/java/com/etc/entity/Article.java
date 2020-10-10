package com.etc.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "article")
public class Article implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Integer articleId;//文章编号
    @Column(name = "article_title")
    private String articleTitle;//文章标题
    @Column(name = "article_Content")
    private String articleContent;//文章内容
    @Column(name = "article_dt")
    private String articleDt;//文章发表时间

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public String getArticleDt() {
        return articleDt;
    }

    public void setArticleDt(String articleDt) {
        this.articleDt = articleDt;
    }
    public Article() {

    }
    public Article(Integer articleId, String articleTitle, String articleContent, String articleDt) {
        this.articleId = articleId;
        this.articleTitle = articleTitle;
        this.articleContent = articleContent;
        this.articleDt = articleDt;
    }

}
