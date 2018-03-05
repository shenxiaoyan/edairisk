package com.liyang.domain.ordermdbt;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflow;
import com.liyang.domain.orderwdxyd.Orderwdxyd;

@Entity
@Table(name="ordermdbt_workflow")
@Cacheable
public class OrdermdbtWorkflow extends AbstractWorkflow<Ordermdbt,OrdermdbtState> {



}
