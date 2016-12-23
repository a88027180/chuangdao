package com.xiyoukeji.controller;

import com.xiyoukeji.entity.Article;
import com.xiyoukeji.service.RecruitmentService;
import com.xiyoukeji.entity.Recruitment;
import com.xiyoukeji.tools.State;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
        map.put("state", State.FAIL.value());
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
    public Map addArticle(Recruitment recruitment) {
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
    public Map editRecruitment(Recruitment recruitment) {
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
