package com.xiyoukeji.controller;

import com.xiyoukeji.service.SettingService;
import com.xiyoukeji.entity.Setting;
import com.xiyoukeji.tools.State;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * 8-联系我们
 *
 * Created by Matilda on 2016/12/14.
 */

@Controller
public class ContactController {

    @Resource
    SettingService settingService;

    @RequestMapping("/getContact")
    @ResponseBody
    public Map getContact() {
        Map<String, List<Setting>> map = new HashMap<>();
        map.put("list", settingService.getContact());
        return map;
    }

    @RequestMapping("/getFind")
    @ResponseBody
    public Map getFind() {
        Map<String, List<Map<String, Object>>> map = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();
        Set<String> types = settingService.getTypes();
        for (String type: types) {
            List<Setting> finds = settingService.getFindByType(type);
            Map<String, Object> findMap = new HashMap<>();
            findMap.put("type", type);
            for (Setting find: finds) {
                Map<String, Object> settingMap = new HashMap<>();
                settingMap.put("id", find.getId());
                settingMap.put("value", find.getValue());

                if(find.getName().equals("address")) {
                    List<String> l = find.getImg();
                    if(l.size() == 0)
                        settingMap.put("img", "" );
                    else
                        settingMap.put("img", l.get(0));
                }
                findMap.put(find.getName(), settingMap);
            }
            list.add(findMap);
        }
        map.put("list", list);
        return map;
    }

    @RequestMapping(value = "/addSetting", method = RequestMethod.POST)
    @ResponseBody
    public Map addSetting(String name, String value, String description, @RequestParam(value = "img", required = false) List<String> img, String type) {
        Setting setting = new Setting();
        setting.setName(name);
        setting.setValue(value);
        setting.setDescription(description);
        setting.setImg(img);
        setting.setType(type);

        settingService.addSetting(setting);
        Map<String, Object> map = new HashMap<>();
        map.put("state", State.SUCCESS.value());
        map.put("detail", State.SUCCESS.desc());
        return map;
    }

    @RequestMapping(value = "/deleteSetting", method = RequestMethod.POST)
    @ResponseBody
    public Map deleteSetting(Integer id) {
        settingService.deleteSetting(id);
        Map<String, Object> map = new HashMap<>();
        map.put("state", State.SUCCESS.value());
        map.put("detail", State.SUCCESS.desc());
        return map;
    }

    @RequestMapping("/editSetting")
    @ResponseBody
    public Map editSetting(Integer id, String name, String value, String description, @RequestParam(value = "img") List<String> img, String type) {
        Setting setting = new Setting();
        setting.setId(id);
        setting.setName(name);
        setting.setValue(value);
        setting.setDescription(description);
        setting.setImg(img);
        setting.setType(type);

        settingService.editSetting(setting);
        Map<String, Object> map = new HashMap<>();
        map.put("state", State.SUCCESS.value());
        map.put("detail", State.SUCCESS.desc());
        return map;
    }

}
