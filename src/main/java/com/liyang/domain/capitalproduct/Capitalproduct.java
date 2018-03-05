package com.liyang.domain.capitalproduct;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.domain.account.Account;
import com.liyang.domain.bankcard.Bankcard;
import com.liyang.domain.base.AbstractWorkflowEntity;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.department.Department;
import com.liyang.domain.loan.Loan;
import com.liyang.domain.product.Product;
import com.liyang.domain.punishinterestrule.Punishinterestrule;
import com.liyang.domain.riskmodel.Riskmodel;
import com.liyang.service.AbstractAuditorService;
import com.liyang.service.AbstractWorkflowService;
import com.liyang.util.UpdateValidGroup;

@Entity
@Table(name = "capitalproduct")
@Info(label="资金产品")
public class Capitalproduct extends AbstractWorkflowEntity<CapitalproductWorkflow, CapitalproductState, CapitalproductAct, CapitalproductLog, CapitalproductFile> {



	@Transient
	private static LogRepository logRepository;
	
	@Transient
	private static AbstractWorkflowService service;
	
	
	
	@Lob
	@Info(label="说明",placeholder="",tip="",help="",secret="")	   
	private String description;

	@Column(name = "debtortype_code")
	@Enumerated(EnumType.STRING)
	@Info(label="个人（自然人）或企业（法人）",placeholder="",tip="该资金产品允许自然人或法人贷款",help="",secret="")	   
	private DebtortypeCode debtortypeCode;//要求（enterprise / person）
	
	@Column(name = "period_code")
	@Enumerated(EnumType.STRING)
	@Info(label="贷款分期时间单位",placeholder="",tip="DAY为天，WEEK为周，MONTH为月",help="",secret="")	   
	private Loan.PeriodCode periodCode;

	@Column(name = "period_number")
	@Info(label="贷款分期数",placeholder="如1/2/3/6/9/12",tip="",help="",secret="")	   
	private Integer periodNumber;//可填的数值
	
	@Column(name = "creditor_amount_min")
	@Info(label="最小放款金额（元）",placeholder="如100000",tip="",help="",secret="")	   
	private Integer creditorAmountMin;//最小放款金额
	
	@Column(name = "creditor_amount_max")
	@Info(label="最大放款金额（元）",placeholder="如1000",tip="",help="",secret="")	   
	private Integer creditorAmountMax;

	@Column(name = "creditor_amount_autopass")
	@Info(label="资方自动通过金额",placeholder="如2000",tip="",help="",secret="")	   
	private Integer creditorAmountAutopass;
	
	@Enumerated(EnumType.STRING)
	@Column(name="repayment_method_code")
	@Info(label="还款规则",placeholder="",tip="",help="",secret="")	
	private RepaymentMethodCode repaymentMethodCode;

	
	@Column(name = "creditor_interest",precision=19,scale=6)
	@Info(label="年利率",placeholder="",tip="",help="",secret="")
	private BigDecimal creditorInterest;

	@JoinColumn(name = "creditor_department_id")
	@ManyToOne
	@Info(label="资方组织ID",placeholder="",tip="",help="",secret="")
	private Department creditorDepartment;

	@Column(name = "creditor_repayment_day")
	@Info(label="每月给资方还款的日期",placeholder="",tip="",help="",secret="")
	private Integer creditorRepaymentDay;

	@Column(name = "overdue_grace_days")
	@Info(label="逾期宽限天数",placeholder="",tip="",help="",secret="")
	private Integer overdueGraceDays;
	
	@Column(name = "overdue_penalty_interest_rate",precision=19,scale=6)
	@Info(label="逾期罚息率",placeholder="",tip="",help="",secret="")
	private BigDecimal overduePenaltyInterestRate;

	
	@JoinColumn(name = "creditor_repayment_bankcard")
	@ManyToOne
	@Info(label="还款账户",placeholder="",tip="",help="",secret="")
	private Bankcard creditorRepaymentBankcard;

	@JoinColumn(name = "creditor_repayment_account_id")
	@ManyToOne
	@Info(label="还款账户（银行）",placeholder="",tip="",help="",secret="")
	private Account creditorRepaymentAccount;

	@JoinColumn(name = "creditor_repayment_alipay_account_id")
	@ManyToOne
	@Info(label="还款账户（支付宝）",placeholder="",tip="",help="",secret="")
	private Account creditorRepaymentAlipayAccount;

	@JoinColumn(name = "creditor_repayment_tenpay_account_id")
	@ManyToOne
	@Info(label="还款账户（财付通）",placeholder="",tip="",help="",secret="")
	private Account creditorRepaymentTenpayAccount;


	@OneToMany(mappedBy="capitalproduct")
	private Set<Product> products;
	
	
	@Lob
	private String exfield1;
	@Lob
	private String exfield2;
	@Lob
	private String exfield3;
	
	@Lob
	@Info(label="资方内部备注",placeholder="",tip="对外不展示",help="",secret="")
	private String comment;
	
	@Column(name = "icon_class")
	@Info(label="小图标class",placeholder="",tip="",help="",secret="用于前台创建贷款按钮前")
	private String iconClass;
	
	@NotNull(groups={UpdateValidGroup.class})
	@Info(label="排序",placeholder="",tip="",help="",secret="")
	private Integer sort;
	
	
	
	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	@ManyToOne
	@JoinColumn(name="auto_riskmodel_id")
	@Info(label="自动风控模型",placeholder="",tip="",help="创建贷款时先进行自动风控，失败或者成功",secret="")
	private Riskmodel autoRiskModel;
	
	@ManyToOne
	@JoinColumn(name="manual_riskmodel_id")
	@Info(label="风控模型",placeholder="",tip="",help="在贷款详细页手动点击查风控",secret="")
	private Riskmodel manualRiskmodel;
	
	@ManyToOne
	@JoinColumn(name="monitor_riskmodel_id")
	@Info(label="风控监视（企业）",placeholder="",tip="",help="贷款之后，周期性检测该模型",secret="暂时无用")	
	private Riskmodel monitorRiskmodel;

	@JoinColumn(name = "punishinterestrule_id")
	@ManyToOne
	@Info(label="该授权专用罚息规则",placeholder="",tip="",help="",secret="罚息规则")
	private Punishinterestrule punishinterestrule;
	//manual_riskmodel_id
	//auto_riskmodel_id
	//monitor_riskmodel_id
	

	@Override
	@JsonIgnore
	@Transient
	public CapitalproductLog getLogInstance() {
		// TODO Auto-generated method stub
		return new CapitalproductLog();
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
		Capitalproduct.logRepository = logRepository;
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
		Capitalproduct.service = (AbstractWorkflowService) service;
		
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public DebtortypeCode getDebtortypeCode() {
		return debtortypeCode;
	}

	public void setDebtortypeCode(DebtortypeCode debtortypeCode) {
		this.debtortypeCode = debtortypeCode;
	}

	public Loan.PeriodCode getPeriodCode() {
		return periodCode;
	}

	public Integer getOverdueGraceDays() {
		return overdueGraceDays;
	}

	public void setOverdueGraceDays(Integer overdueGraceDays) {
		this.overdueGraceDays = overdueGraceDays;
	}



	public BigDecimal getOverduePenaltyInterestRate() {
		return overduePenaltyInterestRate;
	}

	public void setOverduePenaltyInterestRate(BigDecimal overduePenaltyInterestRate) {
		this.overduePenaltyInterestRate = overduePenaltyInterestRate;
	}

	public void setPeriodCode(Loan.PeriodCode periodCode) {
		this.periodCode = periodCode;
	}


	public Bankcard getCreditorRepaymentBankcard() {
		return creditorRepaymentBankcard;
	}

	public void setCreditorRepaymentBankcard(Bankcard creditorRepaymentBankcard) {
		this.creditorRepaymentBankcard = creditorRepaymentBankcard;
	}

	public Integer getPeriodNumber() {
		return periodNumber;
	}

	public void setPeriodNumber(Integer periodNumber) {
		this.periodNumber = periodNumber;
	}

	public Integer getCreditorAmountMin() {
		return creditorAmountMin;
	}

	public void setCreditorAmountMin(Integer creditorAmountMin) {
		this.creditorAmountMin = creditorAmountMin;
	}

	public Integer getCreditorAmountMax() {
		return creditorAmountMax;
	}

	public void setCreditorAmountMax(Integer creditorAmountMax) {
		this.creditorAmountMax = creditorAmountMax;
	}

	public Integer getCreditorAmountAutopass() {
		return creditorAmountAutopass;
	}

	public void setCreditorAmountAutopass(Integer creditorAmountAutopass) {
		this.creditorAmountAutopass = creditorAmountAutopass;
	}

	public RepaymentMethodCode getRepaymentMethodCode() {
		return repaymentMethodCode;
	}

	public void setRepaymentMethodCode(RepaymentMethodCode repaymentMethodCode) {
		this.repaymentMethodCode = repaymentMethodCode;
	}

	public BigDecimal getCreditorInterest() {
		return creditorInterest;
	}

	public void setCreditorInterest(BigDecimal creditorInterest) {
		this.creditorInterest = creditorInterest;
	}

	public Department getCreditorDepartment() {
		return creditorDepartment;
	}

	public void setCreditorDepartment(Department creditorDepartment) {
		this.creditorDepartment = creditorDepartment;
	}



	public Integer getCreditorRepaymentDay() {
		return creditorRepaymentDay;
	}

	public void setCreditorRepaymentDay(Integer creditorRepaymentDay) {
		this.creditorRepaymentDay = creditorRepaymentDay;
	}

	public Account getCreditorRepaymentAccount() {
		return creditorRepaymentAccount;
	}

	public void setCreditorRepaymentAccount(Account creditorRepaymentAccount) {
		this.creditorRepaymentAccount = creditorRepaymentAccount;
	}

	public Account getCreditorRepaymentAlipayAccount() {
		return creditorRepaymentAlipayAccount;
	}

	public void setCreditorRepaymentAlipayAccount(Account creditorRepaymentAlipayAccount) {
		this.creditorRepaymentAlipayAccount = creditorRepaymentAlipayAccount;
	}

	public Account getCreditorRepaymentTenpayAccount() {
		return creditorRepaymentTenpayAccount;
	}

	public void setCreditorRepaymentTenpayAccount(Account creditorRepaymentTenpayAccount) {
		this.creditorRepaymentTenpayAccount = creditorRepaymentTenpayAccount;
	}

	public String getExfield1() {
		return exfield1;
	}

	public void setExfield1(String exfield1) {
		this.exfield1 = exfield1;
	}

	public String getExfield2() {
		return exfield2;
	}

	public void setExfield2(String exfield2) {
		this.exfield2 = exfield2;
	}

	public String getExfield3() {
		return exfield3;
	}

	public void setExfield3(String exfield3) {
		this.exfield3 = exfield3;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getIconClass() {
		return iconClass;
	}

	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Riskmodel getAutoRiskModel() {
		return autoRiskModel;
	}

	public void setAutoRiskModel(Riskmodel autoRiskModel) {
		this.autoRiskModel = autoRiskModel;
	}

	public Riskmodel getManualRiskmodel() {
		return manualRiskmodel;
	}

	public void setManualRiskmodel(Riskmodel manualRiskmodel) {
		this.manualRiskmodel = manualRiskmodel;
	}

	public Riskmodel getMonitorRiskmodel() {
		return monitorRiskmodel;
	}

	public void setMonitorRiskmodel(Riskmodel monitorRiskmodel) {
		this.monitorRiskmodel = monitorRiskmodel;
	}

	public Punishinterestrule getPunishinterestrule() {
		return punishinterestrule;
	}

	public void setPunishinterestrule(Punishinterestrule punishinterestrule) {
		this.punishinterestrule = punishinterestrule;
	}

//	private static enum PeriodCode{
//		@Info(label="一次性",placeholder="",tip="",help="",secret="ONE")
//		ONE,
//		@Info(label="天",placeholder="",tip="",help="",secret="DAY")
//		DAY,
//		@Info(label="周",placeholder="",tip="",help="",secret="WEEK")
//		WEEK,
//		@Info(label="月",placeholder="",tip="",help="",secret="MONTH")
//		MONTH
//	}
	private static  enum DebtortypeCode{
		@Info(label="企业（法人）",placeholder="",tip="",help="",secret="ENTERPRISE")
		ENTERPRISE,
		@Info(label="个人（自然人）",placeholder="",tip="",help="",secret="PERSON")
		PERSON,
		@Info(label="企业或个人",placeholder="",tip="",help="",secret="BOTH")
		BOTH
	}
	
	public static enum RepaymentMethodCode{
		@Info(label="随借随还",placeholder="",tip="",help="",secret="LEND_REPAY_ON_DEMAND")
		LEND_REPAY_ON_DEMAND,//先息后本
		@Info(label="先息后本",placeholder="",tip="",help="",secret="BEFORE_INTEREST_AFTER_PRINCIPAL")
		BEFORE_INTEREST_AFTER_PRINCIPAL,//先息后本
		@Info(label="等额本息",placeholder="",tip="",help="",secret="AVERAGE_CAPITAL_PLUS_INTEREST")
		AVERAGE_CAPITAL_PLUS_INTEREST,//等额本息
		@Info(label="等额本金",placeholder="",tip="",help="",secret="AVERAGE_CAPITAL")
		AVERAGE_CAPITAL//等额本金
	}
	


}