package com.xiyoukeji.Service;

import com.xiyoukeji.entity.Carousel;
import com.xiyoukeji.tools.BaseDaoImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * Created by Matilda on 2016/12/19.
 */

@Service
public class CarouselService {

    @Resource
    BaseDaoImpl<Carousel> carouselBaseDao;

    public List<Carousel> getCarouselList() {
        List<Carousel> carousels = carouselBaseDao.find("from Carousel");
        Collections.sort(carousels);    // 序号小的在前
        return carousels;
    }

}
