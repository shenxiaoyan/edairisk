package com.liyang.domain.creditadjust;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.domain.base.AbstractWorkflowEntity;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.creditcard.Creditcard;
import com.liyang.domain.creditcard.CreditcardChangelog;
import com.liyang.service.AbstractAuditorService;
import com.liyang.service.AbstractWorkflowService;


@Entity
@Table(name = "creditadjust")
@Info(label="信用卡调整")	
public class Creditadjust extends AbstractWorkflowEntity<CreditadjustWorkflow, CreditadjustState, CreditadjustAct, CreditadjustLog,CreditadjustFile>{

	@Transient
	private static AbstractWorkflowService service;

	@Transient
	private static LogRepository logRepository;


	@JoinColumn(name="creditcard_id")
	@ManyToOne
	private Creditcard creditcard;
	
	
	@Column(name="credit_change",precision=19,scale=6)
	private BigDecimal creditChange;

	
	@JoinColumn(name="creditcard_changelog_id")
	@ManyToOne
	private CreditcardChangelog creditcardChangelog;
	
	

	public BigDecimal getCreditChange() {
		return creditChange;
	}

	public void setCreditChange(BigDecimal creditChange) {
		this.creditChange = creditChange;
	}



	public Creditcard getCreditcard() {
		return creditcard;
	}

	public void setCreditcard(Creditcard creditcard) {
		this.creditcard = creditcard;
	}

	public CreditcardChangelog getCreditcardChangelog() {
		return creditcardChangelog;
	}

	public void setCreditcardChangelog(CreditcardChangelog creditcardChangelog) {
		this.creditcardChangelog = creditcardChangelog;
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
		Creditadjust.service = (AbstractWorkflowService) service;

	}
	
	@JsonIgnore
	@Transient
	@Override
	public CreditadjustLog getLogInstance() {
		return new CreditadjustLog();
	}
	@JsonIgnore
	@Transient
	@Override
	public LogRepository getLogRepository() {
		return new Creditadjust().getLogRepository();
	}
	
	@Override
	public void setLogRepository(LogRepository logRepository) {
		Creditadjust.logRepository=logRepository;
	}
}