package com.etc.dao;

import com.etc.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public interface ArticleDao extends JpaRepository<Article,Integer>, JpaSpecificationExecutor<Article>, Serializable {

    Page<Article> findAll(Pageable pageable);
    //动态查询条件,带分页排序
    public default Page<Article> queryConDo(Article a,Pageable pageable){
        return this.findAll(new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();//用来存储查询条件
                if(a != null){
                    if(a.getArticleTitle() != null && !"".equals(a.getArticleTitle())){
                        Path<String> path = root.get("articleTitle");
                        list.add(criteriaBuilder.like(path,"%"+a.getArticleTitle()+"%"));//构造条件,存储到list中
                    }
                    if(a.getArticleContent() != null && !"".equals(a.getArticleContent())){
                        Path<String> path = root.get("articleContent");
                        list.add(criteriaBuilder.like(path,"%"+a.getArticleContent()+"%"));//构造条件,存储到list中
                    }
                }
                Predicate pp[] = new Predicate[list.size()];//构造长度是list.size()大小的Predicate数组
                list.toArray(pp);//把集合中的元素设置到数组中
                criteriaQuery.where(pp);//加查询条件
                return null;
            }
        },pageable);
    }
    //自定义根据文章标题模糊查询，排序+分页
    @Query("select a from Article a where a.articleTitle like ?1")
    public Page<Article> findByLikeOrderPage(String key, Pageable pageable);
    //根据文章发表时间之前查询
    public List<Article> findByArticleDtBefore(String articleDt);
    //根据文章标题模糊查询
    public List<Article> findByArticleTitleLike(String articleTitle);
    //如果命名没有按规则，也可以实现查询，但需要加注解@Query,写jpql语句
    //根据文章标题来模糊查询
    @Query("from Article where articleTitle like ?1")
    public List<Article> findByArticleTitleXXX(String articleTitle);
    //根据文章标题、文章内容一起来模糊查询
    @Query("from Article where articleTitle like ?1 and articleContent like ?2")
    public List<Article> findByArticleTitleContentXXX(String key1,String key2);
    //根据文章编号来查询，使用占位名
    @Query("from Article where articleId =:articleId")
    public Article findByArticleIdXXX(Integer articleId);
    //根据文章编号来查询，使用本地的sql语句
    @Query(value = "select * from Article where article_Id =:articleId",nativeQuery = true)
    public Article findByArticleIdXXX2(Integer articleId);

}
