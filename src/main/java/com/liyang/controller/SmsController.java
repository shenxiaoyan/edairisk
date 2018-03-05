package com.liyang.controller;

import com.liyang.service.SmsService;
import com.liyang.util.FailReturnObject;
import com.liyang.util.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

@RestController
public class SmsController {
    @Autowired
    SmsService smsService;

    @GetMapping("smsCode")
    public Map sms(HttpServletRequest request) {
        System.out.println("手机号"+request.getParameter("mobile"));
        String code =smsService.sendSms(request.getParameter("mobile"));
        Map<String, Object> map = new HashMap<>();
        map.put("errorCode", 0);
        map.put("msg","验证码已发送，请注意查收");
        
        return map;
    }

}
