package com.xiyoukeji.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Random;

/**
 * Created by wangqiyun on 2017/6/8.
 */
@Entity
@Table(name = "verify_code")
public class VerifyCode {
    @Id
    @Column(length = 20)
    private String phone;
    private long time = System.currentTimeMillis();
    private String code = String.format("%06d", new Random().nextInt(999999));

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
