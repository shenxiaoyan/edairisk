package com.liyang.listener;

import com.liyang.domain.capitalproduct.*;
import org.springframework.stereotype.Component;

@Component
public class CapitalProductRestEventListener extends WorkflowRestEventListener<Capitalproduct,CapitalproductWorkflow,CapitalproductAct,CapitalproductState,CapitalproductLog,CapitalproductFile> {
}
