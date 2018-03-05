package com.liyang.dto;

import java.util.Date;


import com.fasterxml.jackson.annotation.JsonFormat;

public class CompanyListInfo {
	
	private Integer id;
	
	private String companyName;
	
	private String companyNumber;
	
	private String addressFullName;
	
	private String companyPersonName;
	
	private String state;
	
	private String label;
	
	private Integer peopleAmount;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createdAt;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyNumber() {
		return companyNumber;
	}

	public void setCompanyNumber(String companyNumber) {
		this.companyNumber = companyNumber;
	}

	public String getAddressFullName() {
		return addressFullName;
	}

	public void setAddressFullName(String addressFullName) {
		this.addressFullName = addressFullName;
	}

	public String getCompanyPersonName() {
		return companyPersonName;
	}

	public void setCompanyPersonName(String companyPersonName) {
		this.companyPersonName = companyPersonName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Integer getPeopleAmount() {
		return peopleAmount;
	}

	public void setPeopleAmount(Integer peopleAmount) {
		this.peopleAmount = peopleAmount;
	}

}
