package com.liyang.domain.creditrepayplan;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowFile;


@Entity
@Table(name="creditrepayplan_file")
public class CreditrepayplanFile extends AbstractWorkflowFile<Creditrepayplan,CreditrepayplanAct,CreditrepayplanLog>  {


	
}
