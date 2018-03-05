package com.liyang.domain.ordercddloan;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowAct;


@Entity
@Table(name="ordercddloan_act")
@Cacheable
public class OrdercddloanAct extends AbstractWorkflowAct<OrdercddloanState,OrdercddloanWorkflow> {

	public OrdercddloanAct(String label, String actCode, Integer sort, ActGroup actGroup) {
		super(label, actCode, sort,actGroup);
		// TODO Auto-generated constructor stub
	}
	public OrdercddloanAct(){
		super();
	}
	
}
