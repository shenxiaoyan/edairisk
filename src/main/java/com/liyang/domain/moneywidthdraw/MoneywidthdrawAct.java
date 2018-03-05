package com.liyang.domain.moneywidthdraw;

import com.liyang.domain.base.AbstractWorkflowAct;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="moneywidthdraw_act")
@Cacheable
public class MoneywidthdrawAct extends AbstractWorkflowAct<MoneywidthdrawState,MoneywidthdrawWorkflow> {

	public MoneywidthdrawAct(String label, String actCode, Integer sort, ActGroup actGroup) {
		super(label, actCode, sort,actGroup);
		// TODO Auto-generated constructor stub
	}
	public MoneywidthdrawAct(){
		super();
	}
	
}
