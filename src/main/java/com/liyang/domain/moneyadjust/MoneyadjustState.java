package com.liyang.domain.moneyadjust;

import com.liyang.domain.base.AbstractWorkflowState;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "moneyadjust_state")
@Cacheable
public class MoneyadjustState extends AbstractWorkflowState<Moneyadjust, MoneyadjustWorkflow, MoneyadjustAct> {

	public MoneyadjustState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}

	public MoneyadjustState() {
		super();
	}

}
