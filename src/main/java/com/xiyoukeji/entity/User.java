package com.xiyoukeji.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Created by Matilda on 2016/12/26.
 */

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Integer id;
    @NotNull
    private String password;
    private int type;   //管理员0， 普通用户1
    @Column(length = 20, unique = true)
    private String phone;

    private String risk;
    private long risk_time = 0L;
    private int risk_times=0;
    private String date;
    private Integer times;
    @Lob
    private String questionnaire; // 问卷填写情况，未填写则为空


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public String getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(String questionnaire) {
        this.questionnaire = questionnaire;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRisk() {
        return risk;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }

    public long getRisk_time() {
        return risk_time;
    }

    public void setRisk_time(long risk_time) {
        this.risk_time = risk_time;
    }

    public int getRisk_times() {
        return risk_times;
    }

    public void setRisk_times(int risk_times) {
        this.risk_times = risk_times;
    }
}
