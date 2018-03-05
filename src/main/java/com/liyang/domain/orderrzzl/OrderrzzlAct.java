package com.liyang.domain.orderrzzl;

import com.liyang.domain.base.AbstractWorkflowAct;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="orderrzzl_act")
@Cacheable
public class OrderrzzlAct extends AbstractWorkflowAct<OrderrzzlState,OrderrzzlWorkflow> {

	public OrderrzzlAct(String label, String actCode, Integer sort, ActGroup actGroup) {
		super(label, actCode, sort,actGroup);
		// TODO Auto-generated constructor stub
	}
	public OrderrzzlAct(){
		super();
	}
	
}
