package com.liyang.domain.moneytransfer;

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
import com.liyang.domain.base.AbstractWorkflowEntity;
import com.liyang.domain.base.LogRepository;
import com.liyang.service.AbstractAuditorService;
import com.liyang.service.AbstractWorkflowService;

/**
 * 外部账户
 */
@Entity
@Table(name = "moneytransfer")
@Info(label="小金账户内部转账")	
public class Moneytransfer extends AbstractWorkflowEntity<MoneytransferWorkflow, MoneytransferState, MoneytransferAct, MoneytransferLog,MoneytransferFile>{

	@Transient
	private static AbstractWorkflowService service;
	
	

	@JoinColumn(name="send_account_id")
	@ManyToOne
	private Account sendAccount;
	
	@JoinColumn(name="receive_account_id")
	@ManyToOne
	private Account receiveAccount;	
	
	@Column(name="money_change",precision=19,scale=6)
	private BigDecimal moneyChange;
	
	@ManyToOne
	@JoinColumn(name="send_account_changelog_id")
	private AccountChangelog sendAccountChangelog;
	
	@ManyToOne
	@JoinColumn(name="receive_account_changelog_id")
	private AccountChangelog receiveAccountChangelog;
	
	@Transient
	private static LogRepository logRepository;


	
	
	
	
	public Account getSendAccount() {
		return sendAccount;
	}

	public void setSendAccount(Account sendAccount) {
		this.sendAccount = sendAccount;
	}

	public Account getReceiveAccount() {
		return receiveAccount;
	}

	public void setReceiveAccount(Account receiveAccount) {
		this.receiveAccount = receiveAccount;
	}

	public BigDecimal getMoneyChange() {
		return moneyChange;
	}

	public void setMoneyChange(BigDecimal moneyChange) {
		this.moneyChange = moneyChange;
	}

	

	public AccountChangelog getSendAccountChangelog() {
		return sendAccountChangelog;
	}

	public void setSendAccountChangelog(AccountChangelog sendAccountChangelog) {
		this.sendAccountChangelog = sendAccountChangelog;
	}

	public AccountChangelog getReceiveAccountChangelog() {
		return receiveAccountChangelog;
	}

	public void setReceiveAccountChangelog(AccountChangelog receiveAccountChangelog) {
		this.receiveAccountChangelog = receiveAccountChangelog;
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
		Moneytransfer.service = (AbstractWorkflowService) service;

	}
	@JsonIgnore
	@Override
	public MoneytransferLog getLogInstance() {
		return new MoneytransferLog();
	}
	@JsonIgnore
	@Override
	public LogRepository getLogRepository() {
		return new Moneytransfer().getLogRepository();
	}
	
	@Override
	public void setLogRepository(LogRepository logRepository) {
		Moneytransfer.logRepository=logRepository;
	}
	
}