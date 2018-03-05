package com.liyang.domain.bankcard;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.liyang.annotation.Info;
import com.liyang.domain.base.BaseEntity;


@Entity
@Table(name = "bankcard_changelog")
@Info(label="银行卡变动记录",placeholder="",tip="",help="",secret="")
public class BankcardChangelog extends BaseEntity {
	
    @JoinColumn(name = "bankcard_id")
    @ManyToOne
	@Info(label="银行卡id",placeholder="",tip="",help="",secret="")						    
    private Bankcard bankcard;
  
    
    @Column(name = "money_change",precision=19,scale=6)
    @Info(label="资金变动值",placeholder="",tip="",help="",secret="")						    
    private BigDecimal moneyChange;


    @Info(label="内部备注",placeholder="",tip="",help="",secret="")						    
    private String comment;

    

	public BigDecimal getMoneyChange() {
		return moneyChange;
	}

	public void setMoneyChange(BigDecimal moneyChange) {
		this.moneyChange = moneyChange;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}



	

}
