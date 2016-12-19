package com.xiyoukeji.controller;

import com.xiyoukeji.Service.ArticleService;
import com.xiyoukeji.entity.Article;
import com.xiyoukeji.tools.ArticleType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 5-新闻中心
 *
 * Created by Matilda on 2016/12/17.
 */

@Controller
public class NewsController {
    @Resource
    ArticleService articleService;

    @RequestMapping("/newscenter/getAnnouncementList")
    @ResponseBody
    public Map getAnnouncementList() {
        Map<String, Object> map = new HashMap<>();
        map.put("list", articleService.getArticleDisplayList(80, ArticleType.ANNOUNCEMENT.name()));
        return map;
    }

    @RequestMapping(value = "/newscenter/getAnnouncement", method = RequestMethod.POST)
    @ResponseBody
    public Article getAnnouncementById(Integer id) {
        if(id == null) return null;
        return articleService.getArticleById(id);
    }

    @RequestMapping("/newscenter/getCompanyNewsList")
    @ResponseBody
    public Map getCompanyNewsList() {
        Map<String, Object> map = new HashMap<>();
        map.put("list", articleService.getArticleDisplayList(80, ArticleType.COMPANY_NEWS.name()));
        return map;
    }

    @RequestMapping(value = "/newscenter/getCompanyNews", method = RequestMethod.POST)
    @ResponseBody
    public Article getCompanyNewsById(Integer id) {
        if(id == null) return null;
        return articleService.getArticleById(id);
    }

    @RequestMapping("/newscenter/getIndustryNewsList")
    @ResponseBody
    public Map getIndustryNewsList() {
        Map<String, Object> map = new HashMap<>();
        map.put("list", articleService.getArticleDisplayList(80, ArticleType.INDUSTRY_NEWS.name()));
        return map;
    }

    @RequestMapping(value = "/newscenter/getIndustryNews", method = RequestMethod.POST)
    @ResponseBody
    public Article getIndustryNewsById(Integer id) {
        if(id == null) return null;
        return articleService.getArticleById(id);
    }


}
