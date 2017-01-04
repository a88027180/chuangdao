package com.xiyoukeji.controller;

import com.xiyoukeji.entity.Manager;
import com.xiyoukeji.service.ManagerService;
import com.xiyoukeji.tools.State;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Matilda on 2016/12/27.
 */

@Controller
public class ManagerController {

    @Resource
    ManagerService managerService;

    @ExceptionHandler
    @ResponseBody
    public Map processException(RuntimeException e){
        Map<String, Object> map = new HashMap<>();
        map.put("state", State.EXCEPTION.value());
        map.put("detail", State.EXCEPTION.desc());
        return map;
    }

    @RequestMapping(value = "/addManager", method = RequestMethod.POST)
    @ResponseBody
    public Map addManager(String name, String spell, String appellation, String main_title,
                          @RequestParam(value = "sub_title", required = false) List<String> sub_title,
                          String description, String img) {
        Manager manager = new Manager();
        manager.setName(name);
        manager.setSpell(spell);
        manager.setAppellation(appellation);
        manager.setMain_title(main_title);
        manager.setSub_title(sub_title);
        manager.setDescription(description);
        manager.setImg(img);

        managerService.addManager(manager);
        Map<String, Object> map = new HashMap<>();
        map.put("state", State.SUCCESS.value());
        map.put("detail", State.SUCCESS.desc());
        return map;
    }

    @RequestMapping(value = "/deleteManager", method = RequestMethod.POST)
    @ResponseBody
    public Map deleteManager(Integer id) {
        managerService.deleteManager(id);
        Map<String, Object> map = new HashMap<>();
        map.put("state", State.SUCCESS.value());
        map.put("detail", State.SUCCESS.desc());
        return map;
    }

    @RequestMapping(value = "/editManager", method = RequestMethod.POST)
    @ResponseBody
    public Map editManager(Integer id, String name, String spell, String appellation, String main_title,
                           @RequestParam(value = "sub_title") List<String> sub_title,
                           String description, String img) {
        Manager manager = new Manager();
        manager.setId(id);
        manager.setName(name);
        manager.setSpell(spell);
        manager.setAppellation(appellation);
        manager.setMain_title(main_title);
        manager.setSub_title(sub_title);
        manager.setDescription(description);
        manager.setImg(img);

        managerService.editManager(manager);
        Map<String, Object> map = new HashMap<>();
        map.put("state", State.SUCCESS.value());
        map.put("detail", State.SUCCESS.desc());
        return map;
    }

}
