package com.liyang.domain.enterprise;

import com.liyang.domain.base.AbstractAuditorLog;
import com.liyang.domain.base.AbstractWorkflowLog;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "enterprise_log")
//Orderwdxyd,OrderwdxydWorkflow,OrderwdxydState,OrderwdxydAct,OrderwdxydFile
public class EnterpriseLog extends AbstractWorkflowLog<Enterprise,EnterpriseWorkflow,EnterpriseState,EnterpriseAct,EnterpriseFile> {

}
