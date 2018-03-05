package com.liyang.domain.company;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractAuditorState;
@Entity
@Table(name = "company_state")
public class CompanyState extends AbstractAuditorState<Company, CompanyAct> {

	public CompanyState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}
	public CompanyState(){
		super();
	}


}
