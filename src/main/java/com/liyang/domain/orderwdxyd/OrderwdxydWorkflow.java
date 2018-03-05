package com.liyang.domain.orderwdxyd;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflow;

@Entity
@Table(name="orderwdxyd_workflow")
@Cacheable
public class OrderwdxydWorkflow extends AbstractWorkflow<Orderwdxyd, OrderwdxydState> {



}
