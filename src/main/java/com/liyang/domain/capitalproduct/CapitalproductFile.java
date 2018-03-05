package com.liyang.domain.capitalproduct;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowFile;
import com.liyang.domain.base.AbstractWorkflowLog;


@Entity
@Table(name="capitalproduct_file")
public class CapitalproductFile extends AbstractWorkflowFile<Capitalproduct,CapitalproductAct,CapitalproductLog>  {


	
}
