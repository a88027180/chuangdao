package com.xiyoukeji.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 轮播图
 *
 * Created by Matilda on 2016/12/19.
 */

@Entity
@Table(name = "carousel")
public class Carousel implements Comparable<Carousel>{

    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Integer id;
    private String img;
    private String url;
    @Column(name = "`order`")
    private Integer order; //顺序

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public int compareTo(Carousel o) {
        if(this.order!=null && o.getOrder()!=null)
            return this.order.compareTo(o.getOrder());
        else if(this.order!=null && o.getOrder()==null)
            return -1;
        else if(this.order==null && o.getOrder()!=null)
            return 1;
        return this.id.compareTo(o.getId());
    }
}
