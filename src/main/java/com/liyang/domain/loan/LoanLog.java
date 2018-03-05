package com.liyang.domain.loan;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowLog;


@Entity
@Table(name="loan_log")
@Cacheable
public class LoanLog extends AbstractWorkflowLog<Loan,LoanWorkflow,LoanState,LoanAct,LoanFile>  {


	
}
