package com.liyang.controller;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import org.hibernate.annotations.Parameter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.css.ElementCSSInlineStyle;

import com.liyang.domain.user.User;
import com.liyang.domain.user.UserInfoProjection;
import com.liyang.domain.user.UserRepository;
import com.liyang.dto.CompanyUserInfo;
import com.liyang.dto.UserInfo;
import com.liyang.dto.UserManagerInfo;
import com.liyang.service.UserService;
import com.liyang.util.CommonUtil;
import com.liyang.util.PageInfo;
import com.liyang.util.ResponseBody;

@RestController
@RequestMapping("/user")
public class UserController {
	
	
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    UserService userService;
    
    @GetMapping(value = "/personalCenter")
    public UserInfoProjection personalCenter() {
        return userRepository.findUserById(CommonUtil.getPrincipal().getId());
    }
    
    
	@GetMapping("/list")
	public ResponseBody getOrdersjsh(User user,Pageable pageable){
		Page<User> UserPage = userService.getUsers(user,pageable);
		Integer number = UserPage.getNumber();
		Integer size = UserPage.getSize();
		long totalElements = UserPage.getTotalElements();
		Integer totalPages = UserPage.getTotalPages();
		List<UserInfo> UserInfoInfoList = new ArrayList<>();
		for (User u : UserPage) {
			
			UserInfo userInfo = new UserInfo();
			userInfo.setId(u.getId());
			userInfo.setNickname(u.getNickname());
			userInfo.setHeadimgurl(u.getHeadimgurl());
			userInfo.setUsername(u.getUsername());
			userInfo.setCreatedAt(u.getCreatedAt());

			UserInfoInfoList.add(userInfo);			
		}
		//自定义的page对象
		PageInfo pageInfo = new PageInfo(number, size, totalElements, totalPages, UserInfoInfoList);
		//返回的最外层对象
		
		ResponseBody  responseBody = new ResponseBody(pageInfo);
		return responseBody;
	}
    
	@GetMapping("/getCompanyRole")
	public ResponseBody companyRole(Integer userid){ 
		User  user = userRepository.findByUserId(userid);
		String compangRole = user.getCompanyRole();
		if(compangRole == null){
			return new  ResponseBody(null);
		}
		if(compangRole.equals("manager")){
			return new  ResponseBody("manager");
		}
		if(compangRole.equals("employee")){
			return new  ResponseBody("employee");
		}		
		return new  ResponseBody(null);
	}
	
	
	@GetMapping("/backgroundlist")
	public ResponseBody getUserlist(String rolecode,String stateCode,Pageable pageable){
		Page<User> UserPage = userService.getBackgroundUsers(rolecode,stateCode,pageable);
		Integer number = UserPage.getNumber();
		Integer size = UserPage.getSize();
		long totalElements = UserPage.getTotalElements();
		Integer totalPages = UserPage.getTotalPages();
		List<UserManagerInfo> UserManagerInfoList = new ArrayList<>();
		for (User u : UserPage) {
			
			UserManagerInfo userManagerInfo = new UserManagerInfo();
			userManagerInfo.setId(u.getId());
			userManagerInfo.setHeadimgurl(u.getHeadimgurl());
			userManagerInfo.setNickname(u.getNickname());
			userManagerInfo.setUsername(u.getUsername());
			userManagerInfo.setRoleName(u.getRole().getLabel());
			userManagerInfo.setStateLabel(u.getState().getLabel());
			userManagerInfo.setSmsNoticer(u.getSmsNoticer());
			userManagerInfo.setCreatedAt(u.getCreatedAt());

			UserManagerInfoList.add(userManagerInfo);			
		}
		//自定义的page对象
		PageInfo pageInfo = new PageInfo(number, size, totalElements, totalPages, UserManagerInfoList);
		//返回的最外层对象
		
		ResponseBody  responseBody = new ResponseBody(pageInfo);
		return responseBody;
	}
	
	 @GetMapping("/getCompanyUsers")
	 public ResponseBody getUserByCompany(@PathParam("companyId") Integer companyId ){
		 List<User> userList = userService.getUserByCompany(companyId);
		 List<CompanyUserInfo> companyUserInfoList = new ArrayList<>();
		 for(User u : userList){
			 CompanyUserInfo companyUserInfo = new CompanyUserInfo();
			 BeanUtils.copyProperties(u, companyUserInfo);
			 companyUserInfoList.add(companyUserInfo);
		 }
		 return new ResponseBody(companyUserInfoList);
	 }
	
}
