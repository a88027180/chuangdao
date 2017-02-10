package com.xiyoukeji.controller;

import com.xiyoukeji.service.ArticleService;
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
 * 3-产品与服务
 *
 * Created by Matilda on 2016/12/14.
 */

@Controller
public class ProductController {

    @Resource
    ArticleService articleService;

    @RequestMapping("/getGalleryList")
    @ResponseBody
    public Map getGalleryList() {
        Map<String, Object> map = new HashMap<>();
        map.put("list", articleService.getArticleDisplayList(0, ArticleType.PROJECT_GALLERY.name()));
        return map;
    }

    @RequestMapping(value = "/getGallery", method = RequestMethod.POST)
    @ResponseBody
    public Article getGalleryDetailById(Integer id) {
        if(id == null) return null;
        return articleService.getArticleById(id);
    }

    @RequestMapping("/getDynamicList")
    @ResponseBody
    public Map getDynamicList() {
        Map<String, Object> map = new HashMap<>();
        map.put("list", articleService.getArticleDisplayList(80, ArticleType.PROJECT_DYNAMICS.name()));
        return map;
    }

    @RequestMapping(value = "/getDynamic", method = RequestMethod.POST)
    @ResponseBody
    public Article getDynamicById(Integer id) {
        if(id == null) return null;
        return articleService.getArticleById(id);
    }

//    @RequestMapping("/getServiceExperience")
//    @ResponseBody
//    public Article getServiceExperience () {
//        List<Article> list = articleService.getArticleByType(ArticleType.SERVICE_EXPERIENCE.name());
//        if(list.size() == 0)
//            return null;
//        return list.get(0);
//    }
//
//    @RequestMapping("/getRiskControl")
//    @ResponseBody
//    public Article getRiskControl () {
//        List<Article> list = articleService.getArticleByType(ArticleType.COMPLIANCE_RISK_CONTROL.name());
//        if(list.size() == 0)
//            return null;
//        return list.get(0);
//    }


}
