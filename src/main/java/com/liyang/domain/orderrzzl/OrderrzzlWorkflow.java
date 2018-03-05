package com.liyang.domain.orderrzzl;

import com.liyang.domain.base.AbstractWorkflow;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="orderrzzl_workflow")
@Cacheable
public class OrderrzzlWorkflow extends AbstractWorkflow<Orderrzzl, OrderrzzlState> {



}
