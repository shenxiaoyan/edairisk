package com.liyang.domain.creditcard;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.liyang.annotation.Info;
import com.liyang.domain.base.BaseEntity;


@Entity
@Table(name = "creditcard_changelog")
@Info(label="信用卡变动记录",placeholder="",tip="",help="",secret="")						    
public class CreditcardChangelog extends BaseEntity {
	
    @JoinColumn(name = "creditcard_id")
    @ManyToOne
	@Info(label="信用卡id",placeholder="",tip="",help="",secret="")						    
    private Creditcard creditcard;
    
    @Column(name = "credit_balance_change")
    @Info(label="账户可用信用值变动",placeholder="",tip="",help="",secret="")						    
    private BigDecimal creditBalanceChange;
    
    @Column(name = "credit_frozen_change")
    @Info(label="账户不可用信用值变动",placeholder="",tip="",help="",secret="")						    
    private BigDecimal creditFrozenChange;   
    
    
    @Column(name = "credit_grant_change")
    @Info(label="账户授信值变动",placeholder="",tip="",help="",secret="")						    
    private BigDecimal creditGrantChange;
    
    @Column(name = "credit_temporary_change")
    @Info(label="账户临时授信变动",placeholder="",tip="",help="",secret="")						    
    private BigDecimal creditTemporaryChange;  
    
    @Column(name = "credit_temporary_new_date")
    @Info(label="账户临时授信有效期变动",placeholder="",tip="",help="",secret="")						    
    private Date creditTemporaryNewDate;     
    

    @Info(label="内部备注",placeholder="",tip="",help="",secret="")						    
    private String comment;

    @Override
    public String toString() {
//        return "CreditcardChangeLog{" +
//                "creditcard=" + creditcard +
//                ", balanceChange=" + balanceChange +
//                ", balanceRecord=" + balanceRecord +
//                ", frozenChange=" + frozenChange +
//                ", frozenRecord=" + frozenRecord +
//                ", changeType='" + changeType + '\'' +
//                ", creditExpectBalanceChange=" + creditExpectBalanceChange +
//                ", creditGrantBalanceChange=" + creditGrantBalanceChange +
//                ", reasonContent='" + reasonContent + '\'' +
//                ", description='" + description + '\'' +
//                '}';
    	StringBuilder sb = new StringBuilder();
    	sb.append("CreditcardChangeLog{")
        .append("creditcard=") .append(creditcard)
        .append('\'')
        .append(",).append(creditBalanceChange=") .append(creditBalanceChange)
        .append(",).append(creditGrantChange=") .append(creditGrantChange)
        .append(",).append(creditTemporaryChange=") .append(creditTemporaryChange)
        .append(",).append(comment='") .append(comment) .append('\'')
        .append('}');
    	return sb.toString();
    }

    public Creditcard getCreditcard() {
        return creditcard;
    }

    public void setCreditcard(Creditcard creditcard) {
        this.creditcard = creditcard;
    }

	
	public BigDecimal getCreditBalanceChange() {
		return creditBalanceChange;
	}

	public void setCreditBalanceChange(BigDecimal creditBalanceChange) {
		this.creditBalanceChange = creditBalanceChange;
	}

	public BigDecimal getCreditFrozenChange() {
		return creditFrozenChange;
	}

	public void setCreditFrozenChange(BigDecimal creditFrozenChange) {
		this.creditFrozenChange = creditFrozenChange;
	}

	public BigDecimal getCreditGrantChange() {
		return creditGrantChange;
	}

	public void setCreditGrantChange(BigDecimal creditGrantChange) {
		this.creditGrantChange = creditGrantChange;
	}

	public BigDecimal getCreditTemporaryChange() {
		return creditTemporaryChange;
	}

	public void setCreditTemporaryChange(BigDecimal creditTemporaryChange) {
		this.creditTemporaryChange = creditTemporaryChange;
	}


	public Date getCreditTemporaryNewDate() {
		return creditTemporaryNewDate;
	}

	public void setCreditTemporaryNewDate(Date creditTemporaryNewDate) {
		this.creditTemporaryNewDate = creditTemporaryNewDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}



	

}
