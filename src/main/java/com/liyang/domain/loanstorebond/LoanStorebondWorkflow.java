package com.liyang.domain.loanstorebond;

import com.liyang.domain.base.AbstractWorkflow;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="creditcard_workflow")
@Cacheable
public class LoanStorebondWorkflow extends AbstractWorkflow<LoanStorebond, LoanStorebondState> {



}
