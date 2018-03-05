package com.liyang.domain.enterprise;

import com.liyang.domain.base.AbstractAuditorState;
import com.liyang.domain.base.AbstractWorkflowState;
import com.liyang.domain.person.Person;
import com.liyang.domain.person.PersonAct;
import com.liyang.domain.person.PersonWorkflow;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "enterprise_state")
//AbstractWorkflowState<Person,PersonWorkflow,PersonAct>
public class EnterpriseState extends AbstractWorkflowState<Enterprise,EnterpriseWorkflow,EnterpriseAct> {

	public EnterpriseState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}
	public EnterpriseState(){
		super();
	}
	/**
	 * 	public PersonState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}

	 public PersonState() {
		// TODO Auto-generated constructor stub
		 super();
	}

	 */

}
