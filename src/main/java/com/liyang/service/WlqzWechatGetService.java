package com.liyang.service;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Timer;

import javax.print.DocFlavor.STRING;
import javax.swing.Spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liyang.util.FailReturnObject;

@Service
public class WlqzWechatGetService {
	private static Logger log = LoggerFactory.getLogger(WlqzWechatGetService.class);
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Autowired
	private WlqzWechatPubService wechatPubService;

	@Value("${spring.wlqz.wechat.grant_type}")
	private String grant_type;

	@Value("${spring.wlqz.wechat.appid}")
	private String appid;

	@Value("${spring.wlqz.wechat.secret}")
	private String secret;

	@Value("${spring.wlqz.wechat.urlTokenTemplate}")
	private String urlTokenTemplate;

	private String urlTicketTemplate = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";

	private Map<String, Object> getAccessToken() {
		String url = String.format(urlTokenTemplate, grant_type, appid, secret);
		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper objectMapper = new ObjectMapper();
		String result = restTemplate.getForObject(url, String.class);
		Map<String, Object> resultMap = null;
		try {
			resultMap = objectMapper.readValue(result, Map.class);
			String access_token = (String) resultMap.get("access_token");
			System.out.println("access_token::" + access_token);
			if (access_token == null) {
				throw new FailReturnObject(3232,
						"errcode:" + (Integer) resultMap.get("errcode") + "errmsg:" + (String) resultMap.get("errmsg"),com.liyang.util.ReturnObject.Level.FATAL);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	public String getCacheTokenTotal() {
		Long nowTime = System.currentTimeMillis() / 1000;
		Long tokenTime = redisTemplate.getExpire("access_token_time");
		System.out.println(tokenTime);
		if (tokenTime == null || (nowTime - tokenTime) > 7000) {
			putTokenToCache(nowTime);
		}
		return redisTemplate.opsForValue().get("access_token");
	}

	public String getCacheTicket() {
		Long nowTime = System.currentTimeMillis() / 1000;
		Long tokenTime = redisTemplate.getExpire("access_ticket_time");
		if ((nowTime - tokenTime) > 7000) {
			putTicketToCache(nowTime);
		}
		return redisTemplate.opsForValue().get("ticket");
	}

	public void putTicketToCache(Long nowTime) {
		String url = String.format(urlTicketTemplate, getCacheTokenTotal());
		Map<String, Object> resultTicketMap = wechatPubService.getMapFromURL(url);
		String ticket = (String) resultTicketMap.get("ticket");
		Integer expires_in = (Integer) resultTicketMap.get("expires_in");
		if (null != ticket) {
			log.info("获取Ticket成功，Ticket为" + ticket + "有效时间为" + expires_in);
		} else {
			log.info("获取ticket失败" + resultTicketMap.get("errcode") + (String) resultTicketMap.get("errmsg"));
		}
		redisTemplate.opsForValue().set("ticket", ticket);
		redisTemplate.expireAt("access_ticket_time", new Date(nowTime));
	}

	public void putTokenToCache(Long nowTime) {
		Map<String, Object> resultTotalMap = getAccessToken();
		String accessToken_quan = (String) resultTotalMap.get("access_token");
		Integer expires_in = (Integer) resultTotalMap.get("expires_in");
		if (null != accessToken_quan) {
			log.info("获取Token成功，Token为" + accessToken_quan + "有效时间为" + expires_in);
		} else {
			log.info("获取Token失败" + resultTotalMap.get("errcode") + (String) resultTotalMap.get("errmsg"));
		}
		redisTemplate.opsForValue().set("access_token", accessToken_quan);
		redisTemplate.expireAt("access_token_time", new Date(nowTime));
	}
}
