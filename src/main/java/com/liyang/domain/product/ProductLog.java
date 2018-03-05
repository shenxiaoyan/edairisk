package com.liyang.domain.product;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowLog;


@Entity
@Table(name="product_log")
public class ProductLog extends AbstractWorkflowLog<Product,ProductWorkflow,ProductState,ProductAct,ProductFile>  {


	
}
