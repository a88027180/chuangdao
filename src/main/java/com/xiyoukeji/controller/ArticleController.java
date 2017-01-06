package com.xiyoukeji.controller;

import com.xiyoukeji.entity.Article;
import com.xiyoukeji.service.ArticleService;
import com.xiyoukeji.service.SettingService;
import com.xiyoukeji.tools.ArticleType;
import com.xiyoukeji.tools.State;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Matilda on 2016/12/20.
 */

@Controller
public class ArticleController {

    @Resource
    ArticleService articleService;
    @Resource
    SettingService settingService;

    @RequestMapping(value = "/addArticle", method = RequestMethod.POST)
    @ResponseBody
    public Map addArticle(Article article) {
        Map<String, Object> map = new HashMap<>();
        // 添加首页文章不能超过1篇
        if(article.getType().equals(ArticleType.HOME_ARTICLE.name()) ||
                article.getType().equals(ArticleType.RESPONSIBILITY.name())) {
            List<Article> list = articleService.getArticleByType(ArticleType.HOME_ARTICLE.name());
            if(list.size() != 0) {
//                map.put("state", State.SUCCESS.value());
//                map.put("detail", State.SUCCESS.desc()+": 1");
//                return map;
                // 若已经存在则修改文章, 添加缺少的id参数
                article.setId(list.get(0).getId());
                articleService.editArticle(article);
            }
            else {
                articleService.addArticle(article);
            }
        }
        else {
            articleService.addArticle(article);
        }

        map.put("state", State.SUCCESS.value());
        map.put("detail", State.SUCCESS.desc());
        return map;
    }

    @RequestMapping(value = "/deleteArticle", method = RequestMethod.POST)
    @ResponseBody
    public Map deleteArticle(Integer id) {
        articleService.deleteArticle(id);
//        settingService.deleteArticleSquares();
        Map<String, Object> map = new HashMap<>();
        map.put("state", State.SUCCESS.value());
        map.put("detail", State.SUCCESS.desc());
        return map;
    }

    @RequestMapping(value = "/editArticle", method = RequestMethod.POST)
    @ResponseBody
    public Map editArticle(Article article) {
        articleService.editArticle(article);
        Map<String, Object> map = new HashMap<>();
        map.put("state", State.SUCCESS.value());
        map.put("detail", State.SUCCESS.desc());
        return map;
    }

    @RequestMapping("/getArticleForPage")
    @ResponseBody
    public Map getArticleForPage(int start, int length, String type) {
        List<Map<String, Object>> articleList = articleService.getArticleDisplayList(0, type);
        List<Map<String, Object>> result = new ArrayList<>();
        int end = start+length;
        if(end > articleList.size())
            end = articleList.size();
        for (int i = start; i < end; i++) {
            result.add(articleList.get(i));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("countAll", articleList.size());
        map.put("list", result);
        return map;
    }

    @RequestMapping(value = "/setArticleSquare", method = RequestMethod.POST)
    @ResponseBody
    public Map setArticleSquare(Integer id){
        List<Integer> ids = settingService.getArticleSquareID();
        Map<String, Object> map = new HashMap<>();
        if(ids.size()==5) {
            map.put("state", State.SET_EXCEED.value());
            map.put("detail", State.SET_EXCEED.desc()+": 5");
            return map;
        }
        settingService.setArticleSquare(id);

        map.put("state", State.SUCCESS.value());
        map.put("detail", State.SUCCESS.desc());
        return map;
    }

    @RequestMapping(value = "/setArticleSquares", method = RequestMethod.POST)
    @ResponseBody
    public Map setArticleSquares(@RequestParam(value = "ids") Integer[] ids){
        Map<String, Object> map = new HashMap<>();
        settingService.deleteArticleSquares();  // 清除原先设置信息
        for (Integer id: ids) {
            settingService.setArticleSquare(id);
        }
        map.put("state", State.SUCCESS.value());
        map.put("detail", State.SUCCESS.desc());
        return map;
    }

    @RequestMapping(value = "/editArticleSquare")
    @ResponseBody
    public Map editArticleSquare(Integer pre_id, Integer cur_id){
        settingService.editArticleSquare(pre_id, cur_id);
        Map<String, Object> map = new HashMap<>();
        map.put("state", State.SUCCESS.value());
        map.put("detail", State.SUCCESS.desc());
        return map;
    }
}
