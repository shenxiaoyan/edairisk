package com.liyang.domain.department;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractAuditorLog;


@Entity
@Table(name="department_log")
public class DepartmentLog extends AbstractAuditorLog<Department,DepartmentState,DepartmentAct>  {


}
