package com.xiyoukeji.controller;

import com.xiyoukeji.Service.RecruitmentService;
import com.xiyoukeji.entity.Recruitment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping("/getRecruitmentList")
    @ResponseBody
    public Map getRecruitmentInfo() {
        Map<String, List<Recruitment>> map = new HashMap<>();
        map.put("list", recruitmentService.getRecruitmentInfo());
        return map;
    }
}
