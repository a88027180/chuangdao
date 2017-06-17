package com.xiyoukeji.service;

import com.xiyoukeji.entity.User;
import com.xiyoukeji.exception.Assert;
import com.xiyoukeji.exception.ErrCodeException;
import com.xiyoukeji.tools.BaseDao;
import com.xiyoukeji.tools.MapTool;
import com.xiyoukeji.tools.State;
import com.xiyoukeji.tools.UserType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Resource
    VerifyService verifyService;
    @Resource
    HttpSession session;
    @Resource
    BaseDao baseDao;

    @Transactional
    public void resetPassword(String phone, String code, String password) {
        Assert.notBlank(phone, code, password);
        User user = userBaseDao.get("from User as u where u.phone=:phone", MapTool.Map().put("phone", phone));
        if (user == null) throw new ErrCodeException("账号不存在");
        verifyService.verify(phone, code);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
    }

    @Transactional
    public void register(String phone, String code, String password, Integer type) {
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        if (userBaseDao.get("from User as u where u.phone=:phone", map) != null)
            throw new ErrCodeException("手机号已经存在");
        verifyService.verify(phone, code);
        User user = new User();
        user.setPhone(phone);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        if (type == null) type = 1;
        user.setType(type);
        userBaseDao.save(user);
        session.setAttribute("userId", user.getId());
        session.setAttribute("name", user.getPhone());
        session.setAttribute("type", user.getType());
    }

    @Transactional
    public void risk(String risk) {
        User user = get();
        if (user.getRisk_times() < 10) {
            user.setRisk_times(user.getRisk_times() + 1);
            user.setRisk(risk);
        } else if (System.currentTimeMillis() - user.getRisk_time() > 31536000000L) {
            user.setRisk_times(1);
            user.setRisk_time(System.currentTimeMillis());
            user.setRisk(risk);
        } else
            throw new ErrCodeException("超过测评次数");
    }

    public Map getRisk() {
        Map<String, Long> map = new HashMap<>();
        List<Object[]> list = baseDao.find("select u.risk,count(*) from User as u where u.risk is not null group by u.risk");
        for (Object[] objects : list) {
            map.put(objects[0].toString(), (Long) objects[1]);
        }
        return map;
    }

    public void userLogin(String phone, String password) {
        login(phone, password, UserType.NORMAL.ordinal(), session);
    }

    public void adminLogin(String phone, String password) {
        login(phone, password, UserType.ADMIN.ordinal(), session);
    }

    public void login(String phone, String password, int checkType, HttpSession session) {
        Assert.notBlank(password, password);

        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        User user = userBaseDao.get("from User u where u.phone = :phone", map);
        if (!new BCryptPasswordEncoder().matches(password, user.getPassword()))
            throw new ErrCodeException("密码错误");

        int userType = user.getType();
        if (checkType != UserType.NORMAL.ordinal() && userType == UserType.NORMAL.ordinal()) { // 用户不能登录后台‘
            throw new ErrCodeException("无权限");
        } else {
            session.setAttribute("userId", user.getId());
            session.setAttribute("name", user.getPhone());
            session.setAttribute("type", user.getType());
        }
    }

    public User get() {
        Integer id = (Integer) session.getAttribute("userId");
        if (id == null) throw new ErrCodeException("未登录");
        return userBaseDao.get(User.class, id);
    }

    public boolean isSubmitted(HttpSession session) {
        User user = get();
        return user.getQuestionnaire() != null;
    }

    public Map<String, Object> isLogin(HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        if (session.getAttribute("name") == null) {
            map.put("state", State.FAIL.value());
            map.put("detail", "用户未登录");
        } else {
            map.put("state", State.SUCCESS.value());
            map.put("name", session.getAttribute("name"));
            map.put("type", session.getAttribute("type"));
        }
        return map;
    }

    public State submitQuestionnaire(String questionnaire, HttpSession session) {
        User user = get();

        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String date = formatter.format(currentTime);

        String userDate = user.getDate();
        // 当天第一次提交
        if (userDate == null || !userDate.equals(date)) {
            user.setDate(date);
            user.setQuestionnaire(questionnaire);
            user.setTimes(1);
            userBaseDao.update(user);
            return State.SUCCESS;
        }

        // 多次提交
        if (user.getTimes() >= 3)
            return State.SUBMIT_EXCEED;

        user.setTimes(user.getTimes() + 1);
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

    public State editPassword(String oPassword, String nPassword, HttpSession session) {
        User user = get();
        if (!new BCryptPasswordEncoder().matches(oPassword, user.getPassword()))
            return State.PASSWORD_ERROR;
        user.setPassword(new BCryptPasswordEncoder().encode(nPassword));
        userBaseDao.update(user);
        return State.SUCCESS;
    }
}
