package com.liyang.domain.product;

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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.domain.account.Account;
import com.liyang.domain.bankcard.Bankcard;
import com.liyang.domain.base.AbstractWorkflowEntity;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.capitalproduct.Capitalproduct;
import com.liyang.domain.department.Department;
import com.liyang.domain.orderwdsjsh.Orderwdsjsh;
import com.liyang.domain.punishinterestrule.Punishinterestrule;
import com.liyang.service.AbstractAuditorService;
import com.liyang.service.AbstractWorkflowService;
import com.liyang.util.UpdateValidGroup;

@Entity
@Table(name = "product")
@Info(label="产品")
public class Product extends AbstractWorkflowEntity<ProductWorkflow, ProductState, ProductAct, ProductLog, ProductFile> {



	@Transient
	private static LogRepository logRepository;
	
	@Transient
	private static AbstractWorkflowService service;
	
	@ManyToOne
	@JoinColumn(name = "department_id")
	@Info(label="授权给部门/门店",placeholder="",tip="",help="支持何种申请订单可以创建（在account里有）",secret="")
	private Department department;

	@Info(label="给部门/门店的利息",placeholder="",tip="",help="",secret="")
	@Column(name = "store_interest")//给门店的利息
	private BigDecimal storeInterest;

	@Column(name="debtor_interest",precision=19,scale=6)
	@Info(label="给借方的利息",placeholder="",tip="",help="",secret="")
	private BigDecimal debtorInterest;

	@Column(name = "store_amount_min",precision=19,scale=6)
	@Info(label="最小贷款额",placeholder="",tip="门店可以创建的贷款额最小值",help="",secret="")
	private Integer storeAmountMin;
	@Column(name = "store_amount_max",precision=19,scale=6)
	@Info(label="最大贷款额",placeholder="",tip="门店可以创建的贷款额最大值",help="",secret="")
	private Integer storeAmountMax;

	
	//添加 产品名称，资方名称，每月贷款百分比，活动期间贷款百分比，活动月份，借款利率 字段 2017/12/21
	@Column(name = "product_Name")
	@Info(label="产品名称",placeholder="",tip="",help="",secret="")
	private String productName;
	@Column(name = "capital_Name")
	@Info(label="资方名称",placeholder="",tip="",help="",secret="")
	private String capitalName;
	@Column(name = "month_Loan_Percent",precision=19,scale=6)
	@Info(label="每月贷款百分比",placeholder="",tip="",help="",secret="单位为%")
	private Double monthLoanPercent;
	@Column(name = "active_Loan_Percent",precision=19,scale=6)
	@Info(label="活动期间贷款百分比",placeholder="",tip="",help="",secret="单位为%")
	private Double activeLoanPercent;
	@Column(name = "active_Month")
	@Info(label="活动月份",placeholder="",tip="",help="",secret="暂时设定十一月和十二月，允许添加新的月份和删除月份")
	private String activeMonth;
	
	@Column(name="loan_rate")
	@Info(label="借款利率",placeholder="",tip="",help="",secret="")
	private Double loanRate;
	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCapitalName() {
		return capitalName;
	}

	public void setCapitalName(String capitalName) {
		this.capitalName = capitalName;
	}

	public Double getMonthLoanPercent() {
		return monthLoanPercent;
	}

	public void setMonthLoanPercent(Double monthLoanPercent) {
		this.monthLoanPercent = monthLoanPercent;
	}

	public Double getActiveLoanPercent() {
		return activeLoanPercent;
	}

	public void setActiveLoanPercent(Double activeLoanPercent) {
		this.activeLoanPercent = activeLoanPercent;
	}

	public String getActiveMonth() {
		return activeMonth;
	}

	public void setActiveMonth(String activeMonth) {
		this.activeMonth = activeMonth;
	}

	public Double getLoanRate() {
		return loanRate;
	}

	public void setLoanRate(Double loanRate) {
		this.loanRate = loanRate;
	}

	public Set<Orderwdsjsh> getOrderwdsjshes() {
		return orderwdsjshes;
	}

	public void setOrderwdsjshes(Set<Orderwdsjsh> orderwdsjshes) {
		this.orderwdsjshes = orderwdsjshes;
	}
	
	
	//一个product可以包含多个随借随还订单,one product has many orderwdsjshes,owner is product jack
	@OneToMany(mappedBy = "product")
	@Info(label="拥有的多个随借随还订单",placeholder="",tip="",help="",secret="")
	private Set<Orderwdsjsh> orderwdsjshes;
	
	
	
	@JoinColumn(name = "punishinterestrule_id")
	@ManyToOne
	@Info(label="该授权专用罚息规则",placeholder="",tip="",help="",secret="开发者：如果这个为空，在业务场景里而按照capitalproduct里的罚息规则")
	private Punishinterestrule punishinterestrule;
	
	@JoinColumn(name = "capitalproduct_id")
	@ManyToOne
	@Info(label="资金产品",placeholder="",tip="",help="",secret="")
	private Capitalproduct capitalproduct;
	
	@JoinColumn(name = "repayment_account_id")
	@ManyToOne
	@Info(label="该授权专门的收款帐户",placeholder="",tip="",help="",secret="开发者：如果这个为空，在业务场景里而按照capitalproduct里的收款账户")
	private Bankcard repaymentAccount;
	
	@Column(name = "store_repayment_day")
	@Info(label="门店还款日",placeholder="",tip="",help="",secret="")
	private Integer storeRepaymentDay;

	@Column(name = "debtor_repayment_day")
	@Info(label="供方还款日",placeholder="",tip="",help="借方的还款日（有则为固定时间，空则为生成贷款时自定义）",secret="")
	private Integer debtorRepaymentDay;

	@Column(name = "auto_riskmodel_failvalue")
	@Info(label="自动风控失败阀值",placeholder="",tip="",help="由capitalproduct对应的自动风控模型auto_riskmodek_id计息所得",secret="")
	private Integer autoRiskmodelFailValue;

	@Column(name = "auto_riskmodel_successvalue")
	@Info(label="自动风控成功阀值",placeholder="",tip="",help="由capitalproduct对应的自动风控模型auto_riskmodek_id计息所得",secret="")
	private Integer autoRiskmodelSuccessValue;

	@Lob
	@Info(label="说明",placeholder="",tip="",help="",secret="")
	private String description;
	@Lob
	@Info(label="内部备注",placeholder="",tip="",help="",secret="")
	private String comment;

	@NotNull(groups={UpdateValidGroup.class})
	@Info(label="排序",placeholder="",tip="",help="",secret="")
	private Integer sort;







	public Capitalproduct getCapitalproduct() {
		return capitalproduct;
	}

	public void setCapitalproduct(Capitalproduct capitalproduct) {
		this.capitalproduct = capitalproduct;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public BigDecimal getStoreInterest() {
		return storeInterest;
	}

	public void setStoreInterest(BigDecimal storeInterest) {
		this.storeInterest = storeInterest;
	}

	public BigDecimal getDebtorInterest() {
		return debtorInterest;
	}

	public void setDebtorInterest(BigDecimal debtorInterest) {
		this.debtorInterest = debtorInterest;
	}

	public Integer getStoreAmountMin() {
		return storeAmountMin;
	}

	public void setStoreAmountMin(Integer storeAmountMin) {
		this.storeAmountMin = storeAmountMin;
	}

	
	public Bankcard getRepaymentAccount() {
		return repaymentAccount;
	}

	public void setRepaymentAccount(Bankcard repaymentAccount) {
		this.repaymentAccount = repaymentAccount;
	}

	public Integer getStoreAmountMax() {
		return storeAmountMax;
	}

	public void setStoreAmountMax(Integer storeAmountMax) {
		this.storeAmountMax = storeAmountMax;
	}



	public Punishinterestrule getPunishinterestrule() {
		return punishinterestrule;
	}

	public void setPunishinterestrule(Punishinterestrule punishinterestrule) {
		this.punishinterestrule = punishinterestrule;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	

	public Integer getStoreRepaymentDay() {
		return storeRepaymentDay;
	}

	public void setStoreRepaymentDay(Integer storeRepaymentDay) {
		this.storeRepaymentDay = storeRepaymentDay;
	}

	public Integer getDebtorRepaymentDay() {
		return debtorRepaymentDay;
	}

	public void setDebtorRepaymentDay(Integer debtorRepaymentDay) {
		this.debtorRepaymentDay = debtorRepaymentDay;
	}

	public Integer getAutoRiskmodelFailValue() {
		return autoRiskmodelFailValue;
	}

	public void setAutoRiskmodelFailValue(Integer autoRiskmodelFailValue) {
		this.autoRiskmodelFailValue = autoRiskmodelFailValue;
	}

	public Integer getAutoRiskmodelSuccessValue() {
		return autoRiskmodelSuccessValue;
	}

	public void setAutoRiskmodelSuccessValue(Integer autoRiskmodelSuccessValue) {
		this.autoRiskmodelSuccessValue = autoRiskmodelSuccessValue;
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

	@Override
	@JsonIgnore
	@Transient
	public ProductLog getLogInstance() {
		// TODO Auto-generated method stub
		return new ProductLog();
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
		Product.logRepository = logRepository;
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
		Product.service = (AbstractWorkflowService) service;
		
	}



}