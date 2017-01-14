package com.xiyoukeji.auth;

import java.lang.annotation.*;

/**
 * Created by Matilda on 2017/1/13.
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EditAuthority {   // 用@interface自定义注解
    String value();
}
