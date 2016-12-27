package com.xiyoukeji.service;

import com.xiyoukeji.entity.Partner;
import com.xiyoukeji.tools.BaseDaoImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Matilda on 2016/12/13.
 */

@Service
public class PartnerService {

    @Resource
    BaseDaoImpl<Partner> partnerBaseDao;

    public List<Partner> getPartnerList () {
        return partnerBaseDao.find("from Partner");
    }

    public Partner getPartnerById(Integer id) {
        return partnerBaseDao.get(Partner.class, id);
    }

    public void addPartner(Partner partner) {
        partnerBaseDao.save(partner);
    }

    public void deletePartner(Integer id) {
        Partner partner = getPartnerById(id);
        partnerBaseDao.delete(partner);
    }

    public void editPartner(Partner partner) {
        partnerBaseDao.update(partner);
    }
}
