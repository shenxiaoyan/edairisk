package com.liyang.domain.creditcard;

import com.liyang.domain.base.AbstractWorkflowFile;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserAct;
import com.liyang.domain.user.UserLog;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="creditcard_file")
public class CreditcardFile extends AbstractWorkflowFile<Creditcard,CreditcardAct,CreditcardLog>  {


	
}
