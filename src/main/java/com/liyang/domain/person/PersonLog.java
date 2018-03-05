package com.liyang.domain.person;

import com.liyang.domain.base.AbstractAuditorLog;
import com.liyang.domain.base.AbstractWorkflowLog;
import com.liyang.domain.orderwdxyd.Orderwdxyd;
import com.liyang.domain.orderwdxyd.OrderwdxydAct;
import com.liyang.domain.orderwdxyd.OrderwdxydFile;
import com.liyang.domain.orderwdxyd.OrderwdxydState;
import com.liyang.domain.orderwdxyd.OrderwdxydWorkflow;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "person_log")
//Orderwdxyd,OrderwdxydWorkflow,OrderwdxydState,OrderwdxydAct,OrderwdxydFile
public class PersonLog extends AbstractWorkflowLog<Person,PersonWorkflow,PersonState,PersonAct,PersonFile> {

}
