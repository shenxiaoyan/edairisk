package com.liyang.listener;

import com.liyang.domain.creditrepayment.*;
import com.liyang.domain.creditrepayplan.*;
import org.springframework.stereotype.Component;

@Component
public class CreditrepayplanRestEventListener extends WorkflowRestEventListener<Creditrepayplan, CreditrepayplanWorkflow, CreditrepayplanAct, CreditrepayplanState, CreditrepayplanLog, CreditrepayplanFile>{

}
