package com.xiyoukeji.controller;

import com.xiyoukeji.Service.ArticleService;
import com.xiyoukeji.Service.CarouselService;
import com.xiyoukeji.entity.Article;
import com.xiyoukeji.entity.Carousel;
import com.xiyoukeji.tools.State;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
    HttpServletRequest request;

    @RequestMapping("/getCarouselList")
    @ResponseBody
    public Map getCarouselList() {
        Map<String, List<Carousel>> map = new HashMap<>();
        map.put("list", carouselService.getCarouselList());
        return map;
    }

    @RequestMapping("/getArticle")
    @ResponseBody
    public Map getArticleByTitle(String keyWord) {
        // 如果为空 xxxxx

        // 如果不为空
        Map<String, List<Article>> map = new HashMap<>();
        map.put("list", articleService.getArticleByTitle(keyWord));
        return map;
    }

    @RequestMapping("/uploadFile")
    @ResponseBody
    public Map uploadFile(MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        if(file.isEmpty())
        {
            map.put("state", State.FILE_EMPTY.value());
            map.put("detail", "File is empty");
            return map;
        }

        // 保存路径
        String dirPath = request.getSession().getServletContext().getRealPath("/upload/");
        File dir = new File(dirPath);   // 目录不存在则创建
        if(!dir.isDirectory())
        {
            boolean res = dir.mkdirs();
            if (!res)
            {
                map.put("state", State.FAIL.value());
                map.put("detail", "Upload directory create failed");
                return map;
            }
        }

        String filePath = dirPath+file.getOriginalFilename();
        System.out.println("filePath: "+filePath);
        // 转存文件
        try {
            file.transferTo(new File(filePath));
            map.put("state", State.SUCCESS.value());
            map.put("detail", "Upload success");
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            map.put("state", State.FAIL.value());
            map.put("detail", "Upload failed");
            return map;
        }
    }

}
