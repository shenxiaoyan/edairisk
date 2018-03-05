package com.liyang.domain.ordersbdy;

import com.liyang.domain.base.AbstractWorkflowAct;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="ordersbdy_act")
@Cacheable
public class OrdersbdyAct extends AbstractWorkflowAct<OrdersbdyState,OrdersbdyWorkflow> {

	public OrdersbdyAct(String label, String actCode, Integer sort, ActGroup actGroup) {
		super(label, actCode, sort,actGroup);
		// TODO Auto-generated constructor stub
	}
	public OrdersbdyAct(){
		super();
	}
	
}
