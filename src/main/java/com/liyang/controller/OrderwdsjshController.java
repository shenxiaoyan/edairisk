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
import com.liyang.domain.orderwdsjsh.Orderwdsjsh;
import com.liyang.domain.orderwdsjsh.OrderwdsjshRepository;
import com.liyang.domain.user.User;
import com.liyang.dto.OrderwdsjshInfo;
import com.liyang.service.OrderwdsjshService;
import com.liyang.util.CommonUtil;
import com.liyang.util.ExceptionResultEnum;
import com.liyang.util.FailReturnObject;
import com.liyang.util.PageInfo;
import com.liyang.util.ResponseBody;
import com.liyang.util.ReturnObject.Level;

@RestController
@RequestMapping("/orderwdsjsh")
public class OrderwdsjshController {
	
	@Autowired
	OrderwdsjshService orderwdsjshService;
	
	@Autowired
	OrderwdsjshRepository orderwdsjshRepository;
	
	
	@GetMapping("/list")
	public ResponseBody getOrdersjsh(Orderwdsjsh orderwdsjsh,DateTransform dateTransform,String stateCode,Integer serviceUserId, Pageable pageable){
				
		Page<Orderwdsjsh> orderwdsjshPage = orderwdsjshService.getOrdersjsh(orderwdsjsh,dateTransform,stateCode,serviceUserId,pageable);
		List<OrderwdsjshInfo> orderwdsjshInfoList = new ArrayList<>();		
		
		Integer number = orderwdsjshPage.getNumber();
		Integer size = orderwdsjshPage.getSize();
		long totalElements = orderwdsjshPage.getTotalElements();
		Integer totalPages = orderwdsjshPage.getTotalPages();
		
		for (Orderwdsjsh ord : orderwdsjshPage) {	
			
			OrderwdsjshInfo orderwdsjshInfo = new OrderwdsjshInfo();			
			BeanUtils.copyProperties(ord, orderwdsjshInfo);
			orderwdsjshInfo.setStateLabel(ord.getState().getLabel());
			orderwdsjshInfoList.add(orderwdsjshInfo);		
			
		}
		//自定义的page对象
		PageInfo pageInfo = new PageInfo(number, size, totalElements, totalPages, orderwdsjshInfoList);
		//返回的最外层对象
		
		ResponseBody  responseBody = new ResponseBody(pageInfo);
		return responseBody;
	}
	
	
	@GetMapping("/commissionerlist")
	public ResponseBody getCommissionerOrdersjshList(Orderwdsjsh orderwdsjsh,DateTransform dateTransform,String stateCode,Integer serviceUserId,Pageable pageable){
		//获取当前登陆用户对象
		User user = CommonUtil.getPrincipal();
		orderwdsjsh.setServiceUser(user);
		
		Page<Orderwdsjsh> orderwdsjshPage = orderwdsjshService.getCommissionerOrdersjshList(orderwdsjsh,dateTransform,stateCode,pageable);
		List<OrderwdsjshInfo> orderwdsjshInfoList = new ArrayList<>();		
		
		Integer number = orderwdsjshPage.getNumber();
		Integer size = orderwdsjshPage.getSize();
		long totalElements = orderwdsjshPage.getTotalElements();
		Integer totalPages = orderwdsjshPage.getTotalPages();
		
		for (Orderwdsjsh ord : orderwdsjshPage) {			
			
			OrderwdsjshInfo orderwdsjshInfo = new OrderwdsjshInfo();			
			BeanUtils.copyProperties(ord, orderwdsjshInfo);
			orderwdsjshInfo.setStateLabel(ord.getState().getLabel());
			orderwdsjshInfoList.add(orderwdsjshInfo);	
			
		}
		//自定义的page对象
		PageInfo pageInfo = new PageInfo(number, size, totalElements, totalPages, orderwdsjshInfoList);
		//返回的最外层对象
		
		ResponseBody  responseBody = new ResponseBody(pageInfo);
		return responseBody;
	}
	
	//查询各个产品的申请条数
	@RequestMapping("/getproductAmount")
	public ResponseBody  productAmount(){
		return new ResponseBody(orderwdsjshService.productAmount());
	}
	
	
}
