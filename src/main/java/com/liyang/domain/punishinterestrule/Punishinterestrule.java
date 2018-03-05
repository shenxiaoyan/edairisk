package com.liyang.domain.punishinterestrule;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.liyang.annotation.Info;
import com.liyang.domain.base.BaseEntity;

/**
 * Created by win7 on 2017-07-27.
 */
@Table(name = "punishinterestrule")
@Entity
@Info(label="罚息规则",placeholder="",tip="",help="",secret="每一笔贷款")
public class Punishinterestrule extends BaseEntity {

	@Info(label="别名",placeholder="",tip="",help="",secret="")
	private String alias;

	
	@Enumerated(EnumType.STRING)
	@Column(name="base_principal_code")
	private BasePrincipalCode basePrincipalCode;
	

	
	@Enumerated(EnumType.STRING)
	@Column(name="start_time_code")
	private StartTimeCode startTimeCode;
	
   
    
    private String rate;
    @Lob
    private String comment;
    private Integer sort;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }



    public BasePrincipalCode getBasePrincipalCode() {
		return basePrincipalCode;
	}

	public void setBasePrincipalCode(BasePrincipalCode basePrincipalCode) {
		this.basePrincipalCode = basePrincipalCode;
	}

	public StartTimeCode getStartTimeCode() {
		return startTimeCode;
	}

	public void setStartTimeCode(StartTimeCode startTimeCode) {
		this.startTimeCode = startTimeCode;
	}

	public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
    
    private static  enum BasePrincipalCode{
        @Info(label="全额本金",placeholder="",tip="",help="",secret="")
        WHOLE_PRINCIPAL,
        @Info(label="剩余本金",placeholder="",tip="",help="",secret="")
        LEFT_PRINCIPAL,
        @Info(label="当期本金",placeholder="",tip="",help="",secret="")
        REPAYMENT_PRINCIPAL,
        @Info(label="当期剩余本金",placeholder="",tip="",help="",secret="")
        REPAYMENT_LEFT_PRINCIPAL,
        @Info(label="当期本息",placeholder="",tip="",help="",secret="")
        REPAYMENT_PRINCIPAL_INTEREST    
        }
    
    
    private static  enum StartTimeCode{
        @Info(label="借方还款日",placeholder="",tip="",help="",secret="")
        DEBTOR_REPAYMENT_DAY
        }       
}
