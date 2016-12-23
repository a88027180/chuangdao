package com.xiyoukeji.controller;

import com.xiyoukeji.entity.Article;
import com.xiyoukeji.service.ArticleService;
import com.xiyoukeji.tools.State;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matilda on 2016/12/20.
 */

@Controller
public class ArticleController {

    @Resource
    ArticleService articleService;

    @ExceptionHandler
    @ResponseBody
    public Map processException(RuntimeException e){
        Map<String, Object> map = new HashMap<>();
        map.put("state", State.FAIL.value());
        map.put("detail", "Exception occurred");
        return map;
    }

    @RequestMapping(value = "/addArticle", method = RequestMethod.POST)
    @ResponseBody
    public Map addArticle(Article article) {
        articleService.addArticle(article);
        Map<String, Object> map = new HashMap<>();
        map.put("state", State.SUCCESS.value());
        map.put("detail", State.SUCCESS.name());
        return map;
    }

    @RequestMapping(value = "/deleteArticle", method = RequestMethod.POST)
    @ResponseBody
    public Map deleteArticle(Integer id) {
        articleService.deleteArticle(id);
        Map<String, Object> map = new HashMap<>();
        map.put("state", State.SUCCESS.value());
        map.put("detail", State.SUCCESS.name());
        return map;
    }

    @RequestMapping(value = "/editArticle", method = RequestMethod.POST)
    @ResponseBody
    public Map editArticle(Article article) {
        articleService.editArticle(article);
        Map<String, Object> map = new HashMap<>();
        map.put("state", State.SUCCESS.value());
        map.put("detail", State.SUCCESS.name());
        return map;
    }

}
