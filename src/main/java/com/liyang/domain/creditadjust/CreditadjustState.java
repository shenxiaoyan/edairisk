package com.liyang.domain.creditadjust;

import com.liyang.domain.base.AbstractWorkflowState;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "creditadjust_state")
@Cacheable
public class CreditadjustState extends AbstractWorkflowState<Creditadjust, CreditadjustWorkflow, CreditadjustAct> {

	public CreditadjustState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}

	public CreditadjustState() {
		super();
	}

}
