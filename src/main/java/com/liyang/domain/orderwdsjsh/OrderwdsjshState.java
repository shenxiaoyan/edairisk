package com.liyang.domain.orderwdsjsh;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowState;

@Entity
@Table(name = "orderwdsjsh_state")
@Cacheable
public class OrderwdsjshState extends AbstractWorkflowState<Orderwdsjsh, OrderwdsjshWorkflow, OrderwdsjshAct> {

	public OrderwdsjshState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}

	public OrderwdsjshState() {
		super();
	}

}
