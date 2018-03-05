package com.liyang.domain.creditadjust;

import com.liyang.domain.base.AbstractWorkflowLog;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="creditadjust_log")
@Cacheable
public class CreditadjustLog extends AbstractWorkflowLog<Creditadjust,CreditadjustWorkflow,CreditadjustState,CreditadjustAct,CreditadjustFile>  {


	
}
