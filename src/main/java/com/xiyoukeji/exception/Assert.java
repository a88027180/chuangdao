package com.xiyoukeji.exception;

import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * Created by wangqiyun on 2017/5/12.
 */
public class Assert {
    public static void notNull(Object... objects) {
        for (Object o : objects) {
            if (o == null) throw new ErrCodeException("参数错误");
        }
    }

    public static void notBlank(Object... objects) {
        for (Object o : objects) {
            if (o == null || o.toString().isEmpty()) throw new ErrCodeException("参数错误");
        }
    }

    public static void notEmpty(Object... objects) {
        for (Object o : objects) {
            if (o == null) throw new ErrCodeException();
            if (o instanceof String && ((String) o).isEmpty()) throw new ErrCodeException("参数错误");
            if (o instanceof Collection && ((Collection) o).isEmpty()) throw new ErrCodeException("参数错误");
            if (o instanceof Map && ((Map) o).isEmpty()) throw new ErrCodeException("参数错误");
        }
    }
}
