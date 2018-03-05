package com.liyang.controller;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liyang.domain.approveloan.ApproveLoan;
import com.liyang.dto.ApproveLoanInfo;
import com.liyang.service.ApproveLoanService;
import com.liyang.util.FailReturnObject;
import com.liyang.util.PageInfo;
import com.liyang.util.ResponseBody;
import com.liyang.util.ReturnObject;

@RestController
@RequestMapping("/approveLoan")
public class ApproveLoanController {
	
	@Autowired
	private ApproveLoanService approveLoanService;
	
	@PostMapping("/adopt")
	public ResponseBody adopt(@PathParam("approveLoanId") Integer approveLoanId){		
		ApproveLoan loan = new ApproveLoan();
		loan = approveLoanService.adopt(approveLoanId); 
		if(loan == null){
		   throw new FailReturnObject(1330, "借款申请通过异常", ReturnObject.Level.WARNING);
		}
		   return new ResponseBody("借款申请已通过");
	}

	@PostMapping("/notadopt")
	public ResponseBody notAdopt(@PathParam("approveLoanId") Integer approveLoanId){	
		ApproveLoan loan = new ApproveLoan();
		loan = approveLoanService.notAdopt(approveLoanId);
		if(loan == null){
			throw new FailReturnObject(1331, "借款申请未通过异常", ReturnObject.Level.WARNING);
		}
		    return new ResponseBody("借款申请未通过");
	}
	
	@PostMapping("/addApproveLoan")
	public ResponseBody notAdopt(ApproveLoan approveLoan){	
		ApproveLoan loan = new ApproveLoan();
		loan = approveLoanService.addApproveLoan(approveLoan); 		
		if(loan == null){
			throw new FailReturnObject(1332, "异常借款提交异常", ReturnObject.Level.WARNING);
		}
		   return new ResponseBody("异常借款提交成功");
	}
	
	@GetMapping("/list")
	public ResponseBody getApproveLoanList(String stateCode,String personName,ApproveLoan approveLoan,Pageable pageable){
	
		Page<ApproveLoan> approveLoanPage = approveLoanService.getApproveLoanList(stateCode,personName,approveLoan,pageable);
		Integer  number = approveLoanPage.getNumber();
		Integer  size = approveLoanPage.getSize();
		long  totalElements = approveLoanPage.getTotalElements();
		Integer  totalPages = approveLoanPage.getTotalPages();
		
		List<ApproveLoanInfo> approveLoanInfoList = new ArrayList<>();
		for (ApproveLoan AP : approveLoanPage) {
			
			ApproveLoanInfo approveLoanInfo = new ApproveLoanInfo();
			BeanUtils.copyProperties(AP, approveLoanInfo);
			approveLoanInfo.setPersonName(AP.getPerson().getName());
			approveLoanInfo.setStateCode(AP.getApproveLoanState().getStateCode());
			approveLoanInfo.setStateLabel(AP.getApproveLoanState().getStateLabel());
			approveLoanInfoList.add(approveLoanInfo);	
			
		}
		//自定义的page对象
		PageInfo pageInfo = new PageInfo(number, size, totalElements, totalPages, approveLoanInfoList);
		//返回的最外层对象
		ResponseBody  responseBody = new ResponseBody(pageInfo);
		return responseBody;
	}
}
