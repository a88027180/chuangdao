package com.xiyoukeji.controller;

import com.xiyoukeji.auth.EditAuthority;
import com.xiyoukeji.entity.Article;
import com.xiyoukeji.service.RecruitmentService;
import com.xiyoukeji.entity.Recruitment;
import com.xiyoukeji.tools.State;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 6-招聘中心
 *
 * Created by Matilda on 2016/12/14.
 */

@Controller
public class RecruitController {

    @Resource
    RecruitmentService recruitmentService;

    @RequestMapping("/getRecruitmentList")
    @ResponseBody
    public Map getRecruitmentInfo() {
        Map<String, List<Recruitment>> map = new HashMap<>();
        map.put("list", recruitmentService.getRecruitmentList());
        return map;
    }

    @EditAuthority("02")
    @RequestMapping(value = "/addRecruitment", method = RequestMethod.POST)
    @ResponseBody
    public Map addRecruitment(String position, @RequestParam(value = "duty") List<String> duty, @RequestParam(value = "requirement") List<String> requirement,
                              @RequestParam(value = "place") List<String> place, String email ) {   // 写成List<String>接收会自动按逗号分隔，String[]也是
        Recruitment recruitment = new Recruitment();
        recruitment.setPosition(position);
        recruitment.setDuty(duty);
        recruitment.setRequirement(requirement);
        recruitment.setPlace(place);
        recruitment.setEmail(email);

        recruitmentService.addRecruitment(recruitment);
        Map<String, Object> map = new HashMap<>();
        map.put("state", State.SUCCESS.value());
        map.put("detail", State.SUCCESS.name());
        return map;
    }

    @EditAuthority("02")
    @RequestMapping(value = "/deleteRecruitment", method = RequestMethod.POST)
    @ResponseBody
    public Map deleteRecruitment(Integer id) {
        recruitmentService.deleteRecruitment(id);
        Map<String, Object> map = new HashMap<>();
        map.put("state", State.SUCCESS.value());
        map.put("detail", State.SUCCESS.name());
        return map;
    }

    @EditAuthority("02")
    @RequestMapping(value = "/editRecruitment", method = RequestMethod.POST)
    @ResponseBody
    public Map editRecruitment(Integer id, String position, @RequestParam(value = "duty") List<String> duty, @RequestParam(value = "requirement") List<String> requirement,
                               @RequestParam(value = "place") List<String> place, String email) {
        Recruitment recruitment = new Recruitment();
        recruitment.setId(id);
        recruitment.setPosition(position);
        recruitment.setDuty(duty);
        recruitment.setRequirement(requirement);
        recruitment.setPlace(place);
        recruitment.setEmail(email);

        recruitmentService.editRecruitment(recruitment);
        Map<String, Object> map = new HashMap<>();
        map.put("state", State.SUCCESS.value());
        map.put("detail", State.SUCCESS.name());
        return map;
    }

    @EditAuthority("02")
    @RequestMapping(value = "/addRecruitmentAttr", method = RequestMethod.POST)
    @ResponseBody
    public Map addRecruitmentAttr(Integer id, String name, String value) {
        recruitmentService.addAttr(id, name, value);
        Map<String, Object> map = new HashMap<>();
        map.put("state", State.SUCCESS.value());
        map.put("detail", State.SUCCESS.name());
        return map;
    }
}
