package com.xiyoukeji.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

/**
 * 招聘信息
 *
 * Created by Matilda on 2016/12/14.
 */

@Entity
@Table(name = "recruitment")
public class Recruitment {

    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Integer id;
    private String position; // 职位
    @Column(columnDefinition = "text")
    private String duty;
    @Column(columnDefinition = "text")
    private String requirement;
    private String place;
    private String email;
    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "recruitment_attr")
    private Map<String, String> attr;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, String> getAttr() {
        return attr;
    }

    public void setAttr(Map<String, String> attr) {
        this.attr = attr;
    }
}
