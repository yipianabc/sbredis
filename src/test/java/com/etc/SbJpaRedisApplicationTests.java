package com.etc;

import com.etc.entity.Article;
import com.etc.service.ArticleService;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SbJpaRedisApplicationTests {


    @Rule
    public ContiPerfRule cr = new ContiPerfRule();

    @Autowired
    private ArticleService articleService;

    /**
     * invocations 1w次查询,200个线程同时操作getArticle方法
     */
    @Test
    @PerfTest(invocations = 10000,threads = 200)
    public void contextLoads() {
        Pageable pageable= PageRequest.of(1,3);
        Page<Article> list  = articleService.query(pageable);
        list.forEach(System.out::println);
    }

}


