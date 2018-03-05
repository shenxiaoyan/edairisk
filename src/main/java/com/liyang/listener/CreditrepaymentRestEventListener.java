package com.liyang.listener;

import org.springframework.stereotype.Component;

import com.liyang.domain.creditrepayment.Creditrepayment;
import com.liyang.domain.creditrepayment.CreditrepaymentAct;
import com.liyang.domain.creditrepayment.CreditrepaymentFile;
import com.liyang.domain.creditrepayment.CreditrepaymentLog;
import com.liyang.domain.creditrepayment.CreditrepaymentState;
import com.liyang.domain.creditrepayment.CreditrepaymentWorkflow;
@Component
public class CreditrepaymentRestEventListener extends WorkflowRestEventListener<Creditrepayment, CreditrepaymentWorkflow, CreditrepaymentAct, CreditrepaymentState, CreditrepaymentLog, CreditrepaymentFile>{

}
