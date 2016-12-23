package com.xiyoukeji.controller;

import com.xiyoukeji.service.*;
import com.xiyoukeji.entity.*;
import com.xiyoukeji.tools.ArticleType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 2-关于创道
 *
 * Created by Matilda on 2016/12/12.
 */

@Controller
public class AboutController {

    @Resource
    SettingService settingService;
    @Resource
    ManagerService managerService;
    @Resource
    HoldingCompanyService holdingCompanyService;
    @Resource
    PartnerService partnerService;
    @Resource
    HonorService honorService;
    @Resource
    ArticleService articleService;

    @RequestMapping("/getIntroduction")
    @ResponseBody
    public Map getCompanyIntroduction() {
        Map<String, Object> map =  new HashMap<>();
        map.put("info", settingService.getSettingByName("introduction"));
        return map;
    }

    @RequestMapping("/getManagerTeamList")
    @ResponseBody
    public Map getManagementTeamList() {
        Map<String, List<Manager>> map = new HashMap<>();
        map.put("list", managerService.getManagerTeamList());
        return map;
    }

    @RequestMapping(value = "/getDevelopmentList")
    @ResponseBody
    public Map getDevelopmentList() {
        Map<String, Object> map = new HashMap<>();
        map.put("list", articleService.getArticleDisplayList(0, ArticleType.DEVELOPMENT.name()));
        return map;
    }

    @RequestMapping(value = "/getDevelopment", method = RequestMethod.POST)
    @ResponseBody
    public Article getDevelopmentById(Integer id) {
        if(id == null) return null;
        return articleService.getArticleById(id);
    }

    @RequestMapping("/getHoldingCompanyList")
    @ResponseBody
    public Map getHoldingCompanyList() {
        Map<String, List<HoldingCompany>> map = new HashMap<>();
        map.put("list", holdingCompanyService.getCompanyList());
        return map;
    }

    @RequestMapping("/getHonorList")
    @ResponseBody
    public Map getHonorList() {
        Map<String, List<Honor>> map = new HashMap<>();
        map.put("list", honorService.getHonorList());
        return map;
    }

    @RequestMapping("/getPartnerList")
    @ResponseBody
    public Map getPartnerList() {
        Map<String, List<Partner>> map = new HashMap<>();
        map.put("list", partnerService.getPartnerList());
        return map;
    }

}
