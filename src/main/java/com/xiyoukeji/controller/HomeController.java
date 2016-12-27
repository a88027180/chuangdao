package com.xiyoukeji.controller;

import com.xiyoukeji.entity.Setting;
import com.xiyoukeji.entity.Video;
import com.xiyoukeji.service.ArticleService;
import com.xiyoukeji.service.CarouselService;
import com.xiyoukeji.entity.Article;
import com.xiyoukeji.entity.Carousel;
import com.xiyoukeji.service.SettingService;
import com.xiyoukeji.service.VideoService;
import com.xiyoukeji.tools.State;
import com.xiyoukeji.tools.UploadType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
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
    VideoService videoService;
    @Resource
    SettingService settingService;

    @RequestMapping("/getCarouselList")
    @ResponseBody
    public Map getCarouselList() {
        Map<String, List<Carousel>> map = new HashMap<>();
        map.put("list", carouselService.getCarouselList());
        return map;
    }

    @RequestMapping(value = "/getArticle", method = RequestMethod.POST)
    @ResponseBody
    public Map getArticleByTitle(String keyWord) {
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

    // test upload
    @RequestMapping(value = "/test")
    public String testUpload() {
        return "test";
    }

}
