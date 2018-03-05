package com.liyang.domain.creditrepayplan;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflow;

@Entity
@Table(name="creditrepayplan_workflow")
@Cacheable
public class CreditrepayplanWorkflow extends AbstractWorkflow<Creditrepayplan, CreditrepayplanState> {



}
