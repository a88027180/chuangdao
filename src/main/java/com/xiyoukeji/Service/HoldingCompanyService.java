package com.xiyoukeji.service;

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

    public HoldingCompany getCompanyById(Integer id) {
        return holdingCompanyBaseDao.get(HoldingCompany.class, id);
    }

    public void addCompany(HoldingCompany company) {
        holdingCompanyBaseDao.save(company);
    }

    public void deleteCompany(Integer id) {
        HoldingCompany company = getCompanyById(id);
        holdingCompanyBaseDao.delete(company);
    }

    public void editCompany(HoldingCompany company) {
        holdingCompanyBaseDao.update(company);
    }

}
