package com.xiyoukeji.service;

import com.xiyoukeji.entity.Honor;
import com.xiyoukeji.tools.BaseDaoImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 荣誉资质
 *
 * Created by Matilda on 2016/12/13.
 */

@Service
public class HonorService {

    @Resource
    BaseDaoImpl<Honor> honorBaseDao;
    public List<Honor> getHonorList() {
        return honorBaseDao.find("from Honor");
    }

}
