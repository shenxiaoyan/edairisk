package com.liyang.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jpa.query.expression.QueryExpSpecificationBuilder;
import com.jpa.query.expression.generic.GenericQueryExpSpecification;
import com.liyang.Enum.OrderTypeEnum;
import com.liyang.aliyunsms.AliyunSmsService;
import com.liyang.domain.approveloan.ApproveLoan;
import com.liyang.domain.approveloan.ApproveLoanRepository;
import com.liyang.domain.approveloan.ApproveLoanStateRepository;
import com.liyang.domain.orderwdsjsh.Orderwdsjsh;
import com.liyang.domain.orderwdsjsh.OrderwdsjshRepository;
import com.liyang.util.FailReturnObject;
import com.liyang.util.ProductUtil;
import com.liyang.util.ReturnObject;


@Service
public class ApproveLoanService {
	
	@Autowired
    private ApproveLoanRepository approveLoanRepository;
	@Autowired
	private ApproveLoanStateRepository approveLoanStateRepository;
	@Autowired
	OrderwdsjshRepository orderwdsjshRepository;
	@Autowired
	AliyunSmsService aliyunSmsService;
	
	@Transactional
	public ApproveLoan addApproveLoan(ApproveLoan approveLoan){
		ApproveLoan loan = new ApproveLoan();
		String orderNo = ProductUtil.modify("网点随借随还", OrderTypeEnum.ABNORMALLOAN);
		approveLoan.setOrderNo(orderNo);
		loan = approveLoanRepository.save(approveLoan);
		Orderwdsjsh ord = orderwdsjshRepository.findByPersonId(approveLoan.getPerson().getId());
		//通知业务员
		if(ord.getServiceUser() != null){
			if(ord.getServiceUser().getUsername() == null || !Pattern.matches("^1[\\d]{10}$", ord.getServiceUser().getUsername())){
				throw new FailReturnObject(1328, "请输入正确的手机号", ReturnObject.Level.INFO);
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("orderNo", ord.getOrderNo());
			param.put("orderType", "借款申请");
			param.put("label", "网点随借随还");
			aliyunSmsService.sendMes(ord.getServiceUser().getUsername(), param, "SMS_119085953");
		}
		return loan;
	}
	
	@Transactional
	public ApproveLoan adopt(Integer approveLoanId){		
		ApproveLoan approveLoan  = approveLoanRepository.findOne(approveLoanId);
		approveLoan.setApproveLoanState(approveLoanStateRepository.findByStateCode("ADOPT"));
		approveLoan.setPassDate(new Date());
		approveLoanRepository.save(approveLoan);
		return approveLoan;
	}
	
	@Transactional
	public ApproveLoan notAdopt(Integer approveLoanId){		
		ApproveLoan approveLoan  = approveLoanRepository.findOne(approveLoanId);
		approveLoan.setApproveLoanState(approveLoanStateRepository.findByStateCode("DENIED"));
		approveLoan.setPassDate(new Date());
		approveLoanRepository.save(approveLoan);
		return approveLoan;
	}

	
    //根据条件动态查询并分页
    public Page<ApproveLoan> getApproveLoanList(String stateCode,String personName,ApproveLoan approveLoan,Pageable pageable){ 
    	
	    GenericQueryExpSpecification<ApproveLoan> queryExpression = new GenericQueryExpSpecification<ApproveLoan>();
	    queryExpression.add(QueryExpSpecificationBuilder.eq("approveLoanState.stateCode", stateCode, true));
	    queryExpression.add(QueryExpSpecificationBuilder.eq("mobile", approveLoan.getMobile(), true));
	    queryExpression.add(QueryExpSpecificationBuilder.eq("person.name", personName, true));
	    queryExpression.add(QueryExpSpecificationBuilder.eq("orderNo", approveLoan.getOrderNo(), true));
    	queryExpression.add(QueryExpSpecificationBuilder.gt("createDate", approveLoan.getCreateDateTime(), true));
    	queryExpression.add(QueryExpSpecificationBuilder.lt("createDate", approveLoan.getEndDateTime(), true));
		return approveLoanRepository.findAll(queryExpression,pageable);	
		
    }
   
    
    
}
