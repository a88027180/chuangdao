package com.xiyoukeji.controller;

import com.xiyoukeji.auth.EditAuthority;
import com.xiyoukeji.entity.Honor;
import com.xiyoukeji.service.HonorService;
import com.xiyoukeji.tools.State;
import org.springframework.stereotype.Controller;
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
public class HonorController {

    @Resource
    HonorService honorService;

    @EditAuthority("0")
    @RequestMapping(value = "/addHonor", method = RequestMethod.POST)
    @ResponseBody
    public Map addHonor(Honor honor) {
        honorService.addHonor(honor);
        Map<String, Object> map = new HashMap<>();
        map.put("state", State.SUCCESS.value());
        map.put("detail", State.SUCCESS.desc());
        return map;
    }

    @EditAuthority("0")
    @RequestMapping(value = "/deleteHonor", method = RequestMethod.POST)
    @ResponseBody
    public Map deleteHonor(Integer id) {
        honorService.deleteHonor(id);
        Map<String, Object> map = new HashMap<>();
        map.put("state", State.SUCCESS.value());
        map.put("detail", State.SUCCESS.desc());
        return map;
    }

    @EditAuthority("0")
    @RequestMapping(value = "/editHonor", method = RequestMethod.POST)
    @ResponseBody
    public Map editHonor(Honor honor) {
        honorService.editHonor(honor);
        Map<String, Object> map = new HashMap<>();
        map.put("state", State.SUCCESS.value());
        map.put("detail", State.SUCCESS.desc());
        return map;
    }

}
