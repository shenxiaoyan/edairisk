package com.liyang.domain.orderwdsjsh;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowAct;


@Entity
@Table(name="orderwdsjsh_act")
@Cacheable
public class OrderwdsjshAct extends AbstractWorkflowAct<OrderwdsjshState,OrderwdsjshWorkflow> {

	public OrderwdsjshAct(String label, String actCode, Integer sort, ActGroup actGroup) {
		super(label, actCode, sort,actGroup);
		// TODO Auto-generated constructor stub
	}
	public OrderwdsjshAct(){
		super();
	}
	
}
