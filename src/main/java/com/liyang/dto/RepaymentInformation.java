package com.liyang.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RepaymentInformation {
	
	private Integer id;

	// 还款记录information
	private String information;

	// 产品线名称
	private String label;

	// 客户姓名
	private String name;

	// 是否逾期
	private Integer isOverdue;

	// 本金
	private BigDecimal principal;

	// 利息
	private BigDecimal interest;

	// 罚息
	private BigDecimal punishinterest;
	
	//还款单号
	private String orderNo;
	
	//实际还款时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date realDate;
	
	//还款人
	private String repaymentName;
	
	//手机号
	private String phoneString;
	
	//信用卡剩余额度
	private BigDecimal remainAmount;
	
	//计划还款日期
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date planDate;
	
	//实际还款金额
	private BigDecimal realPayAmount;
	
	/*状态名称*/
	private  String stateLabel;
	
	//本次应还金额
	private BigDecimal fees;
	
	
	
	
	public BigDecimal getFees() {
		return fees;
	}

	public void setFees(BigDecimal fees) {
		this.fees = fees;
	}

	public BigDecimal getRealPayAmount() {
		return realPayAmount;
	}

	public void setRealPayAmount(BigDecimal realPayAmount) {
		this.realPayAmount = realPayAmount;
	}

	public Date getPlanDate() {
		return planDate;
	}

	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}

	public BigDecimal getRemainAmount() {
		return remainAmount;
	}

	public void setRemainAmount(BigDecimal remainAmount) {
		this.remainAmount = remainAmount;
	}

	public String getPhoneString() {
		return phoneString;
	}

	public void setPhoneString(String phoneString) {
		this.phoneString = phoneString;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getRealDate() {
		return realDate;
	}

	public void setRealDate(Date realDate) {
		this.realDate = realDate;
	}

	public String getRepaymentName() {
		return repaymentName;
	}

	public void setRepaymentName(String repaymentName) {
		this.repaymentName = repaymentName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIsOverdue() {
		return isOverdue;
	}

	public void setIsOverdue(Integer isOverdue) {
		this.isOverdue = isOverdue;
	}

	public BigDecimal getPrincipal() {
		return principal;
	}

	public void setPrincipal(BigDecimal principal) {
		this.principal = principal;
	}

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	public BigDecimal getPunishinterest() {
		return punishinterest;
	}

	public void setPunishinterest(BigDecimal punishinterest) {
		this.punishinterest = punishinterest;
	}

	public String getStateLabel() {
		return stateLabel;
	}

	public void setStateLabel(String stateLabel) {
		this.stateLabel = stateLabel;
	}

}
