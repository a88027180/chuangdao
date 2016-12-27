package com.xiyoukeji.service;

import com.xiyoukeji.entity.User;
import com.xiyoukeji.tools.BaseDao;
import com.xiyoukeji.tools.State;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Matilda on 2016/12/26.
 */

@Service
public class UserService {

    @Resource
    BaseDao<User> userBaseDao;

    public State register(User user) {
        if(user.getName() == null || user.getPassword() == null)
            return State.FAIL;
        Map<String, Object> map = new HashMap<>();
        map.put("name", user.getName());
        User checkUser = userBaseDao.get("from User u where u.name = :name", map);
        if(checkUser!=null)
            return State.USERNAME_USED;
        userBaseDao.save(user);
        return State.SUCCESS;
    }

    public State login(User user, HttpSession session) {
        if(user.getName() == null || user.getPassword() == null)
            return State.FAIL;
        Map<String, Object> map = new HashMap<>();
        map.put("name", user.getName());
        User checkUser = userBaseDao.get("from User u where u.name = :name", map);
        if(checkUser==null) {
            return State.USER_NOT_EXIST;
        } else if(!checkUser.getPassword().equals(user.getPassword())) {
            return State.PASSWORD_ERROR;
        } else {
            session.setAttribute("name", user.getName());
            session.setAttribute("password", user.getPassword());
            return State.SUCCESS;
        }

    }

    public List<User> getUserList() {
        return userBaseDao.find("from User");
    }

}
