package com.liyang.domain.moneywidthdraw;

import com.liyang.domain.base.AbstractWorkflowState;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "moneywidthdraw_state")
@Cacheable
public class MoneywidthdrawState extends AbstractWorkflowState<Moneywidthdraw, MoneywidthdrawWorkflow, MoneywidthdrawAct> {

	public MoneywidthdrawState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}

	public MoneywidthdrawState() {
		super();
	}

}
