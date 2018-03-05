package com.liyang.domain.account;

import com.liyang.domain.base.AbstractWorkflowFile;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserAct;
import com.liyang.domain.user.UserLog;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="account_file")
public class AccountFile extends AbstractWorkflowFile<Account,AccountAct,AccountLog>  {


	
}
