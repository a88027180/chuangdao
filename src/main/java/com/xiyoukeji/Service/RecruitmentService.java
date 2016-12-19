package com.xiyoukeji.Service;

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

    public List<Recruitment> getRecruitmentInfo() {
        return recruitmentBaseDao.find("from Recruitment");
    }
}
