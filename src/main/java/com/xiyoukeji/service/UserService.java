package com.xiyoukeji.service;

import com.xiyoukeji.entity.User;
import com.xiyoukeji.tools.BaseDao;
import com.xiyoukeji.tools.State;
import com.xiyoukeji.tools.UserType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    public State userLogin(User user, HttpSession session) {
        return login(user, UserType.NORMAL.ordinal(), session);
    }

    public State adminLogin(User user, HttpSession session) {
        return login(user, UserType.ADMIN.ordinal(), session);
    }

    public State login(User user, int checkType, HttpSession session) {
        String name = user.getName();
        String password = user.getPassword();
        if( name == null || password == null)
            return State.FAIL;

        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        User checkUser = userBaseDao.get("from User u where u.name = :name", map);
        if(checkUser == null) {
            return State.USER_NOT_EXIST;
        }
        if(!checkUser.getPassword().equals(user.getPassword())) {
            return State.PASSWORD_ERROR;
        }

        int userType = checkUser.getType();
        if(checkType == UserType.ADMIN.ordinal() && userType == UserType.NORMAL.ordinal()) { // 用户不能登录后台‘
            return State.NO_PERMISSION;
        } else {
            session.setAttribute("name", user.getName());
            session.setAttribute("password", user.getPassword());
            return State.SUCCESS;
        }
    }

    public boolean isSubmitted(HttpSession session) {
        String name = (String)session.getAttribute("name");
        if( name == null)
            return false;

        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        User user = userBaseDao.get("from User u where u.name = :name", map);

        return user.getQuestionnaire()!=null;
    }

    public String isLogin(HttpSession session) {
        return (String)session.getAttribute("name");
    }

    public State submitQuestionnaire(String questionnaire, HttpSession session) {
        String name = (String)session.getAttribute("name");
        if( name == null)
            return State.LOGIN_EXPIRE;

        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        User user = userBaseDao.get("from User u where u.name = :name", map);
        if(user == null)
            return State.USER_NOT_EXIST;

        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String date = formatter.format(currentTime);

        String userDate = user.getDate();
        // 当天第一次提交
        if(userDate == null || !userDate.equals(date)) {
            user.setDate(date);
            user.setQuestionnaire(questionnaire);
            user.setTimes(1);
            userBaseDao.update(user);
            return State.SUCCESS;
        }

        // 多次提交
        if(user.getTimes()>=3)
            return State.SUBMIT_EXCEED;

        user.setTimes(user.getTimes()+1);
        user.setQuestionnaire(questionnaire);
        userBaseDao.update(user);

        return State.SUCCESS;
    }

    public List<User> getUserList() {
        return userBaseDao.find("from User");
    }

    public void deleteUser(Integer id) {
        User user = userBaseDao.get(User.class, id);
        userBaseDao.delete(user);
    }

}
