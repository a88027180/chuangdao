package com.xiyoukeji.service;

import com.xiyoukeji.entity.Setting;
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
        String hql = "select type from Setting";
        Query query = session.createQuery(hql);
        Set<String> set = new HashSet<>();
        set.addAll(query.list());
        if(set.contains(null))
            set.remove(null);
        return set;
    }

    public List<Setting> getFindByType(String type) {
        return settingBaseDao.find("from Setting s where s.type = '"+type+"'");
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
        return settingBaseDao.find("from Setting s where s.name = 'links'");
    }
}
