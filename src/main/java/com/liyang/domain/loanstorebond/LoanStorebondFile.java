package com.liyang.domain.loanstorebond;

import com.liyang.domain.base.AbstractWorkflowFile;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="loan_storebond_file")
public class LoanStorebondFile extends AbstractWorkflowFile<LoanStorebond,LoanStorebondAct,LoanStorebondLog>  {


	
}
