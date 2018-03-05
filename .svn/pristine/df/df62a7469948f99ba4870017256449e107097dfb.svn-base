package com.liyang.domain.bankcard;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.annotation.Info.FLAG;
import com.liyang.domain.bank.Bank;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.AbstractWorkflowEntity;
import com.liyang.domain.base.BaseEntity;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.user.User;
import com.liyang.service.AbstractAuditorService;
import com.liyang.service.AbstractWorkflowService;

/**
 * Created by win7 on 2017-07-26.
 */
@Entity
@Table(name = "bankcard")
@Info(label="银行卡",placeholder="",tip="外部资金账户（工农商建、支付宝）",help="",secret="")	   
public class Bankcard extends AbstractAuditorEntity<BankcardState,BankcardAct,BankcardLog> {

	@Transient
	private static LogRepository logRepository;

	@Transient
	private static AbstractAuditorService service;

	@ManyToOne
	@JoinColumn(name = "bank_id")
	@Info(label="所属银行",placeholder="",tip="",help="工农商建线下账户或支付宝、财付通等线上账户",secret="")
	private Bank bank;
	
	@JoinColumn(name = "user_id")
	@ManyToOne
	@Info(label="所属用户ID",placeholder="",tip="",help="",secret="")	
	private User user;
	
	@Column(name = "real_name")
    @Info(label="持有人姓名",placeholder="",tip="",help="",secret="")	   
    private String realName;
	
	@Column(name = "branch_name")
    @Info(label="支行名称",placeholder="",tip="",help="",secret="")	   
    private String branchName;
	
	@Column(name = "account_identity")
    @Info(label="账户号码",placeholder="",tip="",help="",secret="")	   
    private String accountIdentity;
	
	
    @Lob
    @Column(name = "description")
    @Info(label="银行的说明",placeholder="",tip="",help="",secret="")	   
    private String description;
    
    @Column(name = "is_online")
	@Info(flag=FLAG.True,label="是",placeholder="",tip="",help="",secret="")
	@Info(flag=FLAG.False,label="否",placeholder="",tip="",help="",secret="")
	@Info(flag=FLAG.self,label="是否为网络银行账户",placeholder="",tip="",help="工农商建等传统银行的卡为否，支付宝/财富通等为是",secret="")	
	private Boolean isOnline=false;
    
    @Info(label="状态",placeholder="",tip="",help="-1为已删除，0为无效，1为有效",secret="state是工作流，status是简单状态")	   
    private Integer status;

    @Info(label="前台排序",placeholder="",tip="",help="",secret="")	   
    private Integer sort;      

    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public Boolean getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Boolean isOnline) {
		this.isOnline = isOnline;
	}



	public String getAccountIdentity() {
		return accountIdentity;
	}

	public void setAccountIdentity(String accountIdentity) {
		this.accountIdentity = accountIdentity;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
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

	@Override
	@JsonIgnore
	@Transient
	public BankcardLog getLogInstance() {
		// TODO Auto-generated method stub
		return new BankcardLog();
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
		Bankcard.logRepository = logRepository;
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
		Bankcard.service =service;

	}
}
