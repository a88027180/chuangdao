package com.xiyoukeji.service;

import com.xiyoukeji.entity.VerifyCode;
import com.xiyoukeji.exception.Assert;
import com.xiyoukeji.exception.ErrCodeException;
import com.xiyoukeji.tools.BaseDao;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by wangqiyun on 2017/6/9.
 */
@Service
public class VerifyService {
    private static final String apikey = "65cbd2cf0d7c8e2530cfbca857b72a4d";
    private static final String url = "https://sms.yunpian.com/v2/sms/single_send.json";
    private static final Pattern pattern = Pattern.compile("^1[3|4|5|7|8]\\d{9}$");

    @Resource
    BaseDao<VerifyCode> baseDao;


    @Transactional
    public void sendPhoneCode(String phone) {
        Assert.notBlank(phone);
        if (!pattern.matcher(phone).matches()) throw new ErrCodeException("手机号格式不正确");
        VerifyCode code = baseDao.get(VerifyCode.class, phone);
        if (code != null) {
            if (System.currentTimeMillis() - code.getTime() < 60000L) throw new ErrCodeException("获取验证码过于频繁");
            baseDao.delete(code);
        }
        code = new VerifyCode();
        code.setPhone(phone);
        baseDao.save(code);
        sendPhoneCode(code);
    }

    @Transactional
    public void verify(String phone, String code) {
        Assert.notBlank(phone, code);
        VerifyCode verifyCode = baseDao.get(VerifyCode.class, phone);
        if (verifyCode != null) {
            if (System.currentTimeMillis() - verifyCode.getTime() < 1200000L) {
                if (verifyCode.getCode().equals(code)) {
                    baseDao.delete(verifyCode);
                    return;
                }
            }
        }
        throw new ErrCodeException("验证码错误");
    }


    public void sendPhoneCode(VerifyCode verifyCode) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> list = new ArrayList<>();
        list.add(new NameValuePair() {
            @Override
            public String getName() {
                return "apikey";
            }

            @Override
            public String getValue() {
                return apikey;
            }
        });
        list.add(new NameValuePair() {
            @Override
            public String getName() {
                return "mobile";
            }

            @Override
            public String getValue() {
                return verifyCode.getPhone();
            }
        });
        list.add(new NameValuePair() {
            @Override
            public String getName() {
                return "text";
            }

            @Override
            public String getValue() {
                return "【创道资本】您的验证码是" + verifyCode.getCode() + "。如非本人操作，请忽略本短信";
            }
        });
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(list, Charset.forName("utf8")));
            CloseableHttpResponse response = httpClient.execute(httpPost);
            httpClient.close();
            response.close();
        } catch (IOException e) {
            throw new ErrCodeException("验证码获取失败");
        }
    }
}
