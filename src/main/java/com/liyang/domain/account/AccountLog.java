package com.liyang.domain.account;

import com.liyang.domain.base.AbstractWorkflowLog;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="account_log")
@Cacheable
public class AccountLog extends AbstractWorkflowLog<Account,AccountWorkflow,AccountState,AccountAct,AccountFile>  {


	
}
