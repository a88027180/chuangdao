package com.xiyoukeji.service;

import com.xiyoukeji.entity.Manager;
import com.xiyoukeji.tools.BaseDaoImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Matilda on 2016/12/12.
 */

@Service
public class ManagerService {

    @Resource
    BaseDaoImpl<Manager> managerBaseDao;

    public Manager getManagerById(Integer id) {
        return managerBaseDao.get(Manager.class, id);
    }

    public List<Manager> getManagerTeamList() {
        return managerBaseDao.find("from Manager");
    }

    public void addManager(Manager manager) {
        managerBaseDao.save(manager);
    }

    public void deleteManager(Integer id) {
        Manager manager = getManagerById(id);
        managerBaseDao.delete(manager);
    }

    public void editManager(Manager manager) {
        managerBaseDao.update(manager);
    }
}
