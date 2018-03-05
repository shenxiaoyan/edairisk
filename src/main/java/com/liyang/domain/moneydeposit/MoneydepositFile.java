package com.liyang.domain.moneydeposit;

import com.liyang.domain.base.AbstractWorkflowFile;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserAct;
import com.liyang.domain.user.UserLog;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="moneydeposit_file")
public class MoneydepositFile extends AbstractWorkflowFile<Moneydeposit,MoneydepositAct,MoneydepositLog>  {


	
}
