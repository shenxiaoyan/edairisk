package com.liyang.domain.company;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.creditcard.Creditcard;
import com.liyang.domain.enterprise.Enterprise;
import com.liyang.domain.ordermdbt.Ordermdbt;
import com.liyang.domain.person.Person;
import com.liyang.domain.product.Product;
import com.liyang.domain.user.User;
import com.liyang.service.AbstractAuditorService;


@Entity
@Table(name = "company")
@Cacheable
@Info(label="公司",placeholder="",tip="",help="",secret="")	   
public class Company extends AbstractAuditorEntity<CompanyState,CompanyAct,CompanyLog>{


	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = 1L;

	
	@Transient
	private static LogRepository logRepository;
	
	@Transient
	private static AbstractAuditorService service;
	
	@Column(name = "company_name",unique = true)
	@Info(label="公司名称",placeholder="",tip="",help="",secret="")
	private  String  companyName;
	
	@Column(name = "company_person_name")
	@Info(label="公司法人",placeholder="",tip="",help="",secret="")
	private  String  companyPersonName;
	
	@Column(name = "company_address")
	@Info(label="公司地址",placeholder="",tip="",help="",secret="")
	private String companyAddress;
	
	@Column(name = "company_number",unique = true)
	@Info(label="工商号码",placeholder="",tip="",help="",secret="")
	private String  companyNumber;
	
	@OneToOne
	@JoinColumn(name = "apply_user_id")
	@Info(label="申请的用户",placeholder="",tip="",help="",secret="")
	private User user;
	
	@OneToOne
	@JoinColumn(name = "apply_person_id")
	@Info(label="申请的自然人",placeholder="",tip="",help="",secret="")
	private Person person;
	                  
	@Column(name = "company_province")
	@Info(label="省",placeholder="",tip="",help="",secret="")
	private String companyProvince;
	
	@Column(name = "company_city")
	@Info(label="市",placeholder="",tip="",help="",secret="")
	private String companyCity;

	@Column(name = "company_district")
	@Info(label="区",placeholder="",tip="",help="",secret="")
	private String companyDistrict;
	
	@Column(name = "address_full_name")
	@Info(label="详细地址",placeholder="",tip="",help="",secret="")
	private String addressFullName;
	
	@Column(name = "people_amount")
	@Info(label="公司人数",placeholder="",tip="",help="",secret="")
	private int peopleAmount;
	
	public String getCompanyName() {
		return companyName;
	}



	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	

	public Person getPerson() {
		return person;
	}



	public void setPerson(Person person) {
		this.person = person;
	}



	public String getCompanyPersonName() {
		return companyPersonName;
	}



	public void setCompanyPersonName(String companyPersonName) {
		this.companyPersonName = companyPersonName;
	}



	public String getCompanyAddress() {
		return companyAddress;
	}



	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}



	public String getCompanyNumber() {
		return companyNumber;
	}



	public void setCompanyNumber(String companyNumber) {
		this.companyNumber = companyNumber;
	}

	

	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}

	
	
	
	public String getCompanyProvince() {
		return companyProvince;
	}



	public void setCompanyProvince(String companyProvince) {
		this.companyProvince = companyProvince;
	}



	public String getCompanyCity() {
		return companyCity;
	}



	public void setCompanyCity(String companyCity) {
		this.companyCity = companyCity;
	}



	public String getCompanyDistrict() {
		return companyDistrict;
	}



	public void setCompanyDistrict(String companyDistrict) {
		this.companyDistrict = companyDistrict;
	}



	public String getAddressFullName() {
		return addressFullName;
	}



	public void setAddressFullName(String addressFullName) {
		this.addressFullName = addressFullName;
	}

	


	public int getPeopleAmount() {
		return peopleAmount;
	}



	public void setPeopleAmount(int peopleAmount) {
		this.peopleAmount = peopleAmount;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	@Override
	@JsonIgnore
	@Transient
	public CompanyLog getLogInstance() {
		// TODO Auto-generated method stub
		return new CompanyLog();
	}



	@Override
	@JsonIgnore
	@Transient
	public LogRepository getLogRepository() {
		return logRepository;
	}
	@Override
	public void setLogRepository(LogRepository logRepository) {
		Company.logRepository = logRepository;
		
	}


	@JsonIgnore
	@Override
	@Transient
	public AbstractAuditorService getService() {
		// TODO Auto-generated method stub
		return service;
	}



	@Override
	public void setService(AbstractAuditorService service) {
		// TODO Auto-generated method stub
		Company.service = service;
	}



	














}