package com.xiyoukeji.Service;

import com.xiyoukeji.entity.HoldingCompany;
import com.xiyoukeji.tools.BaseDaoImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Matilda on 2016/12/13.
 */

@Service
public class HoldingCompanyService {

    @Resource
    BaseDaoImpl<HoldingCompany> holdingCompanyBaseDao;

    public List<HoldingCompany> getCompanyList() {
        return holdingCompanyBaseDao.find("from HoldingCompany");
    }

}
