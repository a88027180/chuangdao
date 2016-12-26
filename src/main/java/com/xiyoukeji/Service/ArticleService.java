package com.xiyoukeji.service;

import com.xiyoukeji.entity.Article;
import com.xiyoukeji.tools.BaseDaoImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Matilda on 2016/12/13.
 */

@Service
public class ArticleService {

    @Resource
    BaseDaoImpl<Article> articleBaseDao;

    public Article getArticleById(Integer id) {
        return articleBaseDao.get(Article.class, id);
    }

    public List<Article> getArticleByType(String type) { // 异常
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        List<Article> list = articleBaseDao.find("from Article a where a.type = :type", map);
        Collections.sort(list); // sort才能调用重写的compareTo, reverse不能，所以将reverse实现在compareTo中
        return list;
    }

    public List<Article> getArticleByTitle(String keyWord) {
        Map<String, Object> map = new HashMap<>();
        map.put("keyword", "%"+keyWord+"%");
        return articleBaseDao.find("from Article a where a.title like :keyword", map);
    }

    public List<Map<String, Object>> getArticleDisplayList(int length, String type) {
        List<Map<String, Object>> list =  new ArrayList<>();
        List<Article> articleList = getArticleByType(type);

        for (Article a: articleList) {

            Map<String, Object> map = new HashMap<>();
            map.put("id", a.getId());
            map.put("name", a.getTitle());
            map.put("time", a.getTime());

//            // 发展历程包含：id, name, time (可以空的summary)
//            if(type.equals(ArticleType.DEVELOPMENT.name()))
//            {
//                list.add(map);
//                continue;
//            }


            List<String> imgList = a.getImg();
            if (imgList.size() == 0)
                map.put("img", ""); // 图片不存在
            else
                map.put("img", a.getImg().get(0));

//            // 添加预览文字(从文章头部截取)
//            String text = a.getText();
//            text = text.replaceAll("<.+?>", "");
//            if(text.length() < length)
//            {
//                map.put("text",text);
//            }
//            else
//            {
//                // 避免字符实体如&nbsp;被切割
//                String result = text.substring(0,length);
//                int newLength = length;
//                int lastAnd = result.lastIndexOf("&");
//                int lastSemicolon = result.lastIndexOf(";");
//
//                while (lastAnd>0 && lastAnd>lastSemicolon) {
//                    newLength += 7;     // 最长的字符实体如：&middot;
//                    result = text.substring(0, newLength);
//                    lastAnd = result.lastIndexOf("&");
//                    lastSemicolon = result.lastIndexOf(";");
//                }
//                map.put("text", result);
//            }
            map.put("summary", a.getSummary());

            list.add(map);
        }

        // 倒序排列，时间近或id大
        Collections.sort(list, (o1, o2) -> {
            if(o1.get("time")!=null && o2.get("time")!=null)
                return o2.get("time").toString().compareTo(o1.get("time").toString());
            return o2.get("id").toString().compareTo(o1.get("id").toString());
        });
        return list;
    }

    public void addArticle(Article article) {
        articleBaseDao.save(article);
    }

    public void deleteArticle(Integer id) {
        Article article = getArticleById(id);
        articleBaseDao.delete(article);
    }

    public void editArticle(Article article) {
        articleBaseDao.update(article);
    }

}
