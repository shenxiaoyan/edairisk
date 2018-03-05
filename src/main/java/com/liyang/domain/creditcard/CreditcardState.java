package com.liyang.domain.creditcard;

import com.liyang.domain.base.AbstractWorkflowState;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "creditcard_state")
@Cacheable
public class CreditcardState extends AbstractWorkflowState<Creditcard, CreditcardWorkflow, CreditcardAct> {

	public CreditcardState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}

	public CreditcardState() {
		super();
	}

}
