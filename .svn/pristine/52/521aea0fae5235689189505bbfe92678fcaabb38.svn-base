package com.liyang.domain.loan;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.domain.bankcard.Bankcard;
import com.liyang.domain.base.AbstractWorkflowEntity;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.capitalproduct.Capitalproduct.RepaymentMethodCode;
import com.liyang.domain.creditcard.Creditcard;
import com.liyang.domain.creditrepayment.Creditrepayment;
import com.liyang.domain.creditrepayplan.Creditrepayplan;
import com.liyang.domain.enterprise.Enterprise;
//import com.liyang.domain.orderwdsjsh.Orderwdsjsh.PeriodCode;
import com.liyang.domain.ordercdd.Ordercdd;
import com.liyang.domain.person.Person;
import com.liyang.domain.punishinterestrule.Punishinterestrule;
import com.liyang.domain.user.User;
import com.liyang.service.AbstractAuditorService;
import com.liyang.service.AbstractWorkflowService;


/**
 * 
 * @author win7
 *
 */
@Entity
@Table(name = "loan")
@Cacheable
@Info(label="贷款",placeholder="",tip="",help="",secret="")
public class Loan extends AbstractWorkflowEntity<LoanWorkflow, LoanState, LoanAct, LoanLog,LoanFile> {
	@Transient
	private static AbstractWorkflowService service;
	@Transient
	private static ActRepository actRepository;
	

	@JoinColumn(name="creditcard_id")
	@ManyToOne
	@Info(label="所属授信账户",placeholder="",tip="",help="",secret="")
	private Creditcard creditcard;


	@Enumerated(EnumType.STRING)
	@Column(name="repayment_method_code")
	@Info(label="还款方式",placeholder="",tip="",help="",secret="")
	private RepaymentMethodCode repaymentMethodCode;
	
	@Column(name = "use_date")
	@Info(label="使用日期",placeholder="",tip="",help="",secret="")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date useDate; 
	
	@Column(name = "apply_date")
	@Info(label="申请日期",placeholder="",tip="",help="",secret="")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date applyDate;
	
	@Column(name = "order_no")
	@Info(label="订单编号",placeholder="",tip="",help="",secret="")	
	private  String orderNo;
	
	@Column(name = "principal",precision=19,scale=6)
	@Info(label="贷款本金",placeholder="",tip="",help="",secret="")
	private BigDecimal principal;
	
	@Column(name = "total_interest")
	@Info(label="总利息",placeholder="",tip="",help="",secret="")
	private BigDecimal  totalInterest;
	
	
	@Info(label="本金是否逾期",placeholder="",tip="",help="",secret="")
	private Integer overdue;
	
	@Column(name = "overdue_interest")
	@Info(label="利息是否逾期",placeholder="",tip="",help="",secret="")
	private Integer overdueInterest;
	
	@Column(name = "remain_amount")
	@Info(label="剩余应还本息",placeholder="",tip="",help="",secret="")
	private BigDecimal  remainAmount;

	@Column(name="period_code")
	@Enumerated(EnumType.STRING)
	@Info(label="贷款分期时间单位",placeholder="",tip="",help="",secret="")
	private PeriodCode periodCode;
	//	private PeriodCode periodCode;

	@Column(name="period_number")
	@Info(label="贷款分期期数",placeholder="",tip="",help="",secret="")
	private Integer periodNumber;

	@Column(name="debtor_interest",precision=19,scale=6)
	@Info(label="借方利率",placeholder="",tip="",help="product来",secret="")
	private BigDecimal debtorInterest;
	
	@Column(name="store_interest",precision=19,scale=6)
	@Info(label="给门店利率",placeholder="",tip="",help="product来",secret="")
	private BigDecimal storeInterest;
	
	@Column(name="creditor_interest",precision=19,scale=6)
	@Info(label="贷方供应利率",placeholder="",tip="",help="capitalproduct来",secret="")
	private BigDecimal creditorInterest;
	
	@JoinColumn(name="debtor_receive_bankcard_id")
	@ManyToOne
	@Info(label="借方收款账户",placeholder="",tip="",help="资方汇款给这个账户",secret="")
	private Bankcard debtorReceiveBankcard;
	
	
	@Column(name="creditor_repayment_day")
	@Info(label="还给资方的日期",placeholder="",tip="",help="",secret="")
	private Integer creditorRepaymentDay;
	
	@Column(name="debtor_repayment_day")
	@Info(label="借方还款的时间",placeholder="",tip="",help="",secret="")	
	private Integer debtorRepaymentDay;

	@Column(name="debtor_extented_repayment_days")
	@Info(label="借方还款宽限日期数",placeholder="",tip="",help="",secret="注意是宽限的日期数量，即在还款日当天加上这个数值是宽限数")	
	private Integer debtorExtentedRepaymentDays;	
	
	@Lob
	@Info(label="说明",placeholder="",tip="",help="",secret="")	
	private String description;

	@Lob
	@Info(label="内部备注",placeholder="",tip="",help="",secret="")	
	private String comment;

	@JoinColumn(name="punishinterestrule_id")
	@ManyToOne
	@Info(label="罚息规则",placeholder="",tip="",help="",secret="罚息规则那边，一旦创建，无法变更！")
	private Punishinterestrule punishinterestrule;

	@ManyToOne
	@JoinColumn(name="debtor_user_id")
	@Info(label="贷款人",placeholder="",tip="",help="",secret="")	
	private User debtorUser;//借方执行人

	@ManyToOne
	@JoinColumn(name="debtor_person_id")
	@Info(label="贷款自然人（个人）",placeholder="",tip="",help="",secret="")	
	private Person debtorPerson;
	
	@ManyToOne
	@JoinColumn(name="debtor_enterprise_id")
	@Info(label="贷款法人（企业）",placeholder="",tip="",help="",secret="")	
	private Enterprise debtorEnterprise;

	@OneToMany(mappedBy = "loan")
	private Set<Creditrepayplan> creditrepayplans;
	
	@Lob
	@Info(label="其它相关信息",placeholder="",tip="",help="例如第三方还款返回的信息，JSON序列化存储",secret="")
	private String information;
//	private String beidou;

	@Column(name = "creditor_loan_sn")
	@Info(label="资方贷款单号",placeholder="",tip="",help="",secret="")
	private String creditorLoanSn;


	@Column(name = "service_user_name")
	@Info(label="业务员姓名",placeholder="",tip="",help="",secret="")
	private String serviceUserName;

	@ManyToOne
	@JoinColumn(name="service_user_id")
	@Info(label = "业务员id",placeholder = "",tip = "",secret = "")
	private User serviceUser;
	@Column(name = "loan_sn",length = 16,unique = true)//生成的单号
	private String loanSn;
	@OneToOne
	@JoinColumn(name = "ordercdd_id")
	private Ordercdd ordercdd;
	@Info(label = "借款人姓名")
	private String debtorName;
	
	@Column(name = "loan_type")
	@Enumerated(EnumType.STRING)
	@Info(label = "借款类型", placeholder = "", tip = "", help = "", secret = "")
	private LoanType loanType;
	
	@Column(name = "purchasing_unit")
	@Info(label = "采购单位", placeholder = "", tip = "", help = "", secret = "")
	private String  purchasingUnit;

	@Column(name = "debtor_reality_amount")
	@Info(label = "向客户放款的金额")
	private BigDecimal debtorRealityAmount;
	@Column(name = "one_time_fee")
	@Info(label = "一次性收费")
	private BigDecimal oneTimeFee;
	@Column(name = "retreat_fee")
	@Info(label = "可退收费")
	private BigDecimal retreatFee;
	

	public String getPurchasingUnit() {
		return purchasingUnit;
	}

	public void setPurchasingUnit(String purchasingUnit) {
		this.purchasingUnit = purchasingUnit;
	}

	public Integer getOverdueInterest() {
		return overdueInterest;
	}

	public void setOverdueInterest(Integer overdueInterest) {
		this.overdueInterest = overdueInterest;
	}

	public BigDecimal getTotalInterest() {
		return totalInterest;
	}

	public void setTotalInterest(BigDecimal totalInterest) {
		this.totalInterest = totalInterest;
	}

	

	public Integer getOverdue() {
		return overdue;
	}

	public void setOverdue(Integer overdue) {
		this.overdue = overdue;
	}

	public BigDecimal getRemainAmount() {
		return remainAmount;
	}

	public void setRemainAmount(BigDecimal remainAmount) {
		this.remainAmount = remainAmount;
	}

	public Date getUseDate() {
		return useDate;
	}

	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}

	

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public BigDecimal getRetreatFee() {
		return retreatFee;
	}

	public void setRetreatFee(BigDecimal retreatFee) {
		this.retreatFee = retreatFee;
	}

	public BigDecimal getOneTimeFee() {
		return oneTimeFee;
	}

	public void setOneTimeFee(BigDecimal oneTimeFee) {
		this.oneTimeFee = oneTimeFee;
	}

	public BigDecimal getDebtorRealityAmount() {
		return debtorRealityAmount;
	}

	public void setDebtorRealityAmount(BigDecimal debtorRealityAmount) {
		this.debtorRealityAmount = debtorRealityAmount;
	}

	public String getDebtorName() {
		return debtorName;
	}

	public void setDebtorName(String debtorName) {
		this.debtorName = debtorName;
	}

	public LoanType getLoanType() {
		return loanType;
	}

	public void setLoanType(LoanType loanType) {
		this.loanType = loanType;
	}

	public String getLoanSn() {
		return loanSn;
	}

	public void setLoanSn(String loanSn) {
		this.loanSn = loanSn;
	}

	public User getServiceUser() {
		return serviceUser;
	}

	public void setServiceUser(User serviceUser) {
		this.serviceUser = serviceUser;
	}

	@Transient
	private static LogRepository logRepository;

	public Ordercdd getOrdercdd() {
		return ordercdd;
	}

	public void setOrdercdd(Ordercdd ordercdd) {
		this.ordercdd = ordercdd;
	}

	public Punishinterestrule getPunishinterestrule() {
		return punishinterestrule;
	}

	public void setPunishinterestrule(Punishinterestrule punishinterestrule) {
		this.punishinterestrule = punishinterestrule;
	}


	public RepaymentMethodCode getRepaymentMethodCode() {
		return repaymentMethodCode;
	}


	public void setRepaymentMethodCode(RepaymentMethodCode repaymentMethodCode) {
		this.repaymentMethodCode = repaymentMethodCode;
	}


	public Set<Creditrepayplan> getCreditrepayplans() {
		return creditrepayplans;
	}

	public PeriodCode getPeriodCode() {
		return periodCode;
	}

	public void setPeriodCode(PeriodCode periodCode) {
		this.periodCode = periodCode;
	}




	public Integer getPeriodNumber() {
		return periodNumber;
	}

	public void setPeriodNumber(Integer periodNumber) {
		this.periodNumber = periodNumber;
	}

	public BigDecimal getDebtorInterest() {
		return debtorInterest;
	}

	public void setDebtorInterest(BigDecimal debtorInterest) {
		this.debtorInterest = debtorInterest;
	}

	public BigDecimal getStoreInterest() {
		return storeInterest;
	}

	public void setStoreInterest(BigDecimal storeInterest) {
		this.storeInterest = storeInterest;
	}


	public BigDecimal getCreditorInterest() {
		return creditorInterest;
	}

	public void setCreditorInterest(BigDecimal creditorInterest) {
		this.creditorInterest = creditorInterest;
	}

	




	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public User getDebtorUser() {
		return debtorUser;
	}

	public void setDebtorUser(User debtorUser) {
		this.debtorUser = debtorUser;
	}

	public Person getDebtorPerson() {
		return debtorPerson;
	}

	public void setDebtorPerson(Person debtorPerson) {
		this.debtorPerson = debtorPerson;
	}

	public Enterprise getDebtorEnterprise() {
		return debtorEnterprise;
	}

	public void setDebtorEnterprise(Enterprise debtorEnterprise) {
		this.debtorEnterprise = debtorEnterprise;
	}


	public Creditcard getCreditcard() {
		return creditcard;
	}

	public void setCreditcard(Creditcard creditcard) {
		this.creditcard = creditcard;
	}

	public BigDecimal getPrincipal() {
		return principal;
	}

	public void setPrincipal(BigDecimal principal) {
		this.principal = principal;
	}

	public Bankcard getDebtorReceiveBankcard() {
		return debtorReceiveBankcard;
	}

	public void setDebtorReceiveBankcard(Bankcard debtorReceiveBankcard) {
		this.debtorReceiveBankcard = debtorReceiveBankcard;
	}

	public Integer getCreditorRepaymentDay() {
		return creditorRepaymentDay;
	}

	public void setCreditorRepaymentDay(Integer creditorRepaymentDay) {
		this.creditorRepaymentDay = creditorRepaymentDay;
	}

	public Integer getDebtorRepaymentDay() {
		return debtorRepaymentDay;
	}

	public void setDebtorRepaymentDay(Integer debtorRepaymentDay) {
		this.debtorRepaymentDay = debtorRepaymentDay;
	}



	public Integer getDebtorExtentedRepaymentDays() {
		return debtorExtentedRepaymentDays;
	}

	public void setDebtorExtentedRepaymentDays(Integer debtorExtentedRepaymentDays) {
		this.debtorExtentedRepaymentDays = debtorExtentedRepaymentDays;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}




	public String getCreditorLoanSn() {
		return creditorLoanSn;
	}

	public void setCreditorLoanSn(String creditorLoanSn) {
		this.creditorLoanSn = creditorLoanSn;
	}


	public String getServiceUserName() {
		return serviceUserName;
	}

	public void setServiceUserName(String serviceUserName) {
		this.serviceUserName = serviceUserName;
	}

	@Override
	@JsonIgnore
	@Transient
	public LoanLog getLogInstance() {
		// TODO Auto-generated method stub
		return new LoanLog();
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
		Loan.logRepository = logRepository;
	}

	@Override
	@JsonIgnore
	@Transient
	public AbstractAuditorService getService() {
		// TODO Auto-generated method stub
		return service;
	}

	@Override
	public void setService(AbstractAuditorService service) {
		Loan.service = (AbstractWorkflowService) service;
		
	}




	public static enum PeriodCode{
		@Info(label="一次性",placeholder="",tip="",help="",secret="ONE")
		ONE,
		@Info(label="天",placeholder="",tip="",help="",secret="DAY")
		DAY,
		@Info(label="周",placeholder="",tip="",help="",secret="WEEK")
		WEEK,
		@Info(label="月",placeholder="",tip="",help="",secret="MONTH")
		MONTH
	}

	//放款类型
	public static enum  LoanType{
		ORDERCDD,ORDERMDBT
	}
	
}