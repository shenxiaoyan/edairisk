package com.liyang.domain.loan;

import com.liyang.domain.base.AbstractWorkflowFile;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="loan_file")
public class LoanFile extends AbstractWorkflowFile<Loan,LoanAct,LoanLog>  {


	
}
