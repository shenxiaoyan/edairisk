package com.liyang.domain.creditrepayment;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.domain.base.AbstractWorkflowEntity;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.creditcard.Creditcard;
import com.liyang.domain.creditcard.CreditcardChangelog;
import com.liyang.domain.creditrepayplan.Creditrepayplan;
import com.liyang.domain.loan.Loan;
import com.liyang.domain.loan.Loan.LoanType;
import com.liyang.service.AbstractAuditorService;
import com.liyang.service.AbstractWorkflowService;

/**
 * 外部账户
 */
@Entity
@Table(name = "creditrepayment")
@Info(label = "信用卡还款单")
public class Creditrepayment extends
		AbstractWorkflowEntity<CreditrepaymentWorkflow, CreditrepaymentState, CreditrepaymentAct, CreditrepaymentLog, CreditrepaymentFile> {

	@Transient
	private static AbstractWorkflowService service;

	@Transient
	private static LogRepository logRepository;

	@ManyToOne
	@JoinColumn(name = "creditcard_id")
	private Creditcard creditcard;

	@ManyToOne
	@JoinColumn(name = "loan_id")
	private Loan loan;

	@ManyToOne
	@JoinColumn(name = "creditrepayplan_id")
	private Creditrepayplan creditrepayplan;

	@Column(name = "principal", precision = 19, scale = 6)
	@Info(label = "本金", placeholder = "", tip = "", help = "", secret = "")
	private BigDecimal principal;

	@Column(name = "interest", precision = 19, scale = 6)
	@Info(label = "利息", placeholder = "", tip = "", help = "", secret = "")
	private BigDecimal interest;

	@Column(name = "fees", precision = 19, scale = 6)
	@Info(label = "各种费用总和", placeholder = "", tip = "", help = "", secret = "")
	private BigDecimal fees;
	
	@Column(name = "pay_amount")
	@Info(label = "实际还款金额", placeholder = "", tip = "", help = "", secret = "")
	private BigDecimal payAmount;

	@Column(name = "punishinterest", precision = 19, scale = 6)
	@Info(label = "罚息", placeholder = "", tip = "", help = "", secret = "")
	private BigDecimal punishinterest;

	@Column(name = "total", precision = 19, scale = 6)
	private BigDecimal total;

	@Column(name = "is_overdue")
	private Integer isOverdue;// 是否逾期

	@Column(name = "debtor_actual_repayment_date")
	@Info(label = "借方实际还款日期", placeholder = "", tip = "", help = "", secret = "")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date debtorActualRepaymentDate;

	@OneToOne
	@JoinColumn(name = "creditcard_changlog_id")
	private CreditcardChangelog creditcardChangelog;

	@Column(name = "creditor_repayment_sn")
	@Info(label = "资方还款单号", placeholder = "", tip = "", help = "通常是资方接口返回来的", secret = "")
	private String creditorRepaymentSn;

	@Lob
	@Info(label = "其它相关信息", placeholder = "", tip = "", help = "例如第三方还款返回的信息，JSON序列化存储", secret = "")
	private String information;

	@Transient
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@Transient
	public AbstractAuditorService getService() {
		// TODO Auto-generated method stub
		return service;
	}

	@Column(name = "debtor")
	@Info(label = "还款人的名字") // 前段显示列表用
	private String debtorName;
	@Column(name = "order_sn") // 订单号,就是ordercdd.cdd_sn;
	@Info(label = "订单号", placeholder = "", tip = "", help = "", secret = "")
	private String orderSn;
	@Column(name = "loan_sn") // 借款单号,就是loan.loan_sn;
	@Info(label = "借款单号", placeholder = "", tip = "", help = "", secret = "")
	private String loanSn;
	@Column(name = "plan_sn") // 还款计划的单号
	@Info(label = "还款计划的单号", placeholder = "", tip = "", help = "", secret = "")
	private String planSn;
	
	@Column(name = "order_no")
	@Info(label = "还款单号", placeholder = "", tip = "", help = "", secret = "")
	private String orderNo;
	
	@Column(name = "debtor_repayment_date") // 借方正常还款日期
	@Info(label = "预计还款日期", placeholder = "", tip = "", help = "", secret = "")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date debtorRepaymentDate;

	@Column(name = "repayment_type")
	@Enumerated(EnumType.STRING)
	@Info(label = "还款类型", placeholder = "", tip = "", help = "", secret = "")
	private RepaymentType repaymentType;
	
	
	
	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public RepaymentType getRepaymentType() {
		return repaymentType;
	}

	public void setRepaymentType(RepaymentType repaymentType) {
		this.repaymentType = repaymentType;
	}

	public Date getDebtorRepaymentDate() {
		return debtorRepaymentDate;
	}

	public void setDebtorRepaymentDate(Date debtorRepaymentDate) {
		this.debtorRepaymentDate = debtorRepaymentDate;
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

	public String getPlanSn() {
		return planSn;
	}

	public void setPlanSn(String planSn) {
		this.planSn = planSn;
	}

	public String getDebtorName() {
		return debtorName;
	}

	public void setDebtorName(String debtorName) {
		this.debtorName = debtorName;
	}

	public Integer getIsOverdue() {
		return isOverdue;
	}

	public void setIsOverdue(Integer isOverdue) {
		this.isOverdue = isOverdue;
	}

	public BigDecimal getPrincipal() {
		return principal;
	}

	public void setPrincipal(BigDecimal principal) {
		this.principal = principal;
	}

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	public Creditrepayplan getCreditrepayplan() {
		return creditrepayplan;
	}

	public void setCreditrepayplan(Creditrepayplan creditrepayplan) {
		this.creditrepayplan = creditrepayplan;
	}

	public BigDecimal getFees() {
		return fees;
	}

	public void setFees(BigDecimal fees) {
		this.fees = fees;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	public java.util.Date getDebtorActualRepaymentDate() {
		return debtorActualRepaymentDate;
	}

	public void setDebtorActualRepaymentDate(java.util.Date debtorActualRepaymentDate) {
		this.debtorActualRepaymentDate = debtorActualRepaymentDate;
	}

	public Creditcard getCreditcard() {
		return creditcard;
	}

	public void setCreditcard(Creditcard creditcard) {
		this.creditcard = creditcard;
	}

	public BigDecimal getPunishinterest() {
		return punishinterest;
	}

	public void setPunishinterest(BigDecimal punishinterest) {
		this.punishinterest = punishinterest;
	}

	public CreditcardChangelog getCreditcardChangelog() {
		return creditcardChangelog;
	}

	public void setCreditcardChangelog(CreditcardChangelog creditcardChangelog) {
		this.creditcardChangelog = creditcardChangelog;
	}

	public String getCreditorRepaymentSn() {
		return creditorRepaymentSn;
	}

	public void setCreditorRepaymentSn(String creditorRepaymentSn) {
		this.creditorRepaymentSn = creditorRepaymentSn;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	@Override
	public void setService(AbstractAuditorService service) {
		Creditrepayment.service = (AbstractWorkflowService) service;

	}

	@JsonIgnore
	@Transient
	@Override
	public CreditrepaymentLog getLogInstance() {
		return new CreditrepaymentLog();
	}

	@JsonIgnore
	@Transient
	@Override
	public LogRepository getLogRepository() {
		return new Creditrepayment().getLogRepository();
	}

	@Override
	public void setLogRepository(LogRepository logRepository) {
		Creditrepayment.logRepository = logRepository;
	}

	// 还款类型
	public static enum RepaymentType {
		ORDERCDD, ORDERMDBT
	}
}