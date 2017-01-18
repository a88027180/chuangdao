package com.xiyoukeji.service;

import com.xiyoukeji.entity.Article;
import com.xiyoukeji.entity.Setting;
import com.xiyoukeji.entity.Video;
import com.xiyoukeji.tools.BaseDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Matilda on 2016/12/12.
 */

@Service
public class SettingService {

    @Resource
    SessionFactory sessionFactory;
    @Resource
    BaseDao<Setting> settingBaseDao;
    @Resource
    FileService fileService;
    @Resource
    ArticleService articleService;

    public Setting getSettingById(Integer id) {
        return settingBaseDao.get(Setting.class, id);
    }

    public Setting getSettingByName(String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("name",name);
        return settingBaseDao.get("from Setting s where s.name = :name", map);
    }

    public List<Setting> getContact() {
        List<Setting> list = new ArrayList<>();
        list.add(settingBaseDao.get("from Setting s where s.name = 'hotline'"));
        list.add(settingBaseDao.get("from Setting s where s.name = 'fax'"));
        list.add(settingBaseDao.get("from Setting s where s.name = 'email'"));
        list.add(settingBaseDao.get("from Setting s where s.name = 'weibo'"));
        list.add(settingBaseDao.get("from Setting s where s.name = 'weixin'"));
        return list;
    }

    public Set<String> getTypes() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select s.type from Setting as s where s.type like '%地址%'");
        Set<String> set = new HashSet<>();
        set.addAll(query.list());
        if(set.contains(null))
            set.remove(null);
        return set;
    }

    public List<Setting> getFindByType(String type) {
        return settingBaseDao.find("from Setting as s where s.type = '"+type+"'");
    }

    public void addSetting(Setting setting) {
        settingBaseDao.save(setting);
    }

    public void deleteSetting(Integer id) {
        Setting setting = getSettingById(id);
        settingBaseDao.delete(setting);
    }

    public void editSetting(Setting setting) {
        settingBaseDao.update(setting);
    }
    public List<Setting> getLinks() {
        return settingBaseDao.find("from Setting as s where s.name = 'links'");
    }

    public void setHomeVideo(Integer id) {
        Setting setting = new Setting();
        setting.setDescription("首页视频");
        setting.setName("home_video");
        setting.setValue(String.valueOf(id));
        settingBaseDao.save(setting);
    }

    public void setHomeImg(String url) {
        Setting setting = new Setting();
        setting.setDescription("首页图片");
        setting.setName("home_image");
        setting.setValue(url);
        settingBaseDao.save(setting);
    }

    public void setArticleSquare(String title) {
        int id = articleService.getId(title);
        setArticleSquare(id);
    }

    public void setArticleSquare(Integer id) {
        Setting setting = new Setting();
        setting.setDescription("首页文章方块");
        setting.setName("home_article_square");
        setting.setValue(String.valueOf(id));
        settingBaseDao.save(setting);
    }

    public Video getHomeVideo() {
        Setting setting = settingBaseDao.get("from Setting as s where s.name = 'home_video'");
        Video video = new Video();
        if(setting != null) {
            Integer id = Integer.valueOf(setting.getValue());
            video = fileService.getVideoById(id);
            if(video == null)
                video = new Video();
        }
        return video;
    }

    public List<Integer> getArticleSquareID(){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select s.value from Setting as s where s.name = 'home_article_square'");
        List<String> ids = query.list();
        List<Integer> res = new ArrayList<>();
        if(ids.size()!=0) {
            for(String id: ids) {
                res.add(Integer.valueOf(id));
            }
        }
        return res;
    }

    public List<String> getArticleSquareTitle() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select s.value from Setting as s where s.name = 'home_article_square'");
        List<String> ids = query.list();
        List<String> list = new ArrayList<>();
        if(ids.size() != 0) {
            for(String id: ids) {
                if(Integer.valueOf(id)==-1)
                    list.add("(文章不存在)");
                else
                    list.add(articleService.getArticleById(Integer.valueOf(id)).getTitle());
            }
        }
        return list;
    }

    public List<String> getHomeImg() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select s.value from Setting as s where s.name = 'home_image'");
        List<String> ids = query.list();
        return query.list();
    }

    // 可以不要
    public void editArticleSquare(Integer pre_id, Integer cur_id) {
        Setting setting = settingBaseDao.get("from Setting as s where s.value = '"+pre_id+"'");
        if(setting == null)
            return;
        setting.setValue(String.valueOf(cur_id));
        settingBaseDao.update(setting);
    }

    // 可以不要
    public void editHomeImg(String pre_url, String cur_url) {
        Setting setting = settingBaseDao.get("from Setting as s where s.value = '"+pre_url+"'");
        if(setting == null)
            return;
        setting.setValue(cur_url);
        settingBaseDao.update(setting);
    }

    public void deleteHomeVideo() {
        Setting setting = settingBaseDao.get("from Setting as s where s.name = 'home_video'");
        if(setting == null)
            return;
        settingBaseDao.delete(setting);
    }

    public void deleteArticleSquares() {
        List<Setting> settings = settingBaseDao.find("from Setting as s where s.name = 'home_article_square'");
        if(settings == null)
            return;
        for (Setting s: settings) {
            settingBaseDao.delete(s);
        }

    }

    public void deleteHomeImgs() {
        List<Setting> settings = settingBaseDao.find("from Setting as s where s.name = 'home_image'");
        if(settings == null)
            return;
        for (Setting s: settings) {
            settingBaseDao.delete(s);
        }
    }

    public void addFindUs(String[] names, String[] values) {
        String type=null;
        for(int i=0; i<names.length; i++) {
            if(names[i].equals("type")) {
                type = values[i];
            }
        }

        for(int i=0; i<names.length; i++) {
            if(!names[i].equals("type")) {
                Setting setting = new Setting();
                setting.setName(names[i]);
                setting.setValue(values[i]);
                setting.setType(type);
                settingBaseDao.save(setting);
            }
        }
    }

    public void editFindUs(Integer[] ids, String[] values, String type) {
        for(int i=0; i<ids.length; i++) {
            Setting setting = getSettingById(ids[i]);
            setting.setValue(values[i]);
            setting.setType(type);
            settingBaseDao.update(setting);
        }
    }

}
