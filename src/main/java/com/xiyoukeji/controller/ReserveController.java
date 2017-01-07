package com.xiyoukeji.controller;

import com.xiyoukeji.service.ReserveService;
import com.xiyoukeji.tools.State;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matilda on 2017/1/7.
 */

@Controller
public class ReserveController {

    @Resource
    ReserveService reserveService;

    @RequestMapping(value = "/reserve", method = RequestMethod.POST)
    @ResponseBody
    public Map reserve(Integer productId, HttpSession session) {
        // 前端保证登录
        Integer userId = (Integer) session.getAttribute("userId");
        Map<String, Object> map = new HashMap<>();
        if(reserveService.getReserve(userId, productId)!=null) {
            map.put("state", State.FAIL.value());
            map.put("detail", "您已预约过该产品");
            return map;
        }
        reserveService.reserve(userId, productId);

        map.put("state", State.SUCCESS.value());
        map.put("detail", State.SUCCESS.desc());
        return map;
    }

    @RequestMapping("/getReserveList")
    @ResponseBody
    public Map getReserveList() {
        Map<String, Object> map = new HashMap<>();
        map.put("list", reserveService.getReserveList());
        return map;
    }


}
