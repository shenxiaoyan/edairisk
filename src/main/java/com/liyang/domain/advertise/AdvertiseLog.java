package com.liyang.domain.advertise;

import com.liyang.domain.base.AbstractWorkflowLog;
import com.liyang.domain.user.User;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="product_log")
@Cacheable
public class AdvertiseLog extends AbstractWorkflowLog<User,AdvertiseWorkflow,AdvertiseState,AdvertiseAct,AdvertiseFile>  {


	
}
