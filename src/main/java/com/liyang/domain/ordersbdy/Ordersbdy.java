package com.liyang.domain.ordersbdy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.annotation.PersonField;
import com.liyang.domain.account.Account;
import com.liyang.domain.application.Application;
import com.liyang.domain.base.AbstractWorkflowEntity;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.creditcard.Creditcard;
import com.liyang.domain.enterprise.Enterprise;
import com.liyang.domain.person.Person;
import com.liyang.domain.product.Product;
import com.liyang.domain.user.User;
import com.liyang.service.AbstractAuditorService;
import com.liyang.service.AbstractWorkflowService;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 
 * @author win7
 *
 */

@Entity
@Table(name = "ordersbdy")
@Cacheable
@Info(label="设备抵押",secret="目前主要是贝兜做为供应商")
public class Ordersbdy extends AbstractWorkflowEntity<OrdersbdyWorkflow, OrdersbdyState, OrdersbdyAct, OrdersbdyLog,OrdersbdyFile> {
	@Transient
	private static AbstractWorkflowService service;

	@Transient
	private static ActRepository actRepository;

	@Transient
	private static LogRepository logRepository;
	@JsonIgnore
	@Override
	public AbstractAuditorService getService() {
		return service;
	}

	@Override
	public void setService(AbstractAuditorService service) {
		Ordersbdy.service= (AbstractWorkflowService) service;
	}

	@Override
	@JsonIgnore
	@Transient
	public OrdersbdyLog getLogInstance() {
		// TODO Auto-generated method stub
		return new OrdersbdyLog();
	}

	@Override
	@JsonIgnore
	@Transient
	public LogRepository getLogRepository() {
		// TODO Auto-generated method stub
		return logRepository;
	}

	@Override
	public void setLogRepository(LogRepository logRepository) {
		Ordersbdy.logRepository = logRepository;
	}
	//各个order里带入 DAY/WEEK/MONTH
	public static enum PeriodCode{
		DAY,WEEK,MONTh
	}

	/**
	 * 是否是启用状态
	 * @return
	 */
	public boolean isStateEnable(){
		return getState().getStateCode().equals("ENABLED");
	}

	/**
	 * 是否是已接受状态
	 * @return
	 */
	public boolean isStateAdopt(){
		return getState().getStateCode().equals("ADOPT");		
	}

}