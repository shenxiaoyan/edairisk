package com.liyang.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.bouncycastle.math.raw.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liyang.domain.department.Department;
import com.liyang.domain.department.DepartmentRepository;
import com.liyang.domain.role.Role;
import com.liyang.domain.role.RoleRepository;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserAct;
import com.liyang.domain.user.UserRepository;
import com.liyang.domain.user.UserState;
import com.liyang.domain.user.UserStateRepository;
import com.liyang.util.CommonUtil;
import com.liyang.util.FailReturnObject;
import com.liyang.util.ReturnObject;
import com.liyang.util.TIMSignature;
import com.liyang.util.TreeNode;
import com.liyang.util.TreeNodeImpl;
import com.liyang.util.WechatProperties;

@Service
public class WechatLoginService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private WechatProperties properties;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Autowired
	private CacheManager cacheManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserStateRepository userStateRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private TIMService timService;

	@Autowired
	private AccountService accountService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private WlqzWechatPubService wlqzWechatPubService;

	public String webLogin() {
		UUID randomUUID = UUID.randomUUID();
		String state = randomUUID.toString();
		cacheManager.getCache("wechatLogin").put(state, "wechat:0");
		return state;
	}

	public void connect(Model model) {
		setupAttribute(model);
		UUID randomUUID = UUID.randomUUID();
		String state = randomUUID.toString();
		model.addAttribute("state", state);
		cacheManager.getCache("wechatLogin").put(state, "login:0");

	}

	public void apply(Model model, User fromUser) {
		setupAttribute(model);
		UUID randomUUID = UUID.randomUUID();
		String state = randomUUID.toString();
		model.addAttribute("state", state);
		if (fromUser == null) {
			cacheManager.getCache("wechatLogin").put(state, "apply:0");
		} else {
			cacheManager.getCache("wechatLogin").put(state, "apply:" + fromUser.getId().toString());
		}

	}

	public void bind(Model model) {
		setupAttribute(model);
		UUID randomUUID = UUID.randomUUID();
		String state = randomUUID.toString();
		model.addAttribute("state", state);
		cacheManager.getCache("wechatLogin").put(state, "bind:" + CommonUtil.getPrincipal().getId().toString());

	}
	
	
	private void setupAttribute(Model model) {
		model.addAttribute("appid", properties.getAppid());
		model.addAttribute("scope", properties.getScope());
		model.addAttribute("redirect_uri", properties.getRedirect_uri());
		model.addAttribute("secret", properties.getSecret());

	}

	@Transactional
	public Authentication authorize(String code, String state) {

		ValueWrapper valueWrapper = cacheManager.getCache("wechatLogin").get(state);
		if (valueWrapper == null) {
			throw new FailReturnObject(110, "缓存已经过期",com.liyang.util.ReturnObject.Level.WARNING);
		}
		// state=webchatLogin 表示微信登录
		String str = cacheManager.getCache("wechatLogin").get(state, String.class);
		System.out.println(str);


		if(str.equals("wechat:0")){//微信app里面登录
			User user =  wlqzWechatPubService.getUserByCode(code);
			return  doLogin(user,"wechat");
		}



		Map token = getAccessToken(code);
		User exist_user = userRepository.findByOpenid(token.get("openid").toString());//
		
		
		if (str.startsWith("bind:")) {
			if (exist_user != null) {
				throw new FailReturnObject(1572, "微信已经被绑定过",com.liyang.util.ReturnObject.Level.WARNING);
			}
			Integer userid = Integer.valueOf(str.substring(5));
			if (userid != null) {
				User own = userRepository.findOne(userid);
				own = fillOwnInfo(own,token);
				userRepository.save(own);
			}
			return null;
		}
		else if (str.startsWith("login:")) {
			return doLogin(exist_user);

		} else if (str.startsWith("apply:")) {
			if (exist_user != null) {
				throw new FailReturnObject(157, "用户已存在",com.liyang.util.ReturnObject.Level.WARNING);
			}
			User applyUser = tokenToUser(token);
			UserAct actApply = userService.getAct(applyUser, "create", applyUser.getRole());
			User fromUser = null;
			Integer userid = Integer.valueOf(str.substring(6));
			if (userid != null && !userid.equals(0)) {
				fromUser = userRepository.findOne(userid);
			}
			if (fromUser != null) {
				applyUser.setDepartment(fromUser.getDepartment());
			}

			applyUser = userService.doBeforeAct(applyUser, actApply, null, fromUser);
			applyUser = userRepository.save(applyUser);
			applyUser.setSig(TIMSignature.generate(applyUser.getId().toString()).urlSig);
			applyUser = userRepository.save(applyUser);
			//begin 新建完user创建默认的account --Jax
			accountService.createdDefaultAccountByUser(applyUser);
			//end

			timService.addUser(applyUser.getId().toString(), applyUser.getNickname(), applyUser.getHeadimgurl());
			userService.doAfterAct(applyUser, null, fromUser);
			return null;
		}else{
			throw new FailReturnObject(175, "微信登录缓存格式不符",com.liyang.util.ReturnObject.Level.ERROR);
		}

	}

	private User fillOwnInfo(User user, Map token) {
		Map info = getUserInfo(token.get("access_token").toString(), token.get("openid").toString());
		user.setOpenid(token.get("openid").toString());
		user.setUnionid(token.get("unionid").toString());
		user.setNickname(CommonUtil.filter(info.get("nickname").toString()));
		user.setSex(Integer.valueOf(info.get("sex").toString()));
		user.setProvince(info.get("province").toString());
		user.setCity(info.get("city").toString());
		user.setCountry(info.get("country").toString());
		user.setHeadimgurl(info.get("headimgurl").toString());
		return user;
		
	}

	private User tokenToUser(Map token) {
		Map info = getUserInfo(token.get("access_token").toString(), token.get("openid").toString());
		User user = new User();
		user.setOpenid(token.get("openid").toString());
		user.setUnionid(token.get("unionid").toString());
		user.setNickname(CommonUtil.filter(info.get("nickname").toString()));
		user.setSex(Integer.valueOf(info.get("sex").toString()));
		user.setProvince(info.get("province").toString());
		user.setCity(info.get("city").toString());
		user.setCountry(info.get("country").toString());
		user.setHeadimgurl(info.get("headimgurl").toString());
		UserState state = userStateRepository.findByStateCode("UNBORN");
		user.setBeforeState(state);
		Role role = roleRepository.findByRoleCode("USER");
		user.setRole(role);
		return user;
	}

	/**
	 * 微信登录
	 *
	 * @param user
	 * @param wechat
	 * @return
	 */
	private Authentication doLogin(User user, String wechat) {
		if (user == null) {
			throw new FailReturnObject(155, "用户不存在",com.liyang.util.ReturnObject.Level.FATAL);
			//} else if ("DISABLED".equals(user.getState().getStateCode())) {
		} else if (user.isUserStateDisabled()) {
			throw new FailReturnObject(160, "用户被禁用",com.liyang.util.ReturnObject.Level.WARNING);
			//} else if ("DELETED".equals(user.getState().getStateCode())) {
		} else if (user.isUserStateDeleted()) {
			throw new FailReturnObject(163, "用户被删除",com.liyang.util.ReturnObject.Level.WARNING);
			//} else if ("APPLIED".equals(user.getState().getStateCode())) {
		} else if (user.isUserStateApplied()) {
			throw new FailReturnObject(165, "用户正在被审核",com.liyang.util.ReturnObject.Level.WARNING);
		}
		List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();
		if (user.getRole() == null) {
			throw new FailReturnObject(1340, "用户没有角色",com.liyang.util.ReturnObject.Level.FATAL);
		}
		AUTHORITIES.add(new SimpleGrantedAuthority(user.getRole().getRoleCode().toString()));
		List<Department> ownAndChildrenDepartments = CommonUtil.ownAndChildrenDepartments(user.getDepartment());
		if (!ownAndChildrenDepartments.isEmpty()) {
			user.setChildrenDepartments(ownAndChildrenDepartments);
		}
		Authentication authentication = new UsernamePasswordAuthenticationToken(user, wechat, AUTHORITIES);
		return authentication;
	}
	/**
	 * web前端登录
	 * 
	 * @param user
	 * @return
	 */
	private Authentication doLogin(User user) {

		if (user == null) {
			throw new FailReturnObject(155, "用户不存在",com.liyang.util.ReturnObject.Level.WARNING);
		//} else if ("DISABLED".equals(user.getState().getStateCode())) {
		} else if (user.isUserStateDisabled()) {
			throw new FailReturnObject(160, "用户被禁用",com.liyang.util.ReturnObject.Level.WARNING);
		//} else if ("DELETED".equals(user.getState().getStateCode())) {
		} else if ( user.isUserStateDeleted()) {
			throw new FailReturnObject(163, "用户被删除",com.liyang.util.ReturnObject.Level.WARNING);
		//} else if ("APPLIED".equals(user.getState().getStateCode())) {
		} else if (user.isUserStateApplied()) {
			throw new FailReturnObject(165, "用户正在被审核",com.liyang.util.ReturnObject.Level.WARNING);
		}
		List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();
		if (user.getRole() == null) {
			throw new FailReturnObject(1340, "用户没有角色",com.liyang.util.ReturnObject.Level.FATAL);
		}
		AUTHORITIES.add(new SimpleGrantedAuthority(user.getRole().getRoleCode().toString()));
		List<Department> ownAndChildrenDepartments = CommonUtil.ownAndChildrenDepartments(user.getDepartment());
		if (!ownAndChildrenDepartments.isEmpty()) {
			user.setChildrenDepartments(ownAndChildrenDepartments);
		}
		Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, AUTHORITIES);

		return authentication;

	}

	private Map getAccessToken(String code) {
		RestTemplate restTemplate = new RestTemplate();
		String user = restTemplate
				.getForObject(
						"https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + properties.getAppid() + "&secret="
								+ properties.getSecret() + "&code=" + code + "&grant_type=authorization_code",
						String.class);
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> m = mapper.readValue(user, Map.class);
			if (m.containsKey("errcode")) {
				throw new FailReturnObject(130, "获取微信token返回错误：" + m.get("errmsg").toString(),com.liyang.util.ReturnObject.Level.FATAL);
			}

			return m;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new FailReturnObject(120, "用户微信授权解析失败",com.liyang.util.ReturnObject.Level.ERROR);
		}
	}

	private Map getUserInfo(String token, String openid) {
		RestTemplate restTemplate = new RestTemplate();

		String user = restTemplate.getForObject(
				"https://api.weixin.qq.com/sns/userinfo?access_token=" + token + "&openid=" + openid, String.class);

		try {
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> m = mapper.readValue(user, Map.class);
			if (m.containsKey("errcode")) {
				throw new FailReturnObject(140, "获取微信userinfo返回错误：" + m.get("errmsg").toString(),com.liyang.util.ReturnObject.Level.FATAL);
			}
			String nickname = (String) m.get("nickname");
			String name = new String(nickname.getBytes("ISO-8859-1"), "utf-8");
			m.put("nickname", name);
			return m;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new FailReturnObject(150, "用户微信信息解析失败",com.liyang.util.ReturnObject.Level.ERROR);
		}
	}
}
