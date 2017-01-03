package com.xiyoukeji.service;

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

    public void setHomeVideo(Video video) {
        // 要解决重复设置的问题
        Setting setting = new Setting();
        setting.setDescription(video.getImg()); // 图片
        setting.setName("home_video");
        setting.setValue(video.getUrl());
        settingBaseDao.save(setting);
    }

    public void setHomeImg(String url) {
        Setting setting = new Setting();
        setting.setDescription("首页图片");
        setting.setName("home_image");
        setting.setValue(url);
        settingBaseDao.save(setting);
    }

    public void setArticleSquare(Integer id) {
        Setting setting = new Setting();
        setting.setDescription("首页文章方块");
        setting.setName("home_article_square");
        setting.setValue(String.valueOf(id));
        settingBaseDao.save(setting);
    }

    public Setting getHomeVideo() {
        Setting setting = settingBaseDao.get("from Setting as s where s.name = 'home_video'");
        return setting;
    }

    public List<String> getArticleSquareID() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select s.value from Setting as s where s.name = 'home_article_square'");
        List<String> ids = query.list();
        return query.list();
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

    public void deleteHomeImg(String url) {
        Setting setting = settingBaseDao.get("from Setting as s where s.name = 'home_image' and s.value = '"+url+"'");
        if(setting == null)
            return;
        settingBaseDao.delete(setting);
    }


}
