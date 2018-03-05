package com.liyang.domain.moneytransfer;

import com.liyang.domain.base.AbstractWorkflowState;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "moneytransfer_state")
@Cacheable
public class MoneytransferState extends AbstractWorkflowState<Moneytransfer, MoneytransferWorkflow, MoneytransferAct> {

	public MoneytransferState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}

	public MoneytransferState() {
		super();
	}

}
