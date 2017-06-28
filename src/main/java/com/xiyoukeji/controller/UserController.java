package com.xiyoukeji.controller;

import com.xiyoukeji.auth.EditAuthority;
import com.xiyoukeji.entity.User;
import com.xiyoukeji.exception.Assert;
import com.xiyoukeji.exception.ErrCodeException;
import com.xiyoukeji.service.ReserveService;
import com.xiyoukeji.service.UserService;
import com.xiyoukeji.service.VerifyService;
import com.xiyoukeji.tools.MapTool;
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
    @Resource
    VerifyService verifyService;
    @Resource
    HttpSession session;

    @ExceptionHandler
    @ResponseBody
    public Map end(RuntimeException e) {
        if (!(e instanceof ErrCodeException))
            e = new ErrCodeException();
        return MapTool.Map().put("state", ((ErrCodeException) e).getErrcode()).put("msg", e.getMessage());
    }

    @RequestMapping("/phoneCode")
    @ResponseBody
    public Map phoneCode(String phone) {
        verifyService.sendPhoneCode(phone);
        return MapTool.Mapok();
    }

    @RequestMapping("/risk")
    @ResponseBody
    public Map risk(String risk) {
        userService.risk(risk);
        return MapTool.Mapok();
    }

    @RequestMapping("/riskStat")
    @ResponseBody
    public Map riskStat() {
        Map risk = userService.getRisk();
        return MapTool.Mapok().put("data", risk);
    }

    @RequestMapping("/myRisk")
    @ResponseBody
    public Map myRisk() {
        return MapTool.Mapok().put("data", userService.getMyRisk());
    }


    @RequestMapping("/resetPassword")
    @ResponseBody
    public Map resetPassword(String phone, String code, String password) {
        userService.resetPassword(phone, code, password);
        return MapTool.Mapok();
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Map register(String phone, String code, String password, Integer type) {
        Assert.notBlank(phone, code, password);
        userService.register(phone, code, password, type);
        return MapTool.Mapok();
    }

    @RequestMapping(value = "/userLogin", method = RequestMethod.POST)
    @ResponseBody
    public Map userLogin(String phone, String password) {
        userService.userLogin(phone, password);
        return MapTool.Mapok();
    }

    @RequestMapping(value = "/adminLogin", method = RequestMethod.POST)
    @ResponseBody
    public Map adminLogin(String phone, String password) {
        userService.adminLogin(phone, password);
        return MapTool.Mapok().put("type", session.getAttribute("type"));
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
