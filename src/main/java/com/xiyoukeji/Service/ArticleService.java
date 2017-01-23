package com.xiyoukeji.service;

import com.xiyoukeji.entity.Article;
import com.xiyoukeji.tools.ArticleType;
import com.xiyoukeji.tools.BaseDaoImpl;
import com.xiyoukeji.tools.HtmlExtract;
import org.apache.commons.lang.StringEscapeUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
    @Resource
    SessionFactory sessionFactory;

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

            // 发展历程和企业内刊多一条url
            if(type.equals(ArticleType.DEVELOPMENT.name()) || type.equals(ArticleType.MAGAZINE.name()))
            {
                map.put("url", a.getUrl());
            }

            List<String> imgList = a.getImg();
            if (imgList.size() == 0)
                map.put("img", ""); // 图片不存在
            else
                map.put("img", a.getImg().get(0));


            if(a.getSummary()!=null) {
                map.put("summary", a.getSummary());

            } else {
                length = 80;
                String summary = HtmlExtract.delHTMLTag(a.getText());
                if(summary.length() < length)
                {
                    map.put("summary",summary);
                }
                else
                {
                    summary = summary.replaceAll("\r\n","").replaceAll("\t","").replaceAll(" ","");
                    int index = summary.indexOf("项目简介");
                    if(index >=0)
                        summary = summary.substring(index+4);
                    index = summary.indexOf("项目介绍");
                    if(index >=0)
                        summary = summary.substring(index+4);
                    String result = StringEscapeUtils.unescapeHtml(summary).substring(0,length);
//                    int newLength = length;
//                    int lastAnd = result.lastIndexOf("&");
//                    int lastSemicolon = result.lastIndexOf(";");
//
//                    while (lastAnd>0 && lastAnd>lastSemicolon) {
//                        newLength += 7;     // 最长的字符实体如：&middot;
//                        result = summary.substring(0, newLength);
//                        lastAnd = result.lastIndexOf("&");
//                        lastSemicolon = result.lastIndexOf(";");
//                    }

                    map.put("summary", result+"...");
                }
            }

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

//    public List<Object[]> getListAll() {
//        Session session = sessionFactory.getCurrentSession();
//        List<Object[]> res = session.createQuery("select a.id, a.title from Article a").list();
//        if(res.size()!=0) {
//
//        }
//        return res;
//    }

    public int getId(String title) {
        Article article = articleBaseDao.get("from Article a where a.title = '"+title+"'");
        if(article == null)
            return -1;
        else
            return article.getId();
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
