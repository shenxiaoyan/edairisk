package com.liyang.domain.loan;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.domain.creditrepayment.Creditrepayment;

/**
 * 逾期信息表
 */
@Entity
@Table(name = "loanoverdue")
@Cacheable
@Info(label="逾期信息表",placeholder="",tip="",help="",secret="")
public class Loanoverdue implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Info(label="id",placeholder="",tip="",help="",secret="")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="loan_id")
	@Info(label="所属贷款",placeholder="",tip="",help="",secret="")
	@JsonBackReference
	private Loan loan;
	
	@Column(name = "history_amount",precision=19,scale=6)
	@Info(label="逾期累加金额",placeholder="",tip="",help="",secret="")
	private BigDecimal historyAmount;
	
	@Column(name = "overdue_amount",precision=19,scale=6)
	@Info(label="逾期金额",placeholder="",tip="",help="",secret="")
	private BigDecimal overdueAmount;
	
	@Column(name = "penal_sum",precision=19,scale=6)
	@Info(label="逾期罚金",placeholder="",tip="",help="正常剩余应还金额 = 逾期总金额 - 逾期罚金",secret="")
	private BigDecimal penalSum;
	
	@Column(name="overdue_days")
	@Info(label="逾期天数",placeholder="",tip="",help="",secret="")
	private Integer overdueDays;
	
	@Column(name="created_at")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@Info(label="创建时间",placeholder="",tip="",help="",secret="")
	private Date createdAt;
	
	@Column(name="finish_at")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@Info(label="结清时间",placeholder="",tip="",help="",secret="")
	private Date finishAt;
	
	@Column(name="status")
	@Info(label="当前状态",placeholder="",tip="",help="0：逾期中；1：已结清",secret="")
	private Integer status;
	
	@OneToOne(fetch=FetchType.LAZY,targetEntity=Creditrepayment.class)
	@JoinTable(name="loanoverdue_creditrepayment",joinColumns = { @JoinColumn(name = "loanoverdue_id") }, 
												inverseJoinColumns = { @JoinColumn(name = "creditrepayment_id") })
	@Info(label="对应的还款记录",placeholder="",tip="",help="",secret="")
	@JsonIgnore
	private Creditrepayment creditrepayment;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	public BigDecimal getHistoryAmount() {
		return historyAmount;
	}

	public void setHistoryAmount(BigDecimal historyAmount) {
		this.historyAmount = historyAmount;
	}

	public BigDecimal getOverdueAmount() {
		return overdueAmount;
	}

	public void setOverdueAmount(BigDecimal overdueAmount) {
		this.overdueAmount = overdueAmount;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getFinishAt() {
		return finishAt;
	}

	public void setFinishAt(Date finishAt) {
		this.finishAt = finishAt;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Creditrepayment getCreditrepayment() {
		return creditrepayment;
	}

	public void setCreditrepayment(Creditrepayment creditrepayment) {
		this.creditrepayment = creditrepayment;
	}

	public BigDecimal getPenalSum() {
		return penalSum;
	}

	public void setPenalSum(BigDecimal penalSum) {
		this.penalSum = penalSum;
	}

	public Integer getOverdueDays() {
		return overdueDays;
	}

	public void setOverdueDays(Integer overdueDays) {
		this.overdueDays = overdueDays;
	}
}
