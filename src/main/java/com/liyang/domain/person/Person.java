package com.liyang.domain.person;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.annotation.PersonField;
import com.liyang.domain.base.AbstractWorkflowEntity;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.creditcard.Creditcard;
import com.liyang.domain.user.User;
import com.liyang.service.AbstractAuditorService;
import com.liyang.service.AbstractWorkflowService;

/**
 * 人
 */
@Entity
@Table(name = "person")
//AbstractWorkflowEntity<OrderwdxydWorkflow, OrderwdxydState, OrderwdxydAct, OrderwdxydLog,OrderwdxydFile>
@Info(label="自然人",placeholder="",tip="",help="",secret="")
public class Person extends AbstractWorkflowEntity<PersonWorkflow,PersonState, PersonAct, PersonLog,PersonFile>{

	@Transient
	private static AbstractWorkflowService service;
	/**
	 *
	 */
	@Transient
	private static final long serialVersionUID = 1L;

	@Info(label="别名",placeholder="",tip="",help="",secret="")
	private String alias;

	@Column(name = "identity",length = 18,unique=true)
	@PersonField(name="identity")
	@Info(label="身份证号",placeholder="",tip="",help="",secret="")
	private String identity;//身份证
	
	@Info(label="生日",placeholder="",tip="",help="",secret="")
	private Date birthday;

	@Info(label="教育程度",placeholder="",tip="",help="",secret="")
	//要改为枚举型
	private String education;

	@PersonField(name="name")
	@Info(label="姓名",placeholder="",tip="",help="",secret="")
	private String name;
	
	@Column(name = "marital_status")
	@Enumerated(EnumType.STRING)
	@Info(label="婚姻状态",placeholder="",tip="",help="",secret="")
	private MARITAL maritalStatus;
	
	@Info(label="主手机号",placeholder="",tip="",help="",secret="")
	private String mobile1;

	@Info(label="备手机号",placeholder="",tip="",help="",secret="")
	private String mobile2;

	@Column(name = "telephone",unique = true)
	@PersonField(name="mobile")
	@Info(label="固定电话",placeholder="",tip="",help="",secret="")
	private String telephone;
	
	@Info(label="邮件",placeholder="",tip="",help="",secret="")
	private String email;

	@Column(name = "QQ")
	@Info(label="QQ号",placeholder="",tip="",help="",secret="")
	private String qq;
	
	@Column(name = "home_address")
	@Info(label="家庭住址",placeholder="",tip="",help="",secret="")
	private String homeAddress;
	
	@Column(name = "home_provice")
	@Info(label="省份",placeholder="",tip="",help="",secret="")
	private String homeProvince;
	
	@Column(name = "home_city")
	@Info(label="城市",placeholder="",tip="",help="",secret="")
	private String homeCity;

	@Column(name = "home_district")
	@Info(label="区县",placeholder="",tip="",help="",secret="")
	private String homeDistrict;
	
	
	@Column(name = "home_town")
	@Info(label="乡镇",placeholder="",tip="",help="",secret="")
	private String homeTown;

	
	@Column(name = "company_name")
	@Info(label="就职公司",placeholder="",tip="",help="",secret="")
	private String companyName;
	
	@Column(name = "company_telephone")
	@Info(label="公司固话",placeholder="",tip="",help="",secret="")
	private String companyTelephone;
	
	
	@Column(name = "company_provice")
	@Info(label="省份",placeholder="",tip="",help="",secret="")
	private String companyProvince;
	
	@Column(name = "company_city")
	@Info(label="城市",placeholder="",tip="",help="",secret="")
	private String companyCity;

	@Column(name = "company_district")
	@Info(label="区县",placeholder="",tip="",help="",secret="")
	private String companyDistrict;
	
	
	@Column(name = "company_town")
	@Info(label="乡镇",placeholder="",tip="",help="",secret="")
	private String companyTown;
	
	@Column(name = "company_address")
	@Info(label="公司地址",placeholder="",tip="",help="",secret="")
	private String companyAddress;

	@Column(name = "job_grade")
	@Info(label="职级",placeholder="",tip="",help="",secret="")
	//要改为枚举型
	private String jobGrade;
	
	@Column(name = "letter_to")
	@Info(label="邮寄地址",placeholder="",tip="",help="",secret="")
	//要改为枚举型，COMPANY OR HOME
	private String letterTo;
	
	@Lob
	@Info(label="说明",placeholder="",tip="",help="",secret="")
	private String description;

	@Info(label="排序",placeholder="",tip="",help="",secret="")
	private Integer sort;
	
	
	@Column(name = "credit_grant",precision=19,scale=6)
	@Info(label="总授信",placeholder="",tip="",help="",secret="")
	private BigDecimal creditGrant;
	
	@Transient 
	private static ActRepository actRepository;
	
	@OneToMany(mappedBy = "person")
	private Set<Creditcard> creditcards;

	@OneToMany(mappedBy = "person")
	private Set<User> users;
	
	@Column(name = "account_status")
	@Info(label="账号状态",placeholder="",tip="",help="",secret="")
	private String  accountStatus;
	
	@Column(name = "abnormal_remark")
	@Info(label="异常备注",placeholder="",tip="",help="",secret="")
	private String abnormalRemark;
	
//	@ManyToMany
//	@JoinTable(name = "enterprise_persons",joinColumns = @JoinColumn(name = "person_id"),
//	inverseJoinColumns = @JoinColumn(name = "enterprise_id"))
//	
	
//	private Set<Enterprise> enterpriseSet=new HashSet<>();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	@Transient
	private static LogRepository logRepository;
	@ManyToMany(targetEntity = PersonTag.class)
	@JoinTable(name="person_tags",joinColumns = @JoinColumn(name="person_id",referencedColumnName = "id")
		,inverseJoinColumns = @JoinColumn(name = "tag_id",referencedColumnName = "id"))
	private Set<PersonTag> personTags=new HashSet<>();

	public void setPersonTags(Set<PersonTag> personTags) {
		this.personTags = personTags;
	}

	public Set<PersonTag> getPersonTags() {
		return personTags;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public MARITAL getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(MARITAL maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getMobile1() {
		return mobile1;
	}

	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}

	public String getMobile2() {
		return mobile2;
	}

	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return qq;
	}

	
	public String getHomeDistrict() {
		return homeDistrict;
	}

	public void setHomeDistrict(String homeDistrict) {
		this.homeDistrict = homeDistrict;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getHomeProvince() {
		return homeProvince;
	}

	public void setHomeProvince(String homeProvince) {
		this.homeProvince = homeProvince;
	}

	public String getHomeCity() {
		return homeCity;
	}

	public void setHomeCity(String homeCity) {
		this.homeCity = homeCity;
	}




	public String getHomeTown() {
		return homeTown;
	}

	public void setHomeTown(String homeTown) {
		this.homeTown = homeTown;
	}


	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyTelephone() {
		return companyTelephone;
	}

	public void setCompanyTelephone(String companyTelephone) {
		this.companyTelephone = companyTelephone;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getJobGrade() {
		return jobGrade;
	}

	public void setJobGrade(String jobGrade) {
		this.jobGrade = jobGrade;
	}

	public String getLetterTo() {
		return letterTo;
	}

	public void setLetterTo(String letterTo) {
		this.letterTo = letterTo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}


	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getCompanyProvince() {
		return companyProvince;
	}

	public void setCompanyProvince(String companyProvince) {
		this.companyProvince = companyProvince;
	}

	public String getCompanyTown() {
		return companyTown;
	}

	public void setCompanyTown(String companyTown) {
		this.companyTown = companyTown;
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

	public BigDecimal getCreditGrant() {
		return creditGrant;
	}

	public void setCreditGrant(BigDecimal creditGrant) {
		this.creditGrant = creditGrant;
	}

	@JsonIgnore
	@Transient
	public ActRepository getActRepository() {
		// TODO Auto-generated method stub
		return actRepository;
	}

	public void setActRepository(ActRepository actRepository) {
		Person.actRepository = actRepository;
		
	}
	@JsonIgnore
	@Override
	public AbstractAuditorService getService() {
		return service;
	}

	@Override
	public void setService(AbstractAuditorService service) {
		Person.service=(AbstractWorkflowService) service;
	}

	@Override
	@JsonIgnore
	@Transient
	public PersonLog getLogInstance() {
		// TODO Auto-generated method stub
		return new PersonLog();
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
		Person.logRepository = logRepository;
		
	}
	
	public String getAbnormalRemark() {
		return abnormalRemark;
	}

	public void setAbnormalRemark(String abnormalRemark) {
		this.abnormalRemark = abnormalRemark;
	}


	/**
	 * 未婚 已婚 丧偶 离异
	 *
	 */
	private static enum MARITAL{
		UNMARRIED,MARRIED,WIDOWED,DIVORCE
	}

	

}