package com.xiyoukeji.controller;

import com.sun.javafx.geom.Vec2d;
import com.xiyoukeji.entity.Setting;
import com.xiyoukeji.entity.Video;
import com.xiyoukeji.service.ArticleService;
import com.xiyoukeji.service.CarouselService;
import com.xiyoukeji.entity.Article;
import com.xiyoukeji.entity.Carousel;
import com.xiyoukeji.service.SettingService;
import com.xiyoukeji.tools.ArticleType;
import com.xiyoukeji.tools.State;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 1-首页
 *
 * Created by Matilda on 2016/12/19.
 */

@Controller
public class HomeController {

    @Resource
    CarouselService carouselService;
    @Resource
    ArticleService articleService;
    @Resource
    SettingService settingService;

    @RequestMapping("/getCarouselList")
    @ResponseBody
    public Map getCarouselList() {
        Map<String, List<Carousel>> map = new HashMap<>();
        map.put("list", carouselService.getCarouselList());
        return map;
    }

    @RequestMapping(value = "/searchArticle", method = RequestMethod.POST)
    @ResponseBody
    public Map searchArticle(String keyWord) {
        // 如果为空 xxxxx

        // 如果不为空
        Map<String, List<Article>> map = new HashMap<>();
        map.put("list", articleService.getArticleByTitle(keyWord));
        return map;
    }

    @RequestMapping("/getLinks")
    @ResponseBody
    public Map getLinks() {
        Map<String, List<Setting>> map = new HashMap<>();
        map.put("list", settingService.getLinks());
        return map;
    }

    @RequestMapping("/getHomeVideo")
    @ResponseBody
    public Video getHomeVideo() {
        return settingService.getHomeVideo();
    }

    @RequestMapping("/getArticleSquare")
    @ResponseBody
    public Map getArticleSquare() {
        List<Integer> ids = settingService.getArticleSquareID();
        Map<String, List<Article>> map = new HashMap<>();
        List<Article> articles = new ArrayList<>();
        if(ids.size() != 0) {
            for (Integer id: ids) {
                Article article = articleService.getArticleById(id);
                if(article == null)
                    article = new Article();
                articles.add(article);
            }
        }
        map.put("list", articles);
        return map;
    }

    @RequestMapping("/getArticleSquareID")
    @ResponseBody
    public Map getArticleSquareID() {
        Map<String, List<Integer>> map = new HashMap<>();
        map.put("list", settingService.getArticleSquareID());
        return map;
    }

    @RequestMapping("/getHomeArticle")
    @ResponseBody
    public Article getHomeArticle() {
        List<Article> list = articleService.getArticleByType(ArticleType.HOME_ARTICLE.name());
        if(list.size() == 0)
            return null;
        return list.get(0);
    }

    @RequestMapping("/getHomeImg")
    @ResponseBody
    public Map getHomeImg() {
        Map<String, List<String>> map = new HashMap<>();
        map.put("list", settingService.getHomeImg());
        return map;
    }

    // test upload
    @RequestMapping(value = "/test")
    public String testUpload() {
        return "test";
    }



}
