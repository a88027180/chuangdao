package com.xiyoukeji.controller;

import com.xiyoukeji.entity.Manager;
import com.xiyoukeji.service.ManagerService;
import com.xiyoukeji.tools.State;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
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
    public Map addManager(Manager manager) {
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
    public Map editManager(Manager manager) {
        managerService.editManager(manager);
        Map<String, Object> map = new HashMap<>();
        map.put("state", State.SUCCESS.value());
        map.put("detail", State.SUCCESS.desc());
        return map;
    }

}
