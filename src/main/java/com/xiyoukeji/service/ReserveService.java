package com.xiyoukeji.service;

import com.xiyoukeji.entity.Article;
import com.xiyoukeji.entity.Reserve;
import com.xiyoukeji.entity.User;
import com.xiyoukeji.tools.BaseDaoImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Matilda on 2017/1/7.
 */

@Service
public class ReserveService {

    @Resource
    BaseDaoImpl<Reserve> reserveBaseDao;
    @Resource
    BaseDaoImpl<User> userBaseDao;
    @Resource
    BaseDaoImpl<Article> articleBaseDao;

    public void reserve(Integer userId, Integer productId) {
        Reserve reserve = new Reserve();
        reserve.setUserId(userId);
        reserve.setProductId(productId);
        reserve.setTime(new Date());
        reserveBaseDao.save(reserve);
    }

    public Reserve getReserve(Integer userId, Integer productId) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("productId", productId);
        return reserveBaseDao.get("from Reserve r where r.userId = :userId and r.productId = :productId", map);
    }

    public List<Map<String, Object>> getReserveList() {
        List<Map<String, Object>> list =  new ArrayList<>();
        List<Reserve> reserves = reserveBaseDao.find("from Reserve");

        if(reserves.size() != 0) {
            for (Reserve r: reserves) {
                Map<String, Object> rMap = new HashMap<>();
                rMap.put("id", r.getId());
                User user = userBaseDao.get(User.class, r.getUserId());
                Article article = articleBaseDao.get(Article.class, r.getProductId());
                rMap.put("userName", user.getName());
                rMap.put("productName", article.getTitle());
                rMap.put("questionnaire", user.getQuestionnaire());
                rMap.put("time", r.getTime());
                list.add(rMap);
            }

            // 越早预约越靠前
            Collections.sort(list, (o1, o2) -> {
                return o1.get("time").toString().compareTo(o2.get("time").toString());
            });

        }
        return list;
    }


}
