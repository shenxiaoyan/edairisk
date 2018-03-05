package com.liyang.domain.moneyadjust;

import com.liyang.domain.base.AbstractWorkflowFile;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserAct;
import com.liyang.domain.user.UserLog;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="moneyadjust_file")
public class MoneyadjustFile extends AbstractWorkflowFile<Moneyadjust,MoneyadjustAct,MoneyadjustLog>  {


	
}
