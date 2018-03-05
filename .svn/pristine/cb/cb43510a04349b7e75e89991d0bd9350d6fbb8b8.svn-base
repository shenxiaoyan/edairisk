package com.liyang.service;

import com.liyang.aliyunsms.AliyunSmsService;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserRepository;
import com.liyang.util.FailReturnObject;
import com.liyang.util.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

@Service
public class SmsService {

	@Autowired
	private AliyunSmsService aliyunSmsService;
	@Autowired
	private CacheManager cacheManager;
	@Autowired
	private UserRepository userRepository;

	/**
	 *
	 * @param mobile
	 *            手机号
	 * @return 验证码
	 */
	public String sendSms(String mobile) {
		if (mobile == null || !Pattern.matches("^1[\\d]{10}$", mobile)) {
			throw new FailReturnObject(1971, "请输入正确的手机号", ReturnObject.Level.INFO);
		}
		Long millis = System.currentTimeMillis();
		Long lastTime = cacheManager.getCache("smsCode").get("lastTime", Long.class);
		if (lastTime != null && (millis - lastTime < 60000)) {
			throw new FailReturnObject(1895, "请一分钟后再获取验证码", ReturnObject.Level.INFO);
		}
		Random random = new Random();
		String code = Integer.toString(random.nextInt(9999) % (9999 - 1111 + 1) + 1111);
		aliyunSmsService.sendMessage(mobile, code, "SMS_105125051");
		cacheManager.getCache("smsCode").put("lastTime", millis);
		cacheManager.getCache("smsCode").put(mobile, code);
		return code;
	}

	/**
	 *
	 * @return 短信通知 状态操作等
	 * 
	 */
	// public String sendMessage(String mobile) {
	// if (mobile!=null) {
	// boolean sendMes = aliyunSmsService.sendMes(mobile, info(),
	// "SMS_119085953");
	// if (sendMes) {
	// return "发送成功";
	// }
	// else{
	// return "发送失败";
	// }
	// }
	//
	// return null;
	//
	// }

	public String sendCommitMessage(String orderNo, String orderType, String productName) {

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderNo", orderNo);
		param.put("orderType", orderType);
		param.put("label", productName);

		List<String> mobile = userRepository.findUsernameBySmsNoticer();
		System.out.println(mobile.size() + "----------------------------------------------");
		for (String username : mobile) {
			if (username == null || !Pattern.matches("^1[\\d]{10}$", username)) {
				throw new FailReturnObject(1971, "请输入正确的手机号", ReturnObject.Level.INFO);
			}
			aliyunSmsService.sendMes(username, param, "SMS_119085953");
		}
		return null;
	}

}
