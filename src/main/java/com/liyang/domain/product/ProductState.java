package com.liyang.domain.product;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowState;

@Entity
@Table(name = "product_state")
@Cacheable
public class ProductState extends AbstractWorkflowState<Product, ProductWorkflow, ProductAct> {

	public ProductState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}

	public ProductState() {
		super();
	}

}
