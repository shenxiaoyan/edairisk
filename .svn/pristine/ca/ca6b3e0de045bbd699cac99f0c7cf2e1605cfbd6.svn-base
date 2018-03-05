package com.liyang.controller;


import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import com.liyang.domain.user.UserRepository;
import com.liyang.service.UserService;
import com.liyang.service.WlqzWechatPubService;
import com.liyang.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liyang.domain.user.User;
import com.liyang.service.WechatLoginService;

@Controller
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserRepository userRepository;

    @Autowired
    WechatLoginService wechatLoginService;

    @Autowired
    UserService userService;
    @Autowired
    WlqzWechatPubService wlqzWechatPubService;
    @Value(value = "${login.title}")
    private String loginTitle;
    @RequestMapping("/login")
    public String login(Model model) {
        wechatLoginService.connect(model);
        model.addAttribute("loginTitle",loginTitle);
        return "auth/login";
    }


    @RequestMapping(value = "/employeeApply")
    public String employeeApply(Model model) {

        wechatLoginService.apply(model, CommonUtil.getPrincipal());
        return "auth/login";
    }


    @RequestMapping(value = "/employeeBindWechat")
    public String employeeBindWechat(Model model) {
        wechatLoginService.bind(model);
        return "auth/login";
    }


    @RequestMapping(value = "/employeeApplyJson")
    @ResponseBody
    public Map employeeApplyJson(Model model) {

        wechatLoginService.apply(model, CommonUtil.getPrincipal());
        Set<Entry<String, Object>> entrySet = model.asMap().entrySet();
        HashMap<String, String> newmap = new HashMap<String, String>();
        for (Entry<String, Object> entry : entrySet) {
            newmap.put(entry.getKey(), entry.getValue().toString());
        }
        return newmap;
    }


    @PostMapping(value = "weblogin")
    @ResponseBody
    public Map weblogin(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        String credentials=null;
        String code=request.getParameter("code");


        map.put("errorCode", 0);
        if(CommonUtil.getPrincipal()!=null){
            map.put("msg", "已登录");
            return  map;
        }
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String smsCode = request.getParameter("smscode");

        if (username == null) {
            throw new FailReturnObject(1352,"请输入账户", ReturnObject.Level.INFO);
        }

        User user=null;
        String errorMessage=null;
        if(smsCode==null){
            if(password == null)
            {
                throw new FailReturnObject(1352,"请输入密码", ReturnObject.Level.INFO);
            }
            user = userRepository.findUserByUsernameAndPassword(username, MD5Util.encode(password));
            errorMessage="登录失败，用户名或密码错误！";
        }else {
            userService.verifySmsCode(username,smsCode);
            user=userRepository.findByUsername(username);
            errorMessage="登录失败，用户不存在！";
        }

        if (user==null) {
            throw new FailReturnObject(1353,errorMessage, ReturnObject.Level.INFO);
        }

        if(!"ENABLED".equals(user.getState().getStateCode())){
            throw new FailReturnObject(1243,"此用户无法登陆", ReturnObject.Level.INFO);
        }

        if(code!=null){
            credentials="wechat";
            wlqzWechatPubService.weixinBind(code,user);//微信web登录进行当前微信绑定;
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, credentials, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);



        map.put("msg", "登录成功");
        map.put("data",userRepository.findUserById(user.getId()));
        return map;
    }

    /**
     * app登录也走这边
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "register")
    @ResponseBody
    @Transactional
    public Map register(HttpServletRequest request, HttpServletResponse response) {
//        String smsCode = (String) request.getSession().getAttribute("smsCode");
//        if (!request.getParameter("smscode").equals(smsCode)) {
//            throw new FailReturnObject(2536, "验证码输入错误", ReturnObject.Level.INFO);
//        }
        System.out.println(request.getParameter("password"));
        System.out.println(request.getParameter("smscode"));
        System.out.println(request.getParameter("username"));
        userService.verifySmsCode(request.getParameter("username"),request.getParameter("smscode"));
        if(CommonUtil.getPrincipal()!=null){
            throw new FailReturnObject(1542,"请退出登录", ReturnObject.Level.INFO);
        }

        String code=request.getParameter("code");
        String state=request.getParameter("state");
        User user=userService.register(request);

        if(code!=null||state!=null){
            wlqzWechatPubService.weixinBind(code,user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(user,"wechat", user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        Map<String,Object> map=new HashMap<>();
        map.put("msg","注册成功");
        map.put("errorCode",0);
        map.put("Data",userRepository.findUserById(user.getId()));
        return map;
    }


    @PostMapping(value = "/resetPassword")
    @ResponseBody
    public Map resetPassword(HttpServletRequest request)
    {
        Map<String ,Object> map=new HashMap<>();
        userService.resetPassword(request);
        map.put("msg","重置密码成功！");
        map.put("errorCode",0);
        return  map;
    }
}
