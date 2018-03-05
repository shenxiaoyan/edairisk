package com.liyang.domain.approveloan;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.liyang.annotation.Info;
import com.liyang.domain.person.Person;

@Entity
@Table(name = "approveloan")
@Info(label="审批借款")
public class ApproveLoan {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="create_date")
	@Info(label="创建时间",placeholder="",tip="",help="",secret="")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;
	
	private String mobile; 
	
	@Column(name = "loan_amount",precision=19,scale=6)
	@Info(label="借款金额",placeholder="",tip="",help="",secret="")
	private BigDecimal  loanAmount;

	@OneToOne
	@JoinColumn(name="state_id")
	@Info(label="状态",placeholder="",tip="",help="",secret="")
	private ApproveLoanState approveLoanState;
	
	@ManyToOne
	@JoinColumn(name = "person_id")
	@Info(label="法人",placeholder="",tip="",help="",secret="")
	private Person person;
	
	@Column(name="pass_date")
	@Info(label="通过时间",placeholder="",tip="",help="",secret="")	
	private Date passDate;
	
	@Column(name = "order_no")
	@Info(label="单号",placeholder="",tip="",help="",secret="")
	private String orderNo;
	
	//创建开始时间
	@Transient
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") 
	private Date createDateTime;
	
	//创建结束时间
	@Transient
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") 
	private Date endDateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public ApproveLoanState getApproveLoanState() {
		return approveLoanState;
	}

	public void setApproveLoanState(ApproveLoanState approveLoanState) {
		this.approveLoanState = approveLoanState;
	}

	public Date getPassDate() {
		return passDate;
	}

	public void setPassDate(Date passDate) {
		this.passDate = passDate;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public Date getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}
		
}
