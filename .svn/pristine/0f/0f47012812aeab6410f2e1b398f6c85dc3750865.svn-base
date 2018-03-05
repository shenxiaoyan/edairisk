package com.liyang.domain.loan;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowState;

@Entity
@Table(name = "loan_state")
@Cacheable
public class LoanState extends AbstractWorkflowState<Loan, LoanWorkflow, LoanAct> {

	public LoanState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}

	public LoanState() {
		super();
	}

}
