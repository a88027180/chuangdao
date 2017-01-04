package com.xiyoukeji.controller;

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

    @ExceptionHandler
    @ResponseBody
    public Map processException(RuntimeException e){
        Map<String, Object> map = new HashMap<>();
        map.put("state", State.EXCEPTION.value());
        map.put("detail", "Exception occurred");
        return map;
    }

    @RequestMapping("/getRecruitmentList")
    @ResponseBody
    public Map getRecruitmentInfo() {
        Map<String, List<Recruitment>> map = new HashMap<>();
        map.put("list", recruitmentService.getRecruitmentList());
        return map;
    }

    @RequestMapping(value = "/addRecruitment", method = RequestMethod.POST)
    @ResponseBody
    public Map addRecruitment(String position, @RequestParam(value = "duty") List<String> duty, @RequestParam(value = "requirement") List<String> requirement,
                              @RequestParam(value = "place") List<String> place, String email ) {
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

    @RequestMapping(value = "/deleteRecruitment", method = RequestMethod.POST)
    @ResponseBody
    public Map deleteRecruitment(Integer id) {
        recruitmentService.deleteRecruitment(id);
        Map<String, Object> map = new HashMap<>();
        map.put("state", State.SUCCESS.value());
        map.put("detail", State.SUCCESS.name());
        return map;
    }

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
