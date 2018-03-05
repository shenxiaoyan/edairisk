package com.liyang.domain.creditadjust;

import com.liyang.domain.base.AbstractWorkflow;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="creditadjust_workflow")
@Cacheable
public class CreditadjustWorkflow extends AbstractWorkflow<Creditadjust, CreditadjustState> {



}
