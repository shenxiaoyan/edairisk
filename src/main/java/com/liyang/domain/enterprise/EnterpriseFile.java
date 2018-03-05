package com.liyang.domain.enterprise;

import com.liyang.domain.base.AbstractWorkflowFile;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="enterprise_file")
public class EnterpriseFile extends AbstractWorkflowFile<Enterprise,EnterpriseAct,EnterpriseLog>  {


	
}
