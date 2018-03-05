package com.liyang.domain.orderwdsjsh;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflow;

@Entity
@Table(name="orderwdsjsh_workflow")
@Cacheable
public class OrderwdsjshWorkflow extends AbstractWorkflow<Orderwdsjsh, OrderwdsjshState> {



}
