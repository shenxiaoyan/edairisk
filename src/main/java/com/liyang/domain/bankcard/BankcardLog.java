package com.liyang.domain.bankcard;

import com.liyang.domain.base.AbstractAuditorLog;
import com.liyang.domain.base.AbstractWorkflowLog;
import com.liyang.domain.ordercdd.*;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

//wdxyd
//wdmdbt
//wdsjsh
@Entity
@Table(name="bankcard_log")
@Cacheable
public class BankcardLog extends AbstractAuditorLog<Bankcard,OrdercddState,OrdercddAct> {


	
}
