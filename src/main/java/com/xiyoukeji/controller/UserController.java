package com.xiyoukeji.controller;

import com.xiyoukeji.entity.User;
import com.xiyoukeji.service.UserService;
import com.xiyoukeji.tools.State;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matilda on 2016/12/26.
 */

@Controller
public class UserController {

    @Resource
    UserService userService;

    @ExceptionHandler
    @ResponseBody
    public Map processException(RuntimeException e){
        Map<String, Object> map = new HashMap<>();
        map.put("state", State.EXCEPTION.value());
        map.put("detail", State.EXCEPTION.desc());
        return map;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Map register(User user) {
        userService.register(user);
        Map<String, Object> map = new HashMap<>();
        map.put("state", State.SUCCESS.value());
        map.put("detail", State.SUCCESS.desc());
        return map;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Map login(User user, HttpSession session) {
        State s = userService.login(user, session);
        Map<String, Object> map = new HashMap<>();
        map.put("state", s.value());
        map.put("detail", s.desc());
        return map;
    }
}
