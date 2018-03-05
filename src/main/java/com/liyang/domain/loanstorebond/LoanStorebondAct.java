package com.liyang.domain.loanstorebond;

import com.liyang.domain.base.AbstractWorkflowAct;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="loan_storebond_act")
@Cacheable
public class LoanStorebondAct extends AbstractWorkflowAct<LoanStorebondState,LoanStorebondWorkflow> {

	public LoanStorebondAct(String label, String actCode, Integer sort, ActGroup actGroup) {
		super(label, actCode, sort,actGroup);
		// TODO Auto-generated constructor stub
	}
	public LoanStorebondAct(){
		super();
	}
	
}
