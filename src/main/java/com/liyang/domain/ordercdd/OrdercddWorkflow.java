package com.liyang.domain.ordercdd;

import com.liyang.domain.base.AbstractWorkflow;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="ordercdd_workflow")
@Cacheable
public class OrdercddWorkflow extends AbstractWorkflow<Ordercdd, OrdercddState> {



}
