package com.liyang.domain.ordercdd;

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
import com.liyang.domain.loan.Loan;
import com.liyang.domain.person.Person;
import com.liyang.domain.product.Product;
import com.liyang.domain.user.User;
import com.liyang.service.AbstractAuditorService;
import com.liyang.service.AbstractWorkflowService;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
@Entity
@Table(name = "ordercdd")
@Cacheable
@Info(label="车抵贷",secret="车抵贷")
public class Ordercdd extends AbstractWorkflowEntity<OrdercddWorkflow, OrdercddState, OrdercddAct, OrdercddLog,OrdercddFile> {

	@Transient
	private static AbstractWorkflowService service;
	@Transient
	private static LogRepository logRepository;

	@JoinColumn(name="product_id")
	@ManyToOne
	//这里改成Product_id
	@Info(label="产品ID",placeholder="",tip="",help="",secret="主要用于以后统计")
	private Product product;

	@JoinColumn(name = "apply_user_id")
	@OneToOne
	@Info(label="申请的用户",placeholder="",tip="",help="",secret="")
	private User user;

	@OneToOne
	@JoinColumn(name = "apply_person_id")
	@Info(label="申请的自然人",placeholder="",tip="",help="",secret="")
	private Person person;

	@ManyToOne
	@JoinColumn(name="debtor_creditcard_id")
	@Info(label="授信账户",placeholder="",tip="",help="",secret="")
	private Creditcard debtorCreditcard;

	@Column(name = "apply_real_name")
	@Info(label="真实姓名",placeholder="",tip="",help="",secret="")
	@PersonField(name="name")
	private String realName;

	@Column(name = "apply_bank_card")
	@Info(label="储蓄卡卡号",placeholder="",tip="",help="",secret="")
	@PersonField(name="applyBankCard")
	private String applyBankCard;

	@Column(name = "apply_bank_card_branch")
	@Info(label="储蓄卡支行",placeholder="",tip="",help="",secret="")
	@PersonField(name="applyBankCardBranch")
	private String applyBankCardBranch;

	@Column(name = "apply_mobile",unique = false)
	@PersonField(name="mobile")
	@Info(label="手机号",placeholder="",tip="",help="",secret="")
	private String applyMobile;

	@Column(name = "apply_register_reigionname")
	@Info(label="上牌区域",placeholder="",tip="",help="",secret="")
	private String applyRegisterReigionName;

	@Column(name = "apply_identity_no",unique =false)
	@PersonField(name="identity")
	@Info(label="身份证号",placeholder="",tip="",help="",secret="")
	private String applyIdentityNo;

	@Info(label="生日",placeholder="",tip="",help="",secret="")
	private Date birthday;

	@Info(label="性别",placeholder="",tip="",help="",secret="")
	private Integer sex;

	@Column(name = "marital_status")
	@Enumerated(EnumType.STRING)
	@Info(label="婚姻状态",placeholder="",tip="",help="",secret="")
	private MARITAL maritalStatus;

	@Info(label="电子邮件",placeholder="",tip="",help="",secret="")
	private String email;

	@Column(name="apply_enterprise_province")
	@Info(label="省份",placeholder="",tip="",help="",secret="")
	private String applyEnterpriseProvince;

	@Column(name = "personal_address")
	@Info(label="个人通讯地址",placeholder="",tip="",help="",secret="")
	private String personalAddress;

	@Column(name = "company_name")
	@Info(label="就职公司",placeholder="",tip="",help="",secret="")
	private String companyName;

	@Column(name = "company_telephone")
	@Info(label="公司固话",placeholder="",tip="",help="",secret="")
	private String companyTelephone;

	@Column(name = "company_provice")
	@Info(label="户籍",placeholder="",tip="",help="",secret="")
	private String companyProvince;

	@Column(name = "company_address")
	@Info(label="公司地址",placeholder="",tip="",help="",secret="")
	private String companyAddress;

	@Column(name = "apply_amount")
	@Info(label="借款总额",placeholder="",tip="",help="",secret="")
	private BigDecimal applyAmount;

	@Column(name = "apply_period_number")
	@Info(label="分期数",placeholder="",tip="",help="",secret="")
	private Integer applyPeriodNumber;

	@Column(name = "apply_plate_number")
	@Info(label="车牌号",placeholder="",tip="",help="",secret="")
	private String  applyPlateNumber;

	@Column(name = "apply_vehicle_model")
	@Info(label="车辆型号",placeholder="",tip="",help="",secret="")
	private String  applyVehicleModel;

	@Column(name = "apply_annual_survey_maturity")
	@Info(label="年检到期日",placeholder="",tip="",help="",secret="")
	private Date applyAnnualSurveyMaturity;
	@Lob
	@ManyToOne
	@JoinColumn(name="service_user_id")
	@Info(label = "业务员",placeholder = "",tip = "",secret = "")
	private User serviceUser;
	@Column(name="service_name")//业务员名称
	private String serviceName;
	@Column(name = "cdd_sn",length = 16,unique = true)//生成的单号
	private String cddSn;
	//表示是否已经分配
	@Column(name="is_distribution")
	private Boolean isDistribution;
	@Lob
	@Info(label="/内部备注",placeholder="",tip="",help="",secret="")
	private String comment;//内部备注
	@Transient//服务人员
	private Integer serviceId;
	@Transient//短信验证码
	private String smsCode;
	@Transient//微信appWeb传的code
	private String wechatCode;


	@OneToOne(mappedBy = "ordercdd")
	private Loan loan;

	public Loan getLoan() {
		return loan;
	}

	public Date getApplyAnnualSurveyMaturity() {
		return applyAnnualSurveyMaturity;
	}

	public void setApplyAnnualSurveyMaturity(Date applyAnnualSurveyMaturity) {
		this.applyAnnualSurveyMaturity = applyAnnualSurveyMaturity;
	}

	public String getCddSn() {
		return cddSn;
	}

	public void setCddSn(String cddSn) {
		this.cddSn = cddSn;
	}

	public String getApplyBankCard() {
		return applyBankCard;
	}

	public void setApplyBankCard(String applyBankCard) {
		this.applyBankCard = applyBankCard;
	}

	public String getApplyBankCardBranch() {
		return applyBankCardBranch;
	}

	public void setApplyBankCardBranch(String applyBankCardBranch) {
		this.applyBankCardBranch = applyBankCardBranch;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getApplyEnterpriseProvince() {
		return applyEnterpriseProvince;
	}

	public void setApplyEnterpriseProvince(String applyEnterpriseProvince) {
		this.applyEnterpriseProvince = applyEnterpriseProvince;
	}

	public String getPersonalAddress() {
		return personalAddress;
	}

	public void setPersonalAddress(String personalAddress) {
		this.personalAddress = personalAddress;
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

	public String getCompanyProvince() {
		return companyProvince;
	}

	public void setCompanyProvince(String companyProvince) {
		this.companyProvince = companyProvince;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public BigDecimal getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(BigDecimal applyAmount) {
		this.applyAmount = applyAmount;
	}

	public Integer getApplyPeriodNumber() {
		return applyPeriodNumber;
	}

	public void setApplyPeriodNumber(Integer applyPeriodNumber) {
		this.applyPeriodNumber = applyPeriodNumber;
	}

	public String getApplyPlateNumber() {
		return applyPlateNumber;
	}

	public void setApplyPlateNumber(String  applyPlateNumber) {
		this.applyPlateNumber = applyPlateNumber;
	}

	public String getApplyVehicleModel() {
		return applyVehicleModel;
	}

	public void setApplyVehicleModel(String applyVehicleModel) {
		this.applyVehicleModel = applyVehicleModel;
	}

	public MARITAL getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(MARITAL maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getApplyMobile() {
		return applyMobile;
	}

	public void setApplyMobile(String applyMobile) {
		this.applyMobile = applyMobile;
	}

	public String getApplyRegisterReigionName() {
		return applyRegisterReigionName;
	}

	public void setApplyRegisterReigionName(String applyRegisterReigionName) {
		this.applyRegisterReigionName = applyRegisterReigionName;
	}

	public String getApplyIdentityNo() {
		return applyIdentityNo;
	}

	public void setApplyIdentityNo(String applyIdentityNo) {
		this.applyIdentityNo = applyIdentityNo;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Creditcard getDebtorCreditcard() {
		return debtorCreditcard;
	}

	public void setDebtorCreditcard(Creditcard debtorCreditcard) {
		this.debtorCreditcard = debtorCreditcard;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Boolean getDistribution() {
		return isDistribution;
	}

	public void setDistribution(Boolean distribution) {
		isDistribution = distribution;
	}

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public String getWechatCode() {
		return wechatCode;
	}

	public void setWechatCode(String wechatCode) {
		this.wechatCode = wechatCode;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

//	public String getCddSn() {
//		return cddSn;
//	}

//	public void setCddSn(String cddSN) {
//		this.cddSn = cddSN;
//	}

	@Override
	@JsonIgnore
	public AbstractAuditorService<?, OrdercddState, OrdercddAct, OrdercddLog> getService() {
		return service;
	}

	@Override
	public void setService(AbstractAuditorService service) {
		Ordercdd.service=(AbstractWorkflowService)service;
	}
	@Override
	@JsonIgnore
	public OrdercddLog getLogInstance() {
		return new OrdercddLog();
	}

	@Override
	@JsonIgnore
	public LogRepository getLogRepository() {
		return logRepository;
	}

	@Override
	public void setLogRepository(LogRepository logRepository) {
		Ordercdd.logRepository=logRepository;
	}

	public User getServiceUser() {
		return serviceUser;
	}

	public void setServiceUser(User serviceUser) {
		this.serviceUser = serviceUser;
	}

	/**
	 * 未婚 已婚 丧偶 离异
	 *
	 */
	private static enum MARITAL{
		UNMARRIED,MARRIED,WIDOWED,DIVORCE
	}
}