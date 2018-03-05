package com.liyang.domain.bankcard;

import com.liyang.domain.base.AbstractAuditorState;
import com.liyang.domain.base.AbstractWorkflowState;
import com.liyang.domain.creditcard.Creditcard;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "bankcard_state")
@Cacheable
public class BankcardState extends AbstractAuditorState<Bankcard,BankcardAct> {

	public BankcardState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}

	public BankcardState() {
		super();
	}

}
