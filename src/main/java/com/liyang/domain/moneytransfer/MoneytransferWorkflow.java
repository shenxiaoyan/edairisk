package com.liyang.domain.moneytransfer;

import com.liyang.domain.base.AbstractWorkflow;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="moneytransfer_workflow")
@Cacheable
public class MoneytransferWorkflow extends AbstractWorkflow<Moneytransfer, MoneytransferState> {



}
