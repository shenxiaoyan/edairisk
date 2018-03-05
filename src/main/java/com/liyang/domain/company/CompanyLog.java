package com.liyang.domain.company;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractAuditorLog;


@Entity
@Table(name="company_log")
public class CompanyLog extends AbstractAuditorLog<Company,CompanyState,CompanyAct>  {


}
