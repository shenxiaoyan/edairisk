package com.liyang.domain.creditrepayplan;

import com.liyang.domain.base.AbstractWorkflowLog;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="creditrepayplan_log")
@Cacheable
public class CreditrepayplanLog extends AbstractWorkflowLog<Creditrepayplan,CreditrepayplanWorkflow,CreditrepayplanState,CreditrepayplanAct,CreditrepayplanFile>  {


	
}
