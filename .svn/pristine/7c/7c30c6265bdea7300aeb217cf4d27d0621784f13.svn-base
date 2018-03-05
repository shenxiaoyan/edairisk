package com.liyang.domain.creditcard;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.annotation.Info.FLAG;
import com.liyang.domain.base.AbstractWorkflowEntity;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.company.Company;
import com.liyang.domain.creditadjust.Creditadjust;
import com.liyang.domain.creditrepayment.Creditrepayment;
import com.liyang.domain.enterprise.Enterprise;
import com.liyang.domain.loan.Loan;
import com.liyang.domain.person.Person;
import com.liyang.domain.product.Product;
import com.liyang.domain.user.User;
import com.liyang.service.AbstractAuditorService;
import com.liyang.service.AbstractWorkflowService;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * 外部账户
 */
@Entity
@Table(name = "creditcard")
@Info(label="授信账户（信用卡）",placeholder="",tip="",help="",secret="")	
public class Creditcard extends AbstractWorkflowEntity<CreditcardWorkflow, CreditcardState, CreditcardAct, CreditcardLog,CreditcardFile>{

	@Transient
	private static AbstractWorkflowService service;

	@Transient
	private static LogRepository logRepository;

	@Transient
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name="product_id")
	@Info(label="产品ID",placeholder="",tip="",help="",secret="关联表product")	
	private Product product;
	
	@Column(name = "creditcard_identity")
	@Info(label="账户号码",placeholder="",tip="",help="",secret="")	
	private String creditcardIdentity;//贝兜回传的这就是手机号
	
	@Column(name="creditcard_addition")
	@Info(label="账户校验码",placeholder="账户校验码",tip="验证的7位数字（信用卡背后）",help="",secret="自动生成")
	private Integer creditcardAddition=getRanom();


	@Column(name = "credit_grant",precision=19,scale=6)
	@Info(label="长期授信额度",placeholder="长期授信额度",tip="长期授信的额度",help="",secret="")	
	private BigDecimal creditGrant;//授信总额
	
	
	@Column(name="credit_balance",precision=19,scale=6)
	@Info(label="可用信用额度",placeholder="",tip="就像信用卡一样，有可用的额度",help="",secret="随着每一笔贷款和每次调整临时授信、调整永久授信的确认时而更改")	
	private BigDecimal creditBalance;
	
	
	@Column(name="credit_frozen",precision=19,scale=6)
	@Info(label="冻结信用额度",placeholder="",tip="冻结信用额度",help="",secret="随着每一笔贷款和每次调整临时授信、调整永久授信的申请时而更改")	
	private BigDecimal creditFrozen;
	

	@Column(name="credit_temporary",precision=19,scale=6)
	@Info(label="临时授信额度",placeholder="授信额度",tip="临时授信的额度",help="",secret="是一个固定值 ，目前的一次性授信贷款都是用这个字段")	
	private BigDecimal creditTemporary;//临时额度
	
	@Column(name="credit_temporary_date")
	@Info(label="临时授信过期时间",placeholder="授信额度",tip="临时授信的额度",help="",secret="计息到贷款结束时")	
	private Date creditTemporaryDate;//临时额度
	
	
	@JoinColumn(name = "user_id")
	@ManyToOne
	@Info(label="所属用户ID",placeholder="",tip="",help="",secret="")	
	private User user;
	
	
	@JoinColumn(name="enterprise_id")
	@ManyToOne
	@Info(label="实体企业",placeholder="",tip="实体企业",help="",secret="关联到实体企业")	
	private Enterprise enterprise;
	
	@ManyToOne
	@JoinColumn(name="person_id")
	@Info(label="自然人",placeholder="",tip="自然人",help="以身份证为唯一标识的公民",secret="")
	private Person person;	

	@Lob
	@Info(label="说明",placeholder="填写一些公开的说明",tip="填写一些公开的说明",help="填写一些公开的说明",secret="")
	private String description;
		
	
	@Info(label="排序",placeholder="",tip="",help="",secret="")
	private Integer sort;

	

	@Column(name="expiry_date")
	@Info(label="授信过期时间",placeholder="",tip="即账户永久不可使用的过期时间",help="",secret="与克隆任务有关")
	private Date expiryDate;//过期时间
	
	@ManyToOne
	@JoinColumn(name = "company_id")
	@Info(label="所属公司",placeholder="",tip="",help="",secret="")
	private Company company;
	
	
	@Column(name="allow_debtor_loan")
	@Info(flag=FLAG.True,label="是",placeholder="",tip="",help="",secret="")
	@Info(flag=FLAG.False,label="否",placeholder="",tip="",help="",secret="")
	@Info(flag=FLAG.self,label="是否允许借方主动借",placeholder="",tip="",help="",secret="即用户可否创建贷款")
	private Boolean allowDebtorLoan;//是否允许借方主动提款

	@Column(name="allow_loan_number")
	@Info(label="充许最大的贷款笔数",placeholder="充许最大的贷款笔数",tip="充许最大的贷款笔数",help="",secret="在远期的时候会使用")
	private BigDecimal allowLoanNumber;
	
		 
	@Lob
	@Info(label="第三方平台上的相关信息",placeholder="",tip="",help="JSON序列化存储",secret="")
	private String information;
	
	@OneToMany(mappedBy = "creditcard")
	private Set<Loan> loans;

	@OneToMany(mappedBy = "creditcard")
	private Set<Creditadjust> creditadjusts;

	@OneToMany(mappedBy = "creditcard")
	private Set<Creditrepayment> creditrepayments;

	public Set<Creditrepayment> getCreditrepayments() {
		return creditrepayments;
	}

	public void setCreditrepayments(Set<Creditrepayment> creditrepayments) {
		this.creditrepayments = creditrepayments;
	}

	public Integer getCreditcardAddition() {
		return creditcardAddition;
	}

	public void setCreditcardAddition(Integer creditcardAddition) {
		this.creditcardAddition = creditcardAddition;
	}

	
	
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public BigDecimal getCreditTemporary() {
		return creditTemporary;
	}

	public void setCreditTemporary(BigDecimal creditTemporary) {
		this.creditTemporary = creditTemporary;
	}

	public Date getCreditTemporaryDate() {
		return creditTemporaryDate;
	}
	
	public void setCreditTemporaryDate(Date creditTemporaryDate) {
		this.creditTemporaryDate = creditTemporaryDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}
	
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public BigDecimal getAllowLoanNumber() {
		return allowLoanNumber;
	}

	public void setAllowLoanNumber(BigDecimal allowLoanNumber) {
		this.allowLoanNumber = allowLoanNumber;
	}

	

	public Boolean getAllowDebtorLoan() {
		return allowDebtorLoan;
	}

	public void setAllowDebtorLoan(Boolean allowDebtorLoan) {
		this.allowDebtorLoan = allowDebtorLoan;
	}





	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}



	public String getCreditcardIdentity() {
		return creditcardIdentity;
	}

	public void setCreditcardIdentity(String creditcardtIdentity) {
		if (creditcardtIdentity!=null&&!creditcardtIdentity.equals("")) {
			//this.creditcardIdentity=UUID.randomUUID();
			this.creditcardIdentity = creditcardtIdentity;
			return;
		}

		Random random=new Random();
		String first=System.currentTimeMillis()+""+(random.nextInt(899)+100);
		this.creditcardIdentity=first;
	}

	
	public BigDecimal getCreditBalance() {
		return creditBalance;
	}

	public void setCreditBalance(BigDecimal creditBalance) {
		this.creditBalance = creditBalance;
	}

	public BigDecimal getCreditFrozen() {
		return creditFrozen;
	}

	public void setCreditFrozen(BigDecimal creditFrozen) {
		this.creditFrozen = creditFrozen;
	}

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}




	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public BigDecimal getCreditGrant() {
		return creditGrant;
	}

	public void setCreditGrant(BigDecimal creditGrant) {
		this.creditGrant = creditGrant;
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
		Creditcard.service = (AbstractWorkflowService) service;

	}
	@JsonIgnore
	@Transient
	@Override
	public CreditcardLog getLogInstance() {
		return new CreditcardLog();
	}
	@JsonIgnore
	@Transient
	@Override
	public LogRepository getLogRepository() {
		return new Creditcard().getLogRepository();
	}
	
	@Override
	public void setLogRepository(LogRepository logRepository) {
		Creditcard.logRepository=logRepository;
	}
	private Integer getRanom(){
		return (int)(Math.random()*9999999)+1000000;
	}
}