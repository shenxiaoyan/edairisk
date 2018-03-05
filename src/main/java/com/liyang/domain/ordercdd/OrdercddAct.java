package com.liyang.domain.ordercdd;

import com.liyang.domain.base.AbstractWorkflowAct;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="ordercdd_act")
@Cacheable
public class OrdercddAct extends AbstractWorkflowAct<OrdercddState,OrdercddWorkflow> {

	public OrdercddAct(String label, String actCode, Integer sort, ActGroup actGroup) {
		super(label, actCode, sort,actGroup);
		// TODO Auto-generated constructor stub
	}
	public OrdercddAct(){
		super();
	}
	
}
