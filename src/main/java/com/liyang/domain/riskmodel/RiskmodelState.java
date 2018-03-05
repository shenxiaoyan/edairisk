package com.liyang.domain.riskmodel;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractAuditorState;
@Entity
@Table(name = "riskmodel_state")
public class RiskmodelState extends AbstractAuditorState<Riskmodel,RiskmodelAct> {

	public RiskmodelState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}
	public RiskmodelState(){
		super();
	}

}
