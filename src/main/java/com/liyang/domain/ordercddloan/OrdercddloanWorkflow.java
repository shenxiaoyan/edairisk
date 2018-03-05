package com.liyang.domain.ordercddloan;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflow;

@Entity
@Table(name="ordercddloan_workflow")
@Cacheable
public class OrdercddloanWorkflow extends AbstractWorkflow<Ordercddloan, OrdercddloanState> {



}
