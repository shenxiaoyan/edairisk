package com.liyang.domain.account;

import java.math.BigDecimal;
import java.util.Random;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.domain.base.AbstractWorkflowEntity;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.moneyadjust.Moneyadjust;
import com.liyang.domain.moneydeposit.Moneydeposit;
import com.liyang.domain.moneytransfer.Moneytransfer;
import com.liyang.domain.moneywidthdraw.Moneywidthdraw;
import com.liyang.domain.user.User;
import com.liyang.service.AbstractAuditorService;
import com.liyang.service.AbstractWorkflowService;

/**
 * 外部账户
 */
@Entity
@Table(name = "account")
@Info(label="小金账户")	
public class Account extends AbstractWorkflowEntity<AccountWorkflow, AccountState, AccountAct, AccountLog,AccountFile>{

	@Transient
	private static AbstractWorkflowService service;

	@Transient
	private static LogRepository logRepository;


	@Transient
	private static final long serialVersionUID = 1L;

	@JoinColumn(name = "user_id")
	@OneToOne
	@Info(label="所属用户ID",placeholder="",tip="",help="",secret="")	
	private User user;
	
	
	@Column(name = "account_identity")//不是贝兜回传的就是 bank_id =1的
	@Info(label="账户号码",placeholder="",tip="如银行卡号、支付宝账户、小金银行账户",help="",secret="注意，小金银行账户是内部生成的")	
	private Long accountIdentity;//贝兜回传的这就是手机号
	
	
	@Column(name="account_addition")
	@Info(label="账户校验码",placeholder="账户校验码",tip="验证的7位数字（信用卡背后）",help="",secret="自动生成")
	private Integer accountAddition=getRanom();
	
	
	@Column(name = "money_balance",precision=19,scale=6)
	@Info(label="现金余额",placeholder="",tip="现金余额",help="",secret="")	
	private BigDecimal moneyBalance;
	
	
	@Column(name = "money_frozen",precision=19,scale=6)
	@Info(label="现金冻结额",placeholder="",tip="被冻结的现金，通常是因为提现审核中的现金",help="",secret="")	
	private BigDecimal moneyFrozen;
	

	@Lob
	@Info(label="说明",placeholder="填写一些公开的说明",tip="填写一些公开的说明",help="填写一些公开的说明",secret="")
	private String description;
	
	
	@Info(label="排序",placeholder="",tip="",help="",secret="")
	private Integer sort;

	@OneToMany(mappedBy = "account")
	private Set<Moneydeposit> moneydeposits;
	
	@OneToMany(mappedBy = "account")
	private Set<Moneyadjust> moneyadjusts;
	
	@OneToMany(mappedBy = "account")
	private Set<Moneywidthdraw> moneywidthdraws;
	
	@OneToMany(mappedBy = "sendAccount")
	private Set<Moneytransfer> moneytransfers;
	
	@OneToMany(mappedBy = "account")
	private Set<AccountChangelog> accountChangelog;

	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getAccountAddition() {
		return accountAddition;
	}

	public void setAccountAddition(Integer accountAddition) {
		this.accountAddition = accountAddition;
	}


	public Long getAccountIdentity() {
		return accountIdentity;
	}

	public void setAccountIdentity(Long accountIdentity) {
		if (accountIdentity==null) {
			//this.accountIdentity=UUID.randomUUID();
			
			Random random=new Random();
			String first=System.currentTimeMillis()+""+(random.nextInt(899)+100);
			accountIdentity=Long.parseLong(first) ;
			return;
		}
		this.accountIdentity = accountIdentity;
	}

	public BigDecimal getMoneyBalance() {
		return moneyBalance;
	}

	public void setMoneyBalance(BigDecimal moneyBalance) {
		this.moneyBalance = moneyBalance;
	}

	public BigDecimal getMoneyFrozen() {
		return moneyFrozen;
	}

	public void setMoneyFrozen(BigDecimal moneyFrozen) {
		this.moneyFrozen = moneyFrozen;
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


	@JsonIgnore
	@Transient
	public AbstractAuditorService getService() {
		// TODO Auto-generated method stub
		return service;
	}

	@Override
	public void setService(AbstractAuditorService service) {
		Account.service = (AbstractWorkflowService) service;

	}
	@JsonIgnore
	@Transient
	@Override
	public AccountLog getLogInstance() {
		return new AccountLog();
	}
	@JsonIgnore
	@Transient
	@Override
	public LogRepository getLogRepository() {
		return new Account().getLogRepository();
	}
	
	@Override
	public void setLogRepository(LogRepository logRepository) {
		Account.logRepository=logRepository;
	}
	private Integer getRanom(){
		return (int)(Math.random()*9999999)+1000000;
	}
}