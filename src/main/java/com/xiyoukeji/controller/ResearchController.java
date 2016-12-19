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
 * 4-创道研究
 *
 * Created by Matilda on 2016/12/17.
 */

@Controller
public class ResearchController {

    @Resource
    ArticleService articleService;

    @RequestMapping("/research/getReportList")
    @ResponseBody
    public Map getReportList() {
        Map<String, Object> map = new HashMap<>();
        map.put("list", articleService.getArticleDisplayList(80, ArticleType.RESEARCH_REPORT.name()));
        return map;
    }

    @RequestMapping(value = "/research/getReport", method = RequestMethod.POST)
    @ResponseBody
    public Article getReportById(Integer id) {
        if(id == null) return null;
        return articleService.getArticleById(id);
    }

    @RequestMapping("/research/getObservationList")
    @ResponseBody
    public Map getObservationList() {
        Map<String, Object> map = new HashMap<>();
        map.put("list", articleService.getArticleDisplayList(80, ArticleType.WEEK_OBSERVATION.name()));
        return map;
    }

    @RequestMapping(value = "/research/getObservation", method = RequestMethod.POST)
    @ResponseBody
    public Article getObservationById(Integer id) {
        if(id == null) return null;
        return articleService.getArticleById(id);
    }
}
