package com.xiyoukeji.controller;

import com.xiyoukeji.entity.Partner;
import com.xiyoukeji.service.PartnerService;
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
public class PartnerController {

    @Resource
    PartnerService partnerService;

    @RequestMapping(value = "/addPartner", method = RequestMethod.POST)
    @ResponseBody
    public Map addPartner(Partner partner) {
        partnerService.addPartner(partner);
        Map<String, Object> map = new HashMap<>();
        map.put("state", State.SUCCESS.value());
        map.put("detail", State.SUCCESS.desc());
        return map;
    }

    @RequestMapping(value = "/deletePartner", method = RequestMethod.POST)
    @ResponseBody
    public Map deletePartner(Integer id) {
        partnerService.deletePartner(id);
        Map<String, Object> map = new HashMap<>();
        map.put("state", State.SUCCESS.value());
        map.put("detail", State.SUCCESS.desc());
        return map;
    }

    @RequestMapping(value = "/editPartner", method = RequestMethod.POST)
    @ResponseBody
    public Map editPartner(Partner partner) {
        partnerService.editPartner(partner);
        Map<String, Object> map = new HashMap<>();
        map.put("state", State.SUCCESS.value());
        map.put("detail", State.SUCCESS.desc());
        return map;
    }

}
