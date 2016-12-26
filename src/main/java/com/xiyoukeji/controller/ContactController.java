package com.xiyoukeji.controller;

import com.xiyoukeji.entity.Article;
import com.xiyoukeji.service.SettingService;
import com.xiyoukeji.entity.Setting;
import com.xiyoukeji.tools.State;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 8-联系我们
 *
 * Created by Matilda on 2016/12/14.
 */

@Controller
public class ContactController {

    @Resource
    SettingService settingService;

    @ExceptionHandler
    @ResponseBody
    public Map processException(RuntimeException e){
        Map<String, Object> map = new HashMap<>();
        map.put("state", State.EXCEPTION.value());
        map.put("detail", "Exception occurred");
        return map;
    }

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
        Map<String, List<Setting>> map = new HashMap<>();
        map.put("list", settingService.getFind());
        return map;
    }

    @RequestMapping(value = "/addSetting", method = RequestMethod.POST)
    @ResponseBody
    public Map addSetting(Setting setting) {
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
    public Map editSetting(Setting setting) {
        settingService.editSetting(setting);
        Map<String, Object> map = new HashMap<>();
        map.put("state", State.SUCCESS.value());
        map.put("detail", State.SUCCESS.desc());
        return map;
    }

}
