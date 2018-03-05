package com.liyang.domain.creditrepayplan;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.domain.base.AbstractWorkflowEntity;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.creditcard.Creditcard;
import com.liyang.domain.creditrepayment.Creditrepayment;
import com.liyang.domain.loan.Loan;
import com.liyang.domain.user.User;
import com.liyang.service.AbstractAuditorService;
import com.liyang.service.AbstractWorkflowService;

/**
 * 外部账户
 */
@Entity
@Table(name = "creditrepayplan")
@Info(label="信用卡还款计划")	
public class Creditrepayplan extends AbstractWorkflowEntity<CreditrepayplanWorkflow, CreditrepayplanState, CreditrepayplanAct, CreditrepayplanLog,CreditrepayplanFile>{

	@Transient
	private static AbstractWorkflowService service;

	@Transient
	private static LogRepository logRepository;
	
	
	@ManyToOne
	@JoinColumn(name="loan_id")
	private Loan loan;

	@ManyToOne
	@JoinColumn(name="creditcard_id")
	private Creditcard creditcard;
	
	
	@OneToMany(mappedBy = "creditrepayplan")
	private Set<Creditrepayment> creditrepayment;
	
	@Column(name="principal",precision=19,scale=6)
	@Info(label="本金",placeholder="",tip="",help="",secret="")	
	private BigDecimal principal;
	
	@Info(label="当前期数",placeholder="",tip="",help="",secret="")
	private Integer  term;
	
	@Column(name="interest",precision=19,scale=6)
	@Info(label="利息",placeholder="",tip="",help="",secret="")	
	private BigDecimal interest;

	@Column(name="fees",precision=19,scale=6)
	@Info(label="各种费用总和",placeholder="",tip="",help="",secret="")	
	private BigDecimal fees;

	@Column(name="is_overdue")
	private Integer isOverdue;//是否逾期

	@Column(name = "overdue_days")//逾期天数
	@Info(label="逾期天数",placeholder="",tip="",help="",secret="")
	private Integer overdueDays=0;
	@Column(name = "overdue_amount")//逾期金额
	@Info(label="逾期金额",placeholder="",tip="",help="",secret="")
	private BigDecimal overdueAmount=BigDecimal.valueOf(0);
	@Column(name = "account_settle")//提前结清
	@Info(label="提前结清",placeholder="",tip="",help="",secret="")
	private BigDecimal accountSettle;
	@Column(name = "left_amount")//剩下还款
	@Info(label="剩下还款",placeholder="",tip="",help="",secret="")
	private BigDecimal leftAmount;
	@Column(name="debtor_repayment_date")//借方正常还款日期
	@Info(label="借方正常还款日期",placeholder="",tip="",help="",secret="")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private java.util.Date debtorRepaymentDate;
	
	@Column(name = "punishinterest", precision = 19, scale = 6)
	@Info(label = "罚息", placeholder = "", tip = "", help = "", secret = "")
	private BigDecimal punishinterest;
	
	@Column(name="debtor_extented_repayment_date")//借方宽限还款日期
	@Info(label="借方宽限还款日期",placeholder="",tip="",help="",secret="时间上大于正常还款日期，该日期以内还款免逾期")
	private java.util.Date debtorExtentedRepaymentDate;

	@Column(name="creditor_repayment_date")
	@Info(label="贷方还款借款日期",placeholder="",tip="",help="",secret="")
	private java.util.Date creditorRepaymentDate;
		

	@Lob
	@Info(label="其它相关信息",placeholder="",tip="",help="例如第三方还款返回的信息，JSON序列化存储",secret="")
	private String information;

	@Column(name = "order_sn")//订单号,就是ordercdd.cdd_sn;
	@Info(label="订单号",placeholder="",tip="",help="",secret="")
	private String orderSn;
	@Column(name = "loan_sn")//借款单号,就是loan.loan_sn;
	@Info(label="借款单号",placeholder="",tip="",help="",secret="")
	private String loanSn;
	@Column(name = "plan_sn")//还款计划的单号
	@Info(label="还款计划的单号",placeholder="",tip="",help="",secret="")
	private String planSn;
	@Info(label = "借款人手机号")
	private String debtorMobile;
	@Info(label = "借款人姓名")
	private String debtorName;


	@ManyToOne
	@JoinColumn(name="service_user_id")
	@Info(label = "业务员id",placeholder = "",tip = "",secret = "")///李斌说的需要
	private User serviceUser;
	@Transient
	private static final long serialVersionUID = 1L;
	@JsonIgnore
	@Transient
	public AbstractAuditorService getService() {
		// TODO Auto-generated method stub
		return service;
	}
	
	
	
	public Integer getTerm() {
		return term;
	}



	public void setTerm(Integer term) {
		this.term = term;
	}



	public String getDebtorName() {
		return debtorName;
	}

	public void setDebtorName(String debtorName) {
		this.debtorName = debtorName;
	}

	public String getDebtorMobile() {
		return debtorMobile;
	}

	public void setDebtorMobile(String debtorMobile) {
		this.debtorMobile = debtorMobile;
	}

	public Integer getOverdueDays() {
		return overdueDays;
	}

	public void setOverdueDays(Integer overdueDays) {
		this.overdueDays = overdueDays;
	}

	public BigDecimal getOverdueAmount() {
		return overdueAmount;
	}

	public void setOverdueAmount(BigDecimal overdueAmount) {
		this.overdueAmount = overdueAmount;
	}

	public BigDecimal getAccountSettle() {
		return accountSettle;
	}

	public void setAccountSettle(BigDecimal accountSettle) {
		this.accountSettle = accountSettle;
	}

	public BigDecimal getLeftAmount() {
		return leftAmount;
	}

	public void setLeftAmount(BigDecimal leftAmount) {
		this.leftAmount = leftAmount;
	}

	public User getServiceUser() {
		return serviceUser;
	}

	public void setServiceUser(User serviceUser) {
		this.serviceUser = serviceUser;
	}

	public String getPlanSn() {
		return planSn;
	}

	public void setPlanSn(String planSn) {
		this.planSn = planSn;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getLoanSn() {
		return loanSn;
	}

	public void setLoanSn(String loanSn) {
		this.loanSn = loanSn;
	}

	public Integer getIsOverdue() {
		return isOverdue;
	}

	public void setIsOverdue(Integer isOverdue) {
		this.isOverdue = isOverdue;
	}

	

	
	public Creditcard getCreditcard() {
		return creditcard;
	}

	public void setCreditcard(Creditcard creditcard) {
		this.creditcard = creditcard;
	}

	public BigDecimal getFees() {
		return fees;
	}

	public void setFees(BigDecimal fees) {
		this.fees = fees;
	}

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
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


	public java.util.Date getDebtorRepaymentDate() {
		return debtorRepaymentDate;
	}

	public void setDebtorRepaymentDate(java.util.Date debtorRepaymentDate) {
		this.debtorRepaymentDate = debtorRepaymentDate;
	}

	
	

	public java.util.Date getDebtorExtentedRepaymentDate() {
		return debtorExtentedRepaymentDate;
	}

	public void setDebtorExtentedRepaymentDate(java.util.Date debtorExtentedRepaymentDate) {
		this.debtorExtentedRepaymentDate = debtorExtentedRepaymentDate;
	}

	public java.util.Date getCreditorRepaymentDate() {
		return creditorRepaymentDate;
	}

	public void setCreditorRepaymentDate(java.util.Date creditorRepaymentDate) {
		this.creditorRepaymentDate = creditorRepaymentDate;
	}



	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public Set<Creditrepayment> getCreditrepayment() {
		return creditrepayment;
	}

	public void setCreditrepayment(Set<Creditrepayment> creditrepayment) {
		this.creditrepayment = creditrepayment;
	}

	@Override
	public void setService(AbstractAuditorService service) {
		Creditrepayplan.service = (AbstractWorkflowService) service;

	}
	@JsonIgnore
	@Transient
	@Override
	public CreditrepayplanLog getLogInstance() {
		return new CreditrepayplanLog();
	}
	@JsonIgnore
	@Transient
	@Override
	public LogRepository getLogRepository() {
		return new Creditrepayplan().getLogRepository();
	}
	
	@Override
	public void setLogRepository(LogRepository logRepository) {
		Creditrepayplan.logRepository=logRepository;
	}
}