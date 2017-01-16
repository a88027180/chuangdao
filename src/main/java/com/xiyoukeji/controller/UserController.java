package com.xiyoukeji.controller;

import com.xiyoukeji.auth.EditAuthority;
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
        if(s == State.SUCCESS) {
            map.put("state", s.value());
            map.put("type", session.getAttribute("type"));
        } else {
            map.put("state", s.value());
            map.put("detail", s.desc());
        }
        return map;
    }

    @RequestMapping("/isLogin")
    @ResponseBody
    public Map isLogin(HttpSession session) {
        return userService.isLogin(session);
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

    @EditAuthority("0")
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

    @RequestMapping(value = "/editPassword", method = RequestMethod.POST)
    @ResponseBody
    public Map editPassword(String oPassword, String nPassword, HttpSession session) {
        State s = userService.editPassword(oPassword, nPassword, session);
        Map<String, Object> map = new HashMap<>();
        map.put("state", s.value());
        map.put("detail", s.desc());
        return map;
    }

}
