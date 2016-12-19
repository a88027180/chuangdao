package com.xiyoukeji.controller;

import com.xiyoukeji.Service.SettingService;
import com.xiyoukeji.entity.Setting;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping("/getAllContact")
    @ResponseBody
    public Map getContactInfo() {
        Map<String, List<Setting>> map = new HashMap<>();
        map.put("list", settingService.getContactInfo());
        return map;
    }

}
