package com.liyang.domain.enterprise;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.domain.base.AbstractWorkflowEntity;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.department.Department;
import com.liyang.domain.user.User;
import com.liyang.service.AbstractAuditorService;
import com.liyang.service.AbstractWorkflowService;
//AbstractWorkflowEntity<OrderwdxydWorkflow, OrderwdxydState, OrderwdxydAct, OrderwdxydLog,OrderwdxydFile>
/**
 * 企业
 */
@Entity
@Table(name = "enterprise")
@Info(label="企业机构",placeholder="",tip="",help="",secret="")	   
public class Enterprise extends AbstractWorkflowEntity<EnterpriseWorkflow,EnterpriseState, EnterpriseAct, EnterpriseLog,EnterpriseFile>{
	@Transient
	private static AbstractWorkflowService service;

	/**
	 *
	 */
	@Transient
	private static final long serialVersionUID = 1L;

	@Transient
	private static ActRepository actRepository;
	
	@Info(label="短名称",placeholder="如哒哒控股",tip="",help="",secret="")	   
	private String alias;//别名

	@Info(label="营业执照号码",placeholder="",tip="",help="",secret="")	   
	private String identity;
	@Column(name = "tax_identity")
	@Info(label="税务证号码",placeholder="",tip="",help="",secret="")	   
	private String taxIdentity;//税号

	@Info(label="法人代表姓名",placeholder="",tip="",help="",secret="")	   
	private String representativeName;//法人代表姓名

	@Column(name="register_address")
	@Info(label="注册地址",placeholder="",tip="",help="",secret="")	   
	private String registerAddress;//注册地址

	@Column(name = "register_date")
	@Info(label="注册日期",placeholder="",tip="",help="",secret="")	   
	private Date registerDate;
	
	@Column(name = "bussiness_content")
	@Info(label="经营范围",placeholder="",tip="",help="",secret="")	   
	private String bussinessContent;//经营范园
	
	@Column(name = "vakuduty_period")
	@Info(label="经营期限",placeholder="",tip="",help="",secret="")	   
	private String vakudutyPeriod;//经营时限

	@Info(label="电话1",placeholder="",tip="",help="",secret="")	   
	private String telephone1;

	@Info(label="电话2",placeholder="",tip="",help="",secret="")	   
	private String telephone2;

	@Info(label="网站",placeholder="",tip="",help="",secret="")	   
	private String website;

	@Info(label="邮件",placeholder="",tip="",help="",secret="")	   
	private String email;

	@Info(label="说明",placeholder="",tip="",help="",secret="")	   
	private String description;
	
	@Info(label="排序",placeholder="",tip="",help="",secret="")	   
	private Integer sort;
	
	
	@Column(name = "credit_grant",precision=19,scale=6)
	@Info(label="总授信",placeholder="",tip="",help="",secret="")
	private BigDecimal creditGrant;

	public BigDecimal getCreditGrant() {
		return creditGrant;
	}

	public void setCreditGrant(BigDecimal creditGrant) {
		this.creditGrant = creditGrant;
	}

	@OneToMany(mappedBy = "enterprise",fetch=FetchType.EAGER)
	private Set<Department> departments;
	
//	@ManyToMany(mappedBy = "enterpriseSet")
//	private Set<Person> persons=new HashSet<>();

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

	public String getTaxIdentity() {
		return taxIdentity;
	}

	public void setTaxIdentity(String taxIdentity) {
		this.taxIdentity = taxIdentity;
	}

	public String getRepresentativeName() {
		return representativeName;
	}

	public void setRepresentativeName(String representativeName) {
		this.representativeName = representativeName;
	}

	public String getRegisterAddress() {
		return registerAddress;
	}

	public void setRegisterAddress(String registerAddress) {
		this.registerAddress = registerAddress;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public String getBussinessContent() {
		return bussinessContent;
	}

	public void setBussinessContent(String bussinessContent) {
		this.bussinessContent = bussinessContent;
	}

	public String getVakudutyPeriod() {
		return vakudutyPeriod;
	}

	public void setVakudutyPeriod(String vakudutyPeriod) {
		this.vakudutyPeriod = vakudutyPeriod;
	}

	public String getTelephone1() {
		return telephone1;
	}

	public void setTelephone1(String telephone1) {
		this.telephone1 = telephone1;
	}

	public String getTelephone2() {
		return telephone2;
	}

	public void setTelephone2(String telephone2) {
		this.telephone2 = telephone2;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	@Transient
	private static LogRepository logRepository;
	@ManyToMany(targetEntity = EnterpriseTag.class)
	@JoinTable(name = "enterprise_tags",joinColumns = @JoinColumn(name = "enterprise_id",referencedColumnName = "id")
	,inverseJoinColumns = @JoinColumn(name = "tag_id",referencedColumnName = "id"))
	private Set<EnterpriseTag> enterpriseTagSet=new HashSet<>();

	public void setEnterpriseTagSet(Set<EnterpriseTag> enterpriseTagSet) {
		this.enterpriseTagSet = enterpriseTagSet;
	}

	public Set<EnterpriseTag> getEnterpriseTagSet() {
		return enterpriseTagSet;
	}

	@JsonIgnore
	@Transient
	public ActRepository getActRepository() {
		// TODO Auto-generated method stub
		return actRepository;
	}

	public void setActRepository(ActRepository actRepository) {
		Enterprise.actRepository = actRepository;
		
	}
	@JsonIgnore
	@Override
	public AbstractAuditorService getService() {
		return Enterprise.service;
	}

	@Override
	public void setService(AbstractAuditorService service) {
		Enterprise.service=  (AbstractWorkflowService) service;
	}

	@Override
	@JsonIgnore
	@Transient
	public EnterpriseLog getLogInstance() {
		// TODO Auto-generated method stub
		return new EnterpriseLog();
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
		Enterprise.logRepository = logRepository;
		
	}

}