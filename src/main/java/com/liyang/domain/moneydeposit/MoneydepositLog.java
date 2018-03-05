package com.liyang.domain.moneydeposit;

import com.liyang.domain.base.AbstractWorkflowLog;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="moneydeposit_log")
@Cacheable
public class MoneydepositLog extends AbstractWorkflowLog<Moneydeposit,MoneydepositWorkflow,MoneydepositState,MoneydepositAct,MoneydepositFile>  {


	
}
