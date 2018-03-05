package com.liyang.domain.moneyadjust;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.annotation.Info.FLAG;
import com.liyang.domain.account.Account;
import com.liyang.domain.account.AccountChangelog;
import com.liyang.domain.bank.Bank;
import com.liyang.domain.base.*;
import com.liyang.domain.enterprise.Enterprise;
import com.liyang.domain.person.Person;
import com.liyang.domain.user.User;
import com.liyang.service.AbstractAuditorService;
import com.liyang.service.AbstractWorkflowService;

import javax.persistence.*;
import javax.validation.constraints.Max;

import org.springframework.security.access.prepost.PreInvocationAttribute;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Random;
import java.util.UUID;

/**
 * 外部账户
 */
@Entity
@Table(name = "moneyadjust")
@Info(label="余额调整")	
public class Moneyadjust extends AbstractWorkflowEntity<MoneyadjustWorkflow, MoneyadjustState, MoneyadjustAct, MoneyadjustLog,MoneyadjustFile>{

	@Transient
	private static AbstractWorkflowService service;

	@Transient
	private static LogRepository logRepository;

	@JoinColumn(name="account_id")
	@ManyToOne
	private Account account;
	
	@Column(name="money_change",precision=19,scale=6)
	private BigDecimal moneyChange;
	
	@JoinColumn(name="account_changelog_id")
	@ManyToOne
	private AccountChangelog accountChangelog;
	

	
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

	public AccountChangelog getAccountChangelog() {
		return accountChangelog;
	}

	public void setAccountChangelog(AccountChangelog accountChangelog) {
		this.accountChangelog = accountChangelog;
	}

	@Transient
	private static final long serialVersionUID = 1L;
	@JsonIgnore
	@Transient
	public AbstractAuditorService getService() {
		// TODO Auto-generated method stub
		return service;
	}

	@Override
	public void setService(AbstractAuditorService service) {
		Moneyadjust.service = (AbstractWorkflowService) service;

	}
	
	@JsonIgnore
	@Transient
	@Override
	public MoneyadjustLog getLogInstance() {
		return new MoneyadjustLog();
	}
	@JsonIgnore
	@Transient
	@Override
	public LogRepository getLogRepository() {
		return new Moneyadjust().getLogRepository();
	}
	
	@Override
	public void setLogRepository(LogRepository logRepository) {
		Moneyadjust.logRepository=logRepository;
	}
}