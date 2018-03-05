package com.liyang.domain.person;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflow;

@Entity
@Table(name="person_workflow")
@Cacheable
public class PersonWorkflow extends AbstractWorkflow<Person, PersonState> {



}
