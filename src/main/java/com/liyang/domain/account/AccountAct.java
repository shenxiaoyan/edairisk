package com.liyang.domain.account;

import com.liyang.domain.base.AbstractWorkflowAct;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="account_act")
@Cacheable
public class AccountAct extends AbstractWorkflowAct<AccountState,AccountWorkflow> {

	public AccountAct(String label, String actCode, Integer sort, ActGroup actGroup) {
		super(label, actCode, sort,actGroup);
		// TODO Auto-generated constructor stub
	}
	public AccountAct(){
		super();
	}
	
}
