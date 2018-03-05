package com.liyang.domain.product;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowFile;
import com.liyang.domain.base.AbstractWorkflowLog;


@Entity
@Table(name="product_file")
public class ProductFile extends AbstractWorkflowFile<Product,ProductAct,ProductLog>  {


	
}
