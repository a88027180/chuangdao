package com.xiyoukeji.service;

import com.xiyoukeji.entity.Recruitment;
import com.xiyoukeji.tools.BaseDaoImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Matilda on 2016/12/14.
 */

@Service
public class RecruitmentService {

    @Resource
    BaseDaoImpl<Recruitment> recruitmentBaseDao;

    public Recruitment getRecruitmentById(Integer id) {
        return recruitmentBaseDao.get(Recruitment.class, id);
    }

    public List<Recruitment> getRecruitmentList() {
        return recruitmentBaseDao.find("from Recruitment");
    }

    public void addRecruitment(Recruitment recruitment) {
        recruitmentBaseDao.save(recruitment);
    }

    public void deleteRecruitment(Integer id) {
        Recruitment recruitment = getRecruitmentById(id);
        recruitmentBaseDao.delete(recruitment);
    }

    public void editRecruitment(Recruitment recruitment) {
        recruitmentBaseDao.update(recruitment);
    }

    public void addAttr(Integer id, String name, String value) {
        Recruitment recruitment = getRecruitmentById(id);
        recruitment.getAttr().put(name, value);
        recruitmentBaseDao.update(recruitment);
    }
}
