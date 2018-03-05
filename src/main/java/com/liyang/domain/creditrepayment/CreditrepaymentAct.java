package com.liyang.domain.creditrepayment;

import com.liyang.domain.base.AbstractWorkflowAct;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="creditrepayment_act")
@Cacheable
public class CreditrepaymentAct extends AbstractWorkflowAct<CreditrepaymentState,CreditrepaymentWorkflow> {

	public CreditrepaymentAct(String label, String actCode, Integer sort, ActGroup actGroup) {
		super(label, actCode, sort,actGroup);
		// TODO Auto-generated constructor stub
	}
	public CreditrepaymentAct(){
		super();
	}
	
}
