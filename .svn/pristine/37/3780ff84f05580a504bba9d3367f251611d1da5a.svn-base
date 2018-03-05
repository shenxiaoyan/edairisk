package com.liyang.domain.loanstorebond;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Random;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.domain.base.AbstractWorkflowEntity;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.productstorebond.ProductStorebond;
import com.liyang.service.AbstractAuditorService;
import com.liyang.service.AbstractWorkflowService;


@Entity
@Table(name = "loan_storebond")
@Info(label="单笔贷款门店收保证金表",placeholder="",tip="",help="",secret="")
public class LoanStorebond extends AbstractWorkflowEntity<LoanStorebondWorkflow, LoanStorebondState, LoanStorebondAct, LoanStorebondLog,LoanStorebondFile> {

	@Column(name = "amount",precision = 19,scale = 6)
	private BigDecimal amount;
	@Column(name = "description")
	private String description;
	@Column(name = "comment")
	private String comment;
	@JoinColumn(name = "product_storebond_id")
	@ManyToOne
	private ProductStorebond productStorebond;

	public ProductStorebond getProductStorebond() {
		return productStorebond;
	}

	public void setProductStorebond(ProductStorebond productStorebond) {
		this.productStorebond = productStorebond;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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

	@Transient
	private static AbstractWorkflowService service;
	@Transient
	private static ActRepository actRepository;
	@Transient
	private static LogRepository logRepository;
	@Override
	@JsonIgnore
	@Transient
	public LoanStorebondLog getLogInstance() {
		// TODO Auto-generated method stub
		return new LoanStorebondLog();
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
		LoanStorebond.logRepository = logRepository;
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
		LoanStorebond.service = (AbstractWorkflowService) service;

	}
}