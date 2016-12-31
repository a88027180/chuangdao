package com.xiyoukeji.controller;

import com.xiyoukeji.entity.User;
import com.xiyoukeji.service.UserService;
import com.xiyoukeji.tools.State;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
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
        State s = userService.register(user);
        Map<String, Object> map = new HashMap<>();
        map.put("state", s.value());
        map.put("detail", s.desc());
        return map;
    }

    @RequestMapping(value = "/userLogin", method = RequestMethod.POST)
    @ResponseBody
    public Map userLogin(User user, HttpSession session) {
        State s = userService.userLogin(user, session);
        Map<String, Object> map = new HashMap<>();
        map.put("state", s.value());
        map.put("detail", s.desc());
        return map;
    }

    @RequestMapping(value = "/adminLogin", method = RequestMethod.POST)
    @ResponseBody
    public Map adminLogin(User user, HttpSession session) {
        State s = userService.adminLogin(user, session);
        Map<String, Object> map = new HashMap<>();
        map.put("state", s.value());
        map.put("detail", s.desc());
        return map;
    }

    @RequestMapping("/isLogin")
    @ResponseBody
    public boolean isLogin(HttpSession session) {
        return userService.isLogin(session);
    }

    @RequestMapping("/isSubmitted")
    @ResponseBody
    public boolean isSubmitted(HttpSession session) {
        return userService.isSubmitted(session);
    }


    @RequestMapping("/submitQuestionnaire")
    @ResponseBody
    public Map submitQuestionnaire(String questionnaire, HttpSession session) {
        State s = userService.submitQuestionnaire(questionnaire, session);
        Map<String, Object> map = new HashMap<>();
        map.put("state", s.value());
        map.put("detail", s.desc());
        return map;
    }

    @RequestMapping("/getUserList")
    @ResponseBody
    public Map getUserList() {
        Map<String, List<User>> map = new HashMap<>();
        map.put("list", userService.getUserList());
        return map;
    }

}
