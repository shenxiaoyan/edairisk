package com.liyang.domain.orderrzzl;

import com.liyang.domain.base.AbstractWorkflowState;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "orderrzzl_state")
@Cacheable
public class OrderrzzlState extends AbstractWorkflowState<Orderrzzl, OrderrzzlWorkflow, OrderrzzlAct> {

	public OrderrzzlState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}

	public OrderrzzlState() {
		super();
	}

}
