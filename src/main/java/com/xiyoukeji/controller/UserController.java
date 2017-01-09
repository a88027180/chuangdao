package com.xiyoukeji.controller;

import com.xiyoukeji.entity.User;
import com.xiyoukeji.service.ReserveService;
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
	@Resource
    ReserveService reserveService;


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Map register(User user) {
        if(user.getPhone()==null)
            user.setPhone("");
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
    public Map isLogin(HttpSession session) {
        String name = userService.isLogin(session);
        Map<String, Object> map = new HashMap<>();
        if(name == null) {
            map.put("state", State.FAIL.value());
            map.put("detail", "用户未登录");
            return map;
        }

        map.put("state", State.SUCCESS.value());
        map.put("detail", name);
        return map;
    }

    @RequestMapping("/logout")
    @ResponseBody
    public Map logout(HttpSession session) {
        session.invalidate();
        Map<String, Object> map = new HashMap<>();
        map.put("state", State.SUCCESS.value());
        map.put("detail", State.SUCCESS.desc());
        return map;
    }

    @RequestMapping("/isSubmitted")
    @ResponseBody
    public boolean isSubmitted(HttpSession session) {
        return userService.isSubmitted(session);
    }


    @RequestMapping(value = "/submitQuestionnaire", method = RequestMethod.POST)
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

    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    @ResponseBody
    public Map deleteUser(Integer id) {
        userService.deleteUser(id);
		reserveService.deleteUser(id);
        Map<String, Object> map = new HashMap<>();
        map.put("state", State.SUCCESS.value());
        map.put("detail", State.SUCCESS.desc());
        return map;
    }


}
