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
import com.liyang.domain.ordermdbt.Ordermdbt;
import com.liyang.domain.user.User;
import com.liyang.dto.OrdermdbtInfo;
import com.liyang.service.OrdermdbtService;
import com.liyang.util.CommonUtil;
import com.liyang.util.ExceptionResultEnum;
import com.liyang.util.FailReturnObject;
import com.liyang.util.PageInfo;
import com.liyang.util.ResponseBody;
import com.liyang.util.ReturnObject.Level;


@RestController
@RequestMapping("/ordermdbt")
public class OrdermdbtController {
	
	@Autowired
	OrdermdbtService ordermdbtService;
	 
	@GetMapping("/list")
	public ResponseBody getOrdermdbtList(Ordermdbt ordermdbt,DateTransform dateTransform,String stateCode,String companyName,Integer serviceUserId, Pageable pageable){
		
		Page<Ordermdbt> ordermdbtPage = ordermdbtService.getOrdermdbt(ordermdbt,dateTransform,stateCode,companyName,serviceUserId,pageable);
		Integer  number = ordermdbtPage.getNumber();
		Integer  size = ordermdbtPage.getSize();
		long  totalElements = ordermdbtPage.getTotalElements();
		Integer  totalPages = ordermdbtPage.getTotalPages();
		
		List<OrdermdbtInfo> ordermdbtInfoList = new ArrayList<>();
		for (Ordermdbt mdbt : ordermdbtPage) {
			
			OrdermdbtInfo ordermdbtInfo  = new OrdermdbtInfo();
			BeanUtils.copyProperties(mdbt, ordermdbtInfo);
			ordermdbtInfo.setStateLabel(mdbt.getState().getLabel());
			ordermdbtInfo.setCompanyName(mdbt.getCompany().getCompanyName());
			ordermdbtInfoList.add(ordermdbtInfo);			
		}
		//自定义的page对象
		PageInfo pageInfo = new PageInfo(number, size, totalElements, totalPages, ordermdbtInfoList);
		//返回的最外层对象
		ResponseBody  responseBody = new ResponseBody(pageInfo);
		return responseBody;
	}
	
	
	@GetMapping("/commissionerlist")
	public ResponseBody getCommissionerOrdermdbtList(Ordermdbt ordermdbt,DateTransform dateTransform,String stateCode,String companyName,Pageable pageable){
		                
		//获取当前登陆用户对象
		User user = CommonUtil.getPrincipal();
		ordermdbt.setServiceUser(user);
		
		Page<Ordermdbt> ordermdbtPage = ordermdbtService.getCommissionerOrdermdbtList(ordermdbt,dateTransform,stateCode,companyName,pageable);
		Integer  number = ordermdbtPage.getNumber();
		Integer  size = ordermdbtPage.getSize();
		long  totalElements = ordermdbtPage.getTotalElements();
		Integer  totalPages = ordermdbtPage.getTotalPages();
		
		List<OrdermdbtInfo> ordermdbtInfoList = new ArrayList<>();
		for (Ordermdbt mdbt : ordermdbtPage) {
			
			OrdermdbtInfo ordermdbtInfo  = new OrdermdbtInfo();
			BeanUtils.copyProperties(mdbt, ordermdbtInfo);
			ordermdbtInfo.setStateLabel(mdbt.getState().getLabel());
			ordermdbtInfo.setCompanyName(mdbt.getCompany().getCompanyName());
			ordermdbtInfoList.add(ordermdbtInfo);	
			
		}
		//自定义的page对象
		PageInfo pageInfo = new PageInfo(number, size, totalElements, totalPages, ordermdbtInfoList);
		//返回的最外层对象
		ResponseBody  responseBody = new ResponseBody(pageInfo);
		return responseBody;
	}
		
}
