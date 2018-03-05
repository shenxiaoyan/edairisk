package com.liyang.domain.ordercddloan;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowState;

@Entity
@Table(name = "ordercddloan_state")
@Cacheable
public class OrdercddloanState extends AbstractWorkflowState<Ordercddloan, OrdercddloanWorkflow, OrdercddloanAct> {

	public OrdercddloanState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}

	public OrdercddloanState() {
		super();
	}

}
