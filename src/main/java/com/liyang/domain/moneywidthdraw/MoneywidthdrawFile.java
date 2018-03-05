package com.liyang.domain.moneywidthdraw;

import com.liyang.domain.base.AbstractWorkflowFile;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserAct;
import com.liyang.domain.user.UserLog;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="moneywidthdraw_file")
public class MoneywidthdrawFile extends AbstractWorkflowFile<Moneywidthdraw,MoneywidthdrawAct,MoneywidthdrawLog>  {


	
}
