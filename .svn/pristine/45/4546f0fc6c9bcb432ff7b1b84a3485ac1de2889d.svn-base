package com.liyang.domain.account;

import com.liyang.annotation.Info;
import com.liyang.domain.base.BaseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Table(name = "account_changelog")
public class AccountChangelog extends BaseEntity {
	
    @JoinColumn(name = "account_id")
    @ManyToOne
	@Info(label="账户id",placeholder="",tip="",help="",secret="")						    
    private Account account;
    
    @Column(name = "money_balance_change",precision=19,scale=6)
    @Info(label="账户可用资金变动",placeholder="",tip="",help="",secret="")						    
    private BigDecimal moneyBalanceChange;

    @Column(name = "money_frozen_change",precision=19,scale=6)
    @Info(label="账户不可用资金变动",placeholder="",tip="",help="",secret="")						    
    private BigDecimal moneyFrozenChange;

    
    
//    @Column(name = "credit_temporary_date_change")
//    @Info(label="账户临时授信有效期变动",placeholder="",tip="",help="",secret="")						    
//    private Date creditTemporaryDateChange;     
    

    @Info(label="内部备注",placeholder="",tip="",help="",secret="")						    
    private String comment;

    @Override
    public String toString() {
//        return "AccountChangeLog{" +
//                "account=" + account +
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
    	sb.append("AccountChangeLog{")
        .append("account=") .append(account)
        .append(",).append(moneyBalanceChange=") .append(moneyBalanceChange)
        .append(",).append(moneyFrozenChange=") .append(moneyFrozenChange)
        .append('\'')
        .append(",).append(comment='") .append(comment) .append('\'')
        .append('}');
    	return sb.toString();
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

	public BigDecimal getMoneyBalanceChange() {
		return moneyBalanceChange;
	}

	public void setMoneyBalanceChange(BigDecimal moneyBalanceChange) {
		this.moneyBalanceChange = moneyBalanceChange;
	}

	public BigDecimal getMoneyFrozenChange() {
		return moneyFrozenChange;
	}

	public void setMoneyFrozenChange(BigDecimal moneyFrozenChange) {
		this.moneyFrozenChange = moneyFrozenChange;
	}


	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}



	

}
