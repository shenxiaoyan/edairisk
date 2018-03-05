package com.liyang.domain.orderwdsjsh;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowLog;
//wdxyd
//wdmdbt
//wdsjsh
@Entity
@Table(name="orderwdsjsh_log")
@Cacheable
public class OrderwdsjshLog extends AbstractWorkflowLog<Orderwdsjsh,OrderwdsjshWorkflow,OrderwdsjshState,OrderwdsjshAct,OrderwdsjshFile>  {


	
}
