package com.liyang.domain.moneydeposit;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.domain.account.Account;
import com.liyang.domain.account.AccountChangelog;
import com.liyang.domain.bankcard.Bankcard;
import com.liyang.domain.bankcard.BankcardChangelog;
import com.liyang.domain.base.AbstractWorkflowEntity;
import com.liyang.domain.base.LogRepository;
import com.liyang.service.AbstractAuditorService;
import com.liyang.service.AbstractWorkflowService;

/**
 * 外部账户
 */
@Entity
@Table(name = "moneydeposit")
@Info(label="充值")	
public class Moneydeposit extends AbstractWorkflowEntity<MoneydepositWorkflow, MoneydepositState, MoneydepositAct, MoneydepositLog,MoneydepositFile>{


	@ManyToOne
	@JoinColumn(name="bankcard_id")
	private Bankcard bankcard;

	@JoinColumn(name="account_id")
	@ManyToOne
	private Account account;
	
	@Column(name="money_change",precision=19,scale=6)
	private BigDecimal moneyChange;
	
	@ManyToOne
	@JoinColumn(name="bankcard_changelog_id")
	private BankcardChangelog bankcardChangelog;
	
	@ManyToOne
	@JoinColumn(name="account_changelog_id")
	private AccountChangelog accountChangelog;
	
	

	
	@Transient
	private static AbstractWorkflowService service;

	@Transient
	private static LogRepository logRepository;
	/**
	 *
	 */
	@Transient
	private static final long serialVersionUID = 1L;
	
	

	public Bankcard getBankcard() {
		return bankcard;
	}

	public void setBankcard(Bankcard bankcard) {
		this.bankcard = bankcard;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public BigDecimal getMoneyChange() {
		return moneyChange;
	}

	public void setMoneyChange(BigDecimal moneyChange) {
		this.moneyChange = moneyChange;
	}

	public BankcardChangelog getBankcardChangelog() {
		return bankcardChangelog;
	}

	public void setBankcardChangelog(BankcardChangelog bankcardChangelog) {
		this.bankcardChangelog = bankcardChangelog;
	}

	public AccountChangelog getAccountChangelog() {
		return accountChangelog;
	}

	public void setAccountChangelog(AccountChangelog accountChangelog) {
		this.accountChangelog = accountChangelog;
	}

	@JsonIgnore
	@Transient
	public AbstractAuditorService getService() {
		// TODO Auto-generated method stub
		return service;
	}

	@Override
	public void setService(AbstractAuditorService service) {
		Moneydeposit.service = (AbstractWorkflowService) service;

	}
	@JsonIgnore
	@Override
	public MoneydepositLog getLogInstance() {
		return new MoneydepositLog();
	}
	@JsonIgnore
	@Override
	public LogRepository getLogRepository() {
		return new Moneydeposit().getLogRepository();
	}
	
	@Override
	public void setLogRepository(LogRepository logRepository) {
		Moneydeposit.logRepository=logRepository;
	}
	private Integer getRanom(){
		return (int)(Math.random()*9999999)+1000000;
	}
}