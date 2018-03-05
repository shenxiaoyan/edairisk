package com.liyang.listener;

import org.springframework.stereotype.Component;

import com.liyang.domain.orderwdsjsh.Orderwdsjsh;
import com.liyang.domain.orderwdsjsh.OrderwdsjshAct;
import com.liyang.domain.orderwdsjsh.OrderwdsjshFile;
import com.liyang.domain.orderwdsjsh.OrderwdsjshLog;
import com.liyang.domain.orderwdsjsh.OrderwdsjshState;
import com.liyang.domain.orderwdsjsh.OrderwdsjshWorkflow;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserAct;
import com.liyang.domain.user.UserLog;
import com.liyang.domain.user.UserState;
import com.liyang.domain.user.UserWorkflow;

@Component
public class OrderwdsjshRestEventListener extends WorkflowRestEventListener<Orderwdsjsh, OrderwdsjshWorkflow, OrderwdsjshAct, OrderwdsjshState, OrderwdsjshLog, OrderwdsjshFile> {

}
