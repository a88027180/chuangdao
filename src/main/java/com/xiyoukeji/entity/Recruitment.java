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
    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "recruit_duty")
    @Column(columnDefinition = "text")
    private List<String> duty;
    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "recruit_requirement")
    @Column(columnDefinition = "text")
    private List<String> requirement;
    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "recruit_place")
    private List<String> place;
    private String email;
    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "recruit_attr")
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

    public List<String> getDuty() {
        return duty;
    }

    public void setDuty(List<String> duty) {
        this.duty = duty;
    }

    public List<String> getRequirement() {
        return requirement;
    }

    public void setRequirement(List<String> requirement) {
        this.requirement = requirement;
    }

    public List<String> getPlace() {
        return place;
    }

    public void setPlace(List<String> place) {
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
