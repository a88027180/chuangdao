package com.xiyoukeji.controller;

import com.xiyoukeji.entity.HoldingCompany;
import com.xiyoukeji.service.HoldingCompanyService;
import com.xiyoukeji.tools.State;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matilda on 2016/12/27.
 */

@Controller
public class HoldingCompanyController {


    @Resource
    HoldingCompanyService holdingCompanyService;

    @RequestMapping(value = "/addHoldingCompany", method = RequestMethod.POST)
    @ResponseBody
    public Map addHoldingCompany(HoldingCompany hc) {
        holdingCompanyService.addCompany(hc);
        Map<String, Object> map = new HashMap<>();
        map.put("state", State.SUCCESS.value());
        map.put("detail", State.SUCCESS.desc());
        return map;
    }

    @RequestMapping(value = "/deleteHoldingCompany", method = RequestMethod.POST)
    @ResponseBody
    public Map deleteHoldingCompany(Integer id) {
        holdingCompanyService.deleteCompany(id);
        Map<String, Object> map = new HashMap<>();
        map.put("state", State.SUCCESS.value());
        map.put("detail", State.SUCCESS.desc());
        return map;
    }

    @RequestMapping(value = "/editHoldingCompany", method = RequestMethod.POST)
    @ResponseBody
    public Map editHoldingCompany(HoldingCompany hc) {
        holdingCompanyService.editCompany(hc);
        Map<String, Object> map = new HashMap<>();
        map.put("state", State.SUCCESS.value());
        map.put("detail", State.SUCCESS.desc());
        return map;
    }

}
