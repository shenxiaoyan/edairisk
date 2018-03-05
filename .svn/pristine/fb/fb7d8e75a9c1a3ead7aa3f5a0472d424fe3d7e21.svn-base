package com.liyang.domain.loan;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowAct;


@Entity
@Table(name="loan_act")
@Cacheable
public class LoanAct extends AbstractWorkflowAct<LoanState,LoanWorkflow> {

	public LoanAct(String label, String actCode, Integer sort, ActGroup actGroup) {
		super(label, actCode, sort,actGroup);
		// TODO Auto-generated constructor stub
	}
	public LoanAct(){
		super();
	}
	
}
