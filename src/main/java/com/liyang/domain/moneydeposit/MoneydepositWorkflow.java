package com.liyang.domain.moneydeposit;

import com.liyang.domain.base.AbstractWorkflow;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="moneydeposit_workflow")
@Cacheable
public class MoneydepositWorkflow extends AbstractWorkflow<Moneydeposit, MoneydepositState> {



}
