package com.liyang.domain.person;

import com.liyang.domain.base.AbstractAuditorState;
import com.liyang.domain.base.AbstractWorkflowState;
import com.liyang.domain.orderwdxyd.Orderwdxyd;
import com.liyang.domain.orderwdxyd.OrderwdxydAct;
import com.liyang.domain.orderwdxyd.OrderwdxydWorkflow;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "person_state")
//Orderwdxyd, OrderwdxydWorkflow, OrderwdxydAct
public class PersonState extends AbstractWorkflowState<Person,PersonWorkflow,PersonAct> {
	public PersonState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}

	 public PersonState() {
		// TODO Auto-generated constructor stub
		 super();
	}


}
