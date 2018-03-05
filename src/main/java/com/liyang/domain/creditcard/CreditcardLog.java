package com.liyang.domain.creditcard;

import com.liyang.domain.base.AbstractWorkflowLog;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="creditcard_log")
@Cacheable
public class CreditcardLog extends AbstractWorkflowLog<Creditcard,CreditcardWorkflow,CreditcardState,CreditcardAct,CreditcardFile>  {


	
}
