package com.liyang.domain.loanstorebond;

import com.liyang.domain.base.AbstractWorkflowState;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "loan_storebond_state")
@Cacheable
public class LoanStorebondState extends AbstractWorkflowState<LoanStorebond, LoanStorebondWorkflow, LoanStorebondAct> {

	public LoanStorebondState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}

	public LoanStorebondState() {
		super();
	}

}
