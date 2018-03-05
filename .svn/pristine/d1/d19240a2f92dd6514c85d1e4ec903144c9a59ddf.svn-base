package com.liyang.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liyang.domain.datetransform.DateTransform;
import com.liyang.domain.orderrzzl.Orderrzzl;
import com.liyang.domain.user.User;
import com.liyang.dto.OrderrzzlInfo;
import com.liyang.service.OrderrzzlService;
import com.liyang.util.CommonUtil;
import com.liyang.util.ExceptionResultEnum;
import com.liyang.util.FailReturnObject;
import com.liyang.util.PageInfo;
import com.liyang.util.ResponseBody;
import com.liyang.util.ReturnObject.Level;


@RestController
@RequestMapping("/orderrzzl")
public class OrderrzzlController {
	
	@Autowired
	OrderrzzlService orderrzzlService;
	 
	@GetMapping("/list")
	public ResponseBody getOrderrzzlList(Orderrzzl orderrzzl,DateTransform dateTransform,String stateCode,Integer serviceUserId,Pageable pageable){
			
		Page<Orderrzzl> orderrzzlPage = orderrzzlService.getOrderrzzl(orderrzzl,dateTransform,stateCode,serviceUserId,pageable);
		Integer  number = orderrzzlPage.getNumber();
		Integer  size = orderrzzlPage.getSize();
		long  totalElements = orderrzzlPage.getTotalElements();
		Integer  totalPages = orderrzzlPage.getTotalPages();
		
		List<OrderrzzlInfo> orderrzzlInfoList = new ArrayList<>();
		for (Orderrzzl rzzl : orderrzzlPage) {
			
			OrderrzzlInfo orderrzzlInfo  = new OrderrzzlInfo();
			BeanUtils.copyProperties(rzzl, orderrzzlInfo);
			orderrzzlInfo.setStateLabel(rzzl.getState().getLabel());				
			orderrzzlInfoList.add(orderrzzlInfo);			
		}
		//自定义的page对象
		PageInfo pageInfo = new PageInfo(number, size, totalElements, totalPages, orderrzzlInfoList);
		//返回的最外层对象
		ResponseBody  responseBody = new ResponseBody(pageInfo);
		return responseBody;
	}
	
	
	@GetMapping("/commissionerlist")
	public ResponseBody getCommissionerOrderrzzlList(Orderrzzl orderrzzl,DateTransform dateTransform,String stateCode,Pageable pageable){
		//获取当前登陆用户对象
		User user = CommonUtil.getPrincipal();
		orderrzzl.setServiceUser(user);
		Page<Orderrzzl> orderrzzlPage = orderrzzlService.getCommissionerOrderrzzlList(orderrzzl,dateTransform,stateCode,pageable);
		
		Integer  number = orderrzzlPage.getNumber();
		Integer  size = orderrzzlPage.getSize();
		long  totalElements = orderrzzlPage.getTotalElements();
		Integer  totalPages = orderrzzlPage.getTotalPages();
		
		List<OrderrzzlInfo> orderrzzlInfoList = new ArrayList<>();
		for (Orderrzzl rzzl : orderrzzlPage) {
		
			OrderrzzlInfo orderrzzlInfo  = new OrderrzzlInfo();
			BeanUtils.copyProperties(rzzl, orderrzzlInfo);
			orderrzzlInfo.setStateLabel(rzzl.getState().getLabel());				
			orderrzzlInfoList.add(orderrzzlInfo);	
			
		}
		//自定义的page对象
		PageInfo pageInfo = new PageInfo(number, size, totalElements, totalPages, orderrzzlInfoList);
		//返回的最外层对象
		ResponseBody  responseBody = new ResponseBody(pageInfo);
		return responseBody;
	}
	
	
}
