package com.liyang.domain.loanstorebond;

import com.liyang.domain.base.AbstractWorkflowLog;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="loan_storebond_log")
@Cacheable
public class LoanStorebondLog extends AbstractWorkflowLog<LoanStorebond,LoanStorebondWorkflow,LoanStorebondState,LoanStorebondAct,LoanStorebondFile>  {


	
}
