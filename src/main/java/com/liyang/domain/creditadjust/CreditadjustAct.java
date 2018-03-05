package com.liyang.domain.creditadjust;

import com.liyang.domain.base.AbstractWorkflowAct;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="creditadjust_act")
@Cacheable
public class CreditadjustAct extends AbstractWorkflowAct<CreditadjustState,CreditadjustWorkflow> {

	public CreditadjustAct(String label, String actCode, Integer sort, ActGroup actGroup) {
		super(label, actCode, sort,actGroup);
		// TODO Auto-generated constructor stub
	}
	public CreditadjustAct(){
		super();
	}
	
}
