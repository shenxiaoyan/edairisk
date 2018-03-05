package com.liyang.domain.moneyadjust;

import com.liyang.domain.base.AbstractWorkflow;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="moneyadjust_workflow")
@Cacheable
public class MoneyadjustWorkflow extends AbstractWorkflow<Moneyadjust, MoneyadjustState> {



}
