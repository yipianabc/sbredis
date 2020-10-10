package com.etc.controller;

import com.etc.entity.Article;
import com.etc.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;

@Controller
public class ArticleController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ArticleService articleService;

    @RequestMapping("/articledel/{articleId}")
    public ModelAndView del(@PathVariable Integer articleId){
        articleService.del(articleId);
        ModelAndView mv = new ModelAndView(new InternalResourceView("/articlequery")) ;
        mv.addObject("msg","删除成功");
        return mv;
    }

    @RequestMapping("/articlemod")
    public ModelAndView mod(Article a){
        articleService.mod(a);
        ModelAndView mv = new ModelAndView(new InternalResourceView("/articleget/"+a.getArticleId()));
        mv.addObject("msg","修改成功");
        return mv;
    }

    @RequestMapping("/articleadd")
    public ModelAndView add(Article a){
        articleService.add(a);
        ModelAndView mv = new ModelAndView(new RedirectView("articleget/"+a.getArticleId()));
        mv.addObject("msg","新加成功");
        return mv;
    }

    @RequestMapping("/articleget/{articleId}")
    public ModelAndView get(@PathVariable Integer articleId){
        ModelAndView mv = new ModelAndView("articleget");
        mv.addObject("a",articleService.get(articleId));
        return mv;
    }

    @RequestMapping("/articletoadd")
    public ModelAndView toadd(){
        ModelAndView mv = new ModelAndView("articleadd");
        return mv;
    }

    @RequestMapping("/articlequery")
    public ModelAndView query(Article a, @PageableDefault(value = 5,sort = {"articleId"},direction = Sort.Direction.DESC)Pageable pageable){
        ModelAndView mv = new ModelAndView("articlequery");
        Page<Article> p = articleService.query(a,pageable);
        mv.addObject("p",p);
        return mv;
    }

    //开启缓存！！！！！！！！！！！！！！！！
    @RequestMapping("/articlequeryByCache")
    @ResponseBody
    public Page<Article> queryByCache(@PageableDefault(value = 5,sort = {"articleId"},direction = Sort.Direction.DESC)Pageable pageable){
        logger.info("只有第一次查询才会执行sql语句，后面从缓存中读取");
        Page<Article> p = articleService.query(pageable);
        return p;
    }
    @RequestMapping("/articlequery2")
    @ResponseBody
    public Page<Article> query2(Article a, @PageableDefault(value = 5,sort = {"articleId"},direction = Sort.Direction.DESC)Pageable pageable){
        logger.info("只有第一次查询才会执行sql语句，后面从缓存中读取");
        ModelAndView mv = new ModelAndView("articlequery");
        Page<Article> p = articleService.query(a,pageable);
        return p;
    }

}
