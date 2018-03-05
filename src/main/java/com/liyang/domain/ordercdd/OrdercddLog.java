package com.liyang.domain.ordercdd;

import com.liyang.domain.base.AbstractWorkflowLog;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

//wdxyd
//wdmdbt
//wdsjsh
@Entity
@Table(name="ordercdd_log")
@Cacheable
public class OrdercddLog extends AbstractWorkflowLog<Ordercdd,OrdercddWorkflow,OrdercddState,OrdercddAct,OrdercddFile>  {


	
}
