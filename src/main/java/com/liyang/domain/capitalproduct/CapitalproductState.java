package com.liyang.domain.capitalproduct;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowState;

@Entity
@Table(name = "capitalproduct_state")
@Cacheable
public class CapitalproductState extends AbstractWorkflowState<Capitalproduct, CapitalproductWorkflow, CapitalproductAct> {

	public CapitalproductState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}

	public CapitalproductState() {
		super();
	}

}
