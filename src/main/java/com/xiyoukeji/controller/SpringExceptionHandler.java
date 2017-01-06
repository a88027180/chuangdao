package com.xiyoukeji.controller;

import com.xiyoukeji.tools.State;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matilda on 2017/1/6.
 */

@ControllerAdvice
public class SpringExceptionHandler {

    private static Logger logger = Logger.getLogger(SpringExceptionHandler.class);

    @ExceptionHandler
    @ResponseBody
    public Map processException(Exception e){
        Map<String, Object> map = new HashMap<>();
        map.put("state", State.EXCEPTION.value());
        map.put("detail", "Exception occurred");
        map.put("exception: ", e.getLocalizedMessage());
        e.printStackTrace();

        // 将StackTrace输出到日志
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        String str = sw.toString();
        logger.error (str) ;

        return map;
    }
}
