package com.liyang.controller;
/**
 * 微信公众号
 *
 * @author win7
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.jws.soap.SOAPBinding.Use;
import javax.print.DocFlavor.STRING;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpConnection;
import org.hibernate.tuple.entity.EntityMetamodel.GenerationStrategyPair;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.Cache;
import com.google.gson.JsonObject;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserRepository;
import com.liyang.service.FileUploadService;
import com.liyang.service.WlqzWechatGetService;
import com.liyang.service.WechatLoginService;
import com.liyang.service.WlqzWechatPubService;
import com.liyang.util.CommonUtil;
import com.liyang.util.SignUtil;
import com.liyang.util.SuccessReturnObject;

import net.sf.json.JSONObject;
import net.sf.json.util.WebUtils;

/**
 * 微信公众号
 * @author win7
 *
 */
@Controller
public class WechatPubController {


    @Autowired
    private WlqzWechatPubService wechatPubService;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WechatLoginService wechatLoginService;
    @Autowired
    private WlqzWechatGetService wechatGetService;

    @ResponseBody
    @RequestMapping("/wechat/wlqz/state")
    public String getState() {
        return wechatLoginService.webLogin();
//		return  "";
    }

    //拉取用户信息
    @ResponseBody
    @RequestMapping("/wechat/wlqz/callback")
    public Map<String, String> callBack() {
        Map<String, String> map = new HashMap<>();
        if (CommonUtil.getPrincipal() == null) {
            map.put("message", "error");
            return map;
        }
        User user = CommonUtil.getPrincipal();
        map.put("message", user.getId() + "");
        return map;
    }
    /**
     * 根据微信图片id获取阿里云的地址
     */
//	@ResponseBody
//	@RequestMapping("/wechat/wlqz/loan")
//	public Map<String, String> loan(){
//		String url=URLEncoder.encode("http://design.xiaojinpingtai.com/wlqz/html/MyLoan.html");
//		Map<String, String> map=new HashMap<String,String>();
//		map.put("url", wechatPubService.authenize(url));
//		return map;
//	}

    /**
     * 用于微信生成自定义的菜单
     * @return
     */
    @RequestMapping("/wechat/wlqz/custommenu")
    @ResponseBody
    public String customMenu() {
        if (wechatPubService.isAccessToken()) {
            return "success";
        }
        return "fail";
    }

    @RequestMapping("/wechat/wlqz/pushMessage")
    @ResponseBody
    public Object testController() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("first", "正式菜");
        map.put("keyword1", "aaa");
        map.put("keyword2", "18702621932");
        map.put("keyword3", "没有开通");
        map.put("remark", "啦啦");
        return "ok";
    }

    @RequestMapping("/wechat/wlqz/getCofigure")
    @ResponseBody
    public String getTicket(@RequestParam(value = "JsUrl", required = false) String JsUrl) {
        String token_quanju = wechatGetService.getCacheTokenTotal();
        System.out.println(token_quanju);
        String ticket = wechatGetService.getCacheTicket();
        System.out.println(ticket);
        JSONObject json = JSONObject.fromObject(SignUtil.sign(ticket, JsUrl));
        return json.toString();
    }
    /**
     * 微信通过这个接口提供图片的url，通过这个接口返回阿里云服务器上图片的地址，接着微信将得到的json通过restful执行存储。
     * @throws IOException
     * @throws URISyntaxException
     *
     */

}
