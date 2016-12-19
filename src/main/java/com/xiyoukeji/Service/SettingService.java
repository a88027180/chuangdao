package com.xiyoukeji.Service;

import com.xiyoukeji.entity.Setting;
import com.xiyoukeji.tools.BaseDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Matilda on 2016/12/12.
 */

@Service
public class SettingService {

    @Resource
    BaseDao<Setting> settingBaseDao;

    public Setting getSetting(String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("name",name);
        return settingBaseDao.get("from Setting s where s.name = :name", map);
    }

    public List<Setting> getContactInfo() {
        List<Setting> list = new ArrayList<>();
        list.add(settingBaseDao.get("from Setting s where s.name = 'hotline'"));
        list.add(settingBaseDao.get("from Setting s where s.name = 'fax'"));
        list.add(settingBaseDao.get("from Setting s where s.name = 'email'"));
        list.add(settingBaseDao.get("from Setting s where s.name = 'address'"));
        list.add(settingBaseDao.get("from Setting s where s.name = 'phone'"));
        list.add(settingBaseDao.get("from Setting s where s.name = 'cellphone'"));
        list.add(settingBaseDao.get("from Setting s where s.name = 'zipcode'"));
        return list;
    }

}
