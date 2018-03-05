package com.liyang.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class QuotaInfo {
	
					
	private Integer id;
	
	private Integer productId;
	
	private Integer productRecordId;
	
	private String productLabel;
	
	private String stateCode;
	
	private BigDecimal quotaAmount;
						
	/*创建时间*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Date applicationTime;
	
	private String applicationUserName;
	
	private String quotaNumber;
	
	private String applyEnterpriseName;	
	
	private String personName;

	private String applyMobile;
	//日收件量
	private Integer applyDayPickExpress;
	//日派件量
	private Integer applyDayPatchExpress;
	
	private BigDecimal currentAmount;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getProductRecordId() {
		return productRecordId;
	}

	public void setProductRecordId(Integer productRecordId) {
		this.productRecordId = productRecordId;
	}

	public String getProductLabel() {
		return productLabel;
	}

	public void setProductLabel(String productLabel) {
		this.productLabel = productLabel;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public Date getApplicationTime() {
		return applicationTime;
	}

	public void setApplicationTime(Date applicationTime) {
		this.applicationTime = applicationTime;
	}

	public String getApplicationUserName() {
		return applicationUserName;
	}

	public void setApplicationUserName(String applicationUserName) {
		this.applicationUserName = applicationUserName;
	}

	public String getQuotaNumber() {
		return quotaNumber;
	}

	public void setQuotaNumber(String quotaNumber) {
		this.quotaNumber = quotaNumber;
	}

	public String getApplyEnterpriseName() {
		return applyEnterpriseName;
	}

	public void setApplyEnterpriseName(String applyEnterpriseName) {
		this.applyEnterpriseName = applyEnterpriseName;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getApplyMobile() {
		return applyMobile;
	}

	public void setApplyMobile(String applyMobile) {
		this.applyMobile = applyMobile;
	}

	public Integer getApplyDayPickExpress() {
		return applyDayPickExpress;
	}

	public void setApplyDayPickExpress(Integer applyDayPickExpress) {
		this.applyDayPickExpress = applyDayPickExpress;
	}

	public Integer getApplyDayPatchExpress() {
		return applyDayPatchExpress;
	}

	public void setApplyDayPatchExpress(Integer applyDayPatchExpress) {
		this.applyDayPatchExpress = applyDayPatchExpress;
	}

	public BigDecimal getQuotaAmount() {
		return quotaAmount;
	}

	public void setQuotaAmount(BigDecimal quotaAmount) {
		this.quotaAmount = quotaAmount;
	}

	public BigDecimal getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(BigDecimal currentAmount) {
		this.currentAmount = currentAmount;
	}

	@Override
	public String toString() {
		return "QuotaInfo [id=" + id + ", productId=" + productId + ", productRecordId=" + productRecordId
				+ ", productLabel=" + productLabel + ", stateCode=" + stateCode + ", quotaAmount=" + quotaAmount
				+ ", applicationTime=" + applicationTime + ", applicationUserName=" + applicationUserName
				+ ", quotaNumber=" + quotaNumber + ", applyEnterpriseName=" + applyEnterpriseName + ", personName="
				+ personName + ", applyMobile=" + applyMobile + ", applyDayPickExpress=" + applyDayPickExpress
				+ ", applyDayPatchExpress=" + applyDayPatchExpress + ", currentAmount=" + currentAmount + "]";
	}

	
	
}
