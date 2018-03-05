package com.liyang.domain.orderwdsjsh;

import java.math.BigDecimal;

import javax.persistence.*;

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

/**
 * 
 * @author win7
 *
 */

@Entity
@Table(name = "orderwdsjsh")
@Cacheable
@Info(label="网点随借随还",secret="目前主要是贝兜做为供应商")	   
public class Orderwdsjsh extends AbstractWorkflowEntity<OrderwdsjshWorkflow, OrderwdsjshState, OrderwdsjshAct, OrderwdsjshLog,OrderwdsjshFile> {
	@Transient
	private static AbstractWorkflowService service;

	@OneToOne(targetEntity = Application.class)
	@JoinColumn(name = "application_id")
	@Info(label="应用",placeholder="",tip="",help="来源的应用",secret="主要用于以后统计")
	private Application application;
	//表示是否已经分配
	@Column(name="is_distribution")
	private Boolean isDistribution;
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
	
	@Column(name = "apply_amount")
	@Info(label="申请金额",placeholder="",tip="",help="",secret="")	
	private Integer applyAmount;

	@ManyToOne
	@JoinColumn(name="debtor_creditcard_id")
	@Info(label="授信账户",placeholder="",tip="",help="",secret="")
	private Creditcard debtorCreditcard;
	
	@Transient
	private Integer serviceId;
	@Column(name = "comfirm_amount",precision=19,scale=6)
	@Info(label="确认的金额",placeholder="",tip="",help="申请之后应该有一个审核放款额度，做为授信额度",secret="")
	private BigDecimal comfirmAmount;
	
	@Column(name="service_name")
	private String serviceName;
	
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	@Column(name = "apply_period_number")
	@Info(label="分期数",placeholder="",tip="",help="",secret="")	
	private Integer applyPeriodNumber;
	
	@Column(name = "apply_real_name")
	@Info(label="真实姓名",placeholder="",tip="",help="",secret="")	
	@PersonField(name="name")
	private String realName;
	
	@Column(name = "order_no")
	@Info(label="订单编号",placeholder="",tip="",help="",secret="")	
	private  String orderNo;
	
	
	@Column(name="zip_name")
	@Lob
	private String zipName;

	
	@Column(name="pdf_name")
	@Lob
	private String pdfName;

	@Column(name = "sign_data",columnDefinition = "text")
	private String signData;

	
	@Column(name = "apply_mobile",unique = true)
	@PersonField(name="mobile")
	@Info(label="手机号",placeholder="",tip="",help="",secret="")	
	private String applyMobile;
	
	@Column(name = "apply_identity_no",unique =true)
	@PersonField(name="identity")
	@Info(label="身份证号",placeholder="",tip="",help="",secret="")
	private String applyIdentityNo;
	
	@JoinColumn(name = "apply_enterprise_id")
	@OneToOne
	@Info(label="法人（企业）",placeholder="",tip="",help="",secret="")
	private Enterprise enterprise;
	
	@Column(name="apply_enterprise_province")
	@Info(label="省份",placeholder="",tip="",help="",secret="")
	private String applyEnterpriseProvince;

	@Column(name="apply_enterprise_city")
	@Info(label="城市",placeholder="",tip="",help="",secret="")
	private String applyEnterpriseCity;
	
	@Column(name="apply_enterprise_district")
	@Info(label="区县",placeholder="",tip="",help="",secret="")
	private String applyEnterpriseDistrict;
	
	@Column(name="apply_enterprise_town")
	@Info(label="乡镇",placeholder="",tip="",help="",secret="")
	private String applyEnterpriseTown;
	
	@Column(name = "apply_enterprise_name")
	@Info(label="网点名称",placeholder="",tip="",help="",secret="")
	private String applyEnterpriseName;

	@Column(name = "apply_enterprise_reigionname")
	@Info(label="经营区域",placeholder="",tip="",help="",secret="")
	private String applyEnterpriseReigionName;
	
	@Column(name = "apply_enterprise_address")
	@Info(label="经营地址",placeholder="",tip="",help="",secret="")
	private String applyEnterpriseAddress;//经营地点
	
	@Column(name = "apply_referrer_realname")
	@Info(label="推荐人姓名",placeholder="",tip="",help="",secret="")
	private String applyReferrerRealName;//推荐人姓名

	@Column(name = "apply_daypickexpress")
	@Info(label="日收件量",placeholder="",tip="",help="",secret="")
	private Integer applyDayPickExpress;//日收件量

	@Column(name = "apply_daypatchexpress")
	@Info(label="日派件量",placeholder="",tip="",help="",secret="")
	private Integer applyDayPatchExpress;//日派件量

	@Column(name = "apply_waybillfee")
	@Info(label="面单费",placeholder="",tip="",help="",secret="")
	private BigDecimal applyWayBillFee;//面单费

	@Lob
	@Info(label="/内部备注",placeholder="",tip="",help="",secret="")
	private String comment;//内部备注

	@Column(name = "apply_agent_band")
	@Info(label="代理品牌",placeholder="",tip="",help="",secret="")
	private String agentBrand;//代理品牌	
	
	@Column(name = "apply_disabled_remark")
	@Info(label="禁用备注",placeholder="",tip="",help="",secret="")
	private String disabledRemark;
		
	public String getAgentBrand() {
		return agentBrand;
	}

	public void setAgentBrand(String agentBrand) {
		this.agentBrand = agentBrand;
	}

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	@Lob
	@ManyToOne
	@JoinColumn(name="debtor_receive_accound_id")
	@Info(label="收款账户",placeholder="",tip="",help="",secret="")
	private Account debtorReceiveAccount;//贷款的收款账户
	@Column(name="beidou_comment")
	private String beidouComment;

	@Lob
	@ManyToOne
	@JoinColumn(name="service_user_id")
	@Info(label = "业务员",placeholder = "",tip = "",secret = "")
	private User serviceUser;
	
	public User getServiceUser() {
		return serviceUser;
	}

	public Boolean getIsDistribution() {
		return isDistribution;
	}

	public void setIsDistribution(Boolean isDistribution) {
		this.isDistribution = isDistribution;
	}

	public String getBeidouComment() {
		return beidouComment;
	}

	public void setBeidouComment(String beidouComment) {
		this.beidouComment = beidouComment;
	}

	public Boolean isDistribution() {
		return isDistribution;
	}

	public void setDistribution(boolean isDistribution) {
		this.isDistribution = isDistribution;
	}

	public String getZipName() {
		return zipName;
	}

	public void setZipName(String zipName) {
		this.zipName = zipName;
	}

	public String getPdfName() {
		return pdfName;
	}

	public void setPdfName(String pdfName) {
		this.pdfName = pdfName;
	}

	

	public String getApplyEnterpriseProvince() {
		return applyEnterpriseProvince;
	}

	public void setApplyEnterpriseProvince(String applyEnterpriseProvince) {
		this.applyEnterpriseProvince = applyEnterpriseProvince;
	}

	public String getApplyEnterpriseCity() {
		return applyEnterpriseCity;
	}

	public void setApplyEnterpriseCity(String applyEnterpriseCity) {
		this.applyEnterpriseCity = applyEnterpriseCity;
	}

	public String getApplyEnterpriseDistrict() {
		return applyEnterpriseDistrict;
	}

	public void setApplyEnterpriseDistrict(String applyEnterpriseDistrict) {
		this.applyEnterpriseDistrict = applyEnterpriseDistrict;
	}

	public String getApplyEnterpriseTown() {
		return applyEnterpriseTown;
	}

	public void setApplyEnterpriseTown(String applyEnterpriseTown) {
		this.applyEnterpriseTown = applyEnterpriseTown;
	}

	

	public Account getDebtorReceiveAccount() {
		return debtorReceiveAccount;
	}

	public void setDebtorReceiveAccount(Account debtorReceiveAccount) {
		this.debtorReceiveAccount = debtorReceiveAccount;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
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

	public Integer getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(Integer applyAmount) {
		this.applyAmount = applyAmount;
	}
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getApplyPeriodNumber() {
		return applyPeriodNumber;
	}

	public void setApplyPeriodNumber(Integer applyPeriodNumber) {
		this.applyPeriodNumber = applyPeriodNumber;
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

	public String getApplyIdentityNo() {
		return applyIdentityNo;
	}

	public void setApplyIdentityNo(String applyIdentityNo) {
		this.applyIdentityNo = applyIdentityNo;
	}

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public String getApplyEnterpriseName() {
		return applyEnterpriseName;
	}

	public void setApplyEnterpriseName(String applyEnterpriseName) {
		this.applyEnterpriseName = applyEnterpriseName;
	}

	public String getApplyEnterpriseReigionName() {
		return applyEnterpriseReigionName;
	}

	public void setApplyEnterpriseReigionName(String applyEnterpriseReigionName) {
		this.applyEnterpriseReigionName = applyEnterpriseReigionName;
	}

	public String getApplyEnterpriseAddress() {
		return applyEnterpriseAddress;
	}

	public void setApplyEnterpriseAddress(String applyEnterpriseAddress) {
		this.applyEnterpriseAddress = applyEnterpriseAddress;
	}

	public String getApplyReferrerRealName() {
		return applyReferrerRealName;
	}

	public void setApplyReferrerRealName(String applyReferrerRealName) {
		this.applyReferrerRealName = applyReferrerRealName;
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

	public BigDecimal getApplyWayBillFee() {
		return applyWayBillFee;
	}

	public void setApplyWayBillFee(BigDecimal applyWayBillFee) {
		this.applyWayBillFee = applyWayBillFee;
	}

	public BigDecimal getComfirmAmount() {
		return comfirmAmount;
	}

	public void setComfirmAmount(BigDecimal comfirmAmount) {
		this.comfirmAmount = comfirmAmount;
	}

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
		Orderwdsjsh.service= (AbstractWorkflowService) service;
	}

	@Override
	@JsonIgnore
	@Transient
	public OrderwdsjshLog getLogInstance() {
		// TODO Auto-generated method stub
		return new OrderwdsjshLog();
	}

	@Override
	@JsonIgnore
	@Transient
	public LogRepository getLogRepository() {
		// TODO Auto-generated method stub
		return logRepository;
	}
	
	public Creditcard getDebtorCreditcard() {
		return debtorCreditcard;
	}

	public void setDebtorCreditcard(Creditcard debtorCreditcard) {
		this.debtorCreditcard = debtorCreditcard;
	}

	public void setServiceUser(User serviceUser) {
		this.serviceUser = serviceUser;
	}

	
	public String getDisabledRemark() {
		return disabledRemark;
	}

	public void setDisabledRemark(String disabledRemark) {
		this.disabledRemark = disabledRemark;
	}

	@Override
	public void setLogRepository(LogRepository logRepository) {
		Orderwdsjsh.logRepository = logRepository;
	}
	//各个order里带入 DAY/WEEK/MONTH
//	public static enum PeriodCode{
//		DAY,WEEK,MONTh
//	}

	public String getSignData() {
		return signData;
	}

	public void setSignData(String signData) {
		this.signData = signData;
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