package com.liyang.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class LoanInformation {
	
	private Integer id;
	
	//Loan里的information
	private  String information;
	
	//产品线名称
	private  String label;
	
	//产品线授信总额度
	private  BigDecimal creditGrant;
	
	//产品线剩余额度
	private BigDecimal creditBalance;
	
	//借款法人
	private String personName;
	
	//借款操作人
	private String userName;
	
	//申请日期
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date applyDate;
	
	//使用日期
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date useDate;
	
	//借款单号
	private String loanSn;
	
	//手机号
	private String phone;
	
	//借款金额
	private BigDecimal loanAmount;
	
	//总利息
	private BigDecimal totalInterest;
	
	//本息和
	private BigDecimal total;
	
	//剩余应还
	private BigDecimal remainAmount;
	
	//采购单位
	private String purchasingUnit;
	
	//公司名称
	private String companyName;
	
	/*状态*/
	private  String stateLabel;
	
	//是否逾期
	private Integer overdue;
	
	//person账号状态
	private String  accountStatus;
	
	
	
	public Integer getOverdue() {
		return overdue;
	}

	public void setOverdue(Integer overdue) {
		this.overdue = overdue;
	}

	public BigDecimal getCreditBalance() {
		return creditBalance;
	}

	public void setCreditBalance(BigDecimal creditBalance) {
		this.creditBalance = creditBalance;
	}

	public String getPurchasingUnit() {
		return purchasingUnit;
	}

	public void setPurchasingUnit(String purchasingUnit) {
		this.purchasingUnit = purchasingUnit;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public BigDecimal getRemainAmount() {
		return remainAmount;
	}

	public void setRemainAmount(BigDecimal remainAmount) {
		this.remainAmount = remainAmount;
	}

	public BigDecimal getTotalInterest() {
		return totalInterest;
	}

	public void setTotalInterest(BigDecimal totalInterest) {
		this.totalInterest = totalInterest;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public Date getUseDate() {
		return useDate;
	}

	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}

	public String getLoanSn() {
		return loanSn;
	}

	public void setLoanSn(String loanSn) {
		this.loanSn = loanSn;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
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

	public BigDecimal getCreditGrant() {
		return creditGrant;
	}

	public void setCreditGrant(BigDecimal creditGrant) {
		this.creditGrant = creditGrant;
	}

	public String getStateLabel() {
		return stateLabel;
	}

	public void setStateLabel(String stateLabel) {
		this.stateLabel = stateLabel;
	}
	
	

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	@Override
	public String toString() {
		return "LoanInformation [id=" + id + ", information=" + information + ", label=" + label + ", creditGrant="
				+ creditGrant + ", creditBalance=" + creditBalance + ", personName=" + personName + ", userName="
				+ userName + ", applyDate=" + applyDate + ", useDate=" + useDate + ", loanSn=" + loanSn + ", phone="
				+ phone + ", loanAmount=" + loanAmount + ", totalInterest=" + totalInterest + ", total=" + total
				+ ", remainAmount=" + remainAmount + ", purchasingUnit=" + purchasingUnit + ", companyName="
				+ companyName + ", stateLabel=" + stateLabel + ", overdue=" + overdue + ", accountStatus="
				+ accountStatus + "]";
	}

	
}
