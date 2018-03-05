package com.liyang.listener;

import org.springframework.stereotype.Component;

import com.liyang.domain.ordermdbt.Ordermdbt;
import com.liyang.domain.ordermdbt.OrdermdbtAct;
import com.liyang.domain.ordermdbt.OrdermdbtFile;
import com.liyang.domain.ordermdbt.OrdermdbtLog;
import com.liyang.domain.ordermdbt.OrdermdbtState;
import com.liyang.domain.ordermdbt.OrdermdbtWorkflow;
import com.liyang.domain.orderwdxyd.Orderwdxyd;
import com.liyang.domain.orderwdxyd.OrderwdxydAct;
import com.liyang.domain.orderwdxyd.OrderwdxydFile;
import com.liyang.domain.orderwdxyd.OrderwdxydLog;
import com.liyang.domain.orderwdxyd.OrderwdxydState;
import com.liyang.domain.orderwdxyd.OrderwdxydWorkflow;
@Component
public class OrdermdbtRestEventListener extends WorkflowRestEventListener<Ordermdbt, OrdermdbtWorkflow, OrdermdbtAct, OrdermdbtState, OrdermdbtLog, OrdermdbtFile>{

}
