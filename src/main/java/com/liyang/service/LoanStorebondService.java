package com.liyang.service;

import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.loanstorebond.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class LoanStorebondService extends AbstractWorkflowService<LoanStorebond,LoanStorebondWorkflow,LoanStorebondAct,LoanStorebondState,LoanStorebondLog,LoanStorebondFile> {

   @Autowired
   private LoanStorebondLogRepository logRepository;
   @Autowired
   private LoanStorebondRepository loanStorebondRepository;
   @Autowired
   private LoanStorebondActRepository actRepository;
    @Override
    public LoanStorebondFile getFileLogInstance() {
        return new LoanStorebondFile();
    }

    @Override
    @PostConstruct
    public void sqlInit() {

    }

    @Override
    public LogRepository<LoanStorebondLog> getLogRepository() {
        return logRepository;
    }

    @Override
    public LoanStorebondLog getLogInstance() {
        return new LoanStorebondLog();
    }

    @Override
    public AuditorEntityRepository<LoanStorebond> getAuditorEntityRepository() {
        return loanStorebondRepository;
    }

    @Override
    public ActRepository<LoanStorebondAct> getActRepository() {
        return actRepository;
    }

    @Override
    @PostConstruct
    public void injectEntityService() {
        new LoanStorebond().setService(this);
    }

    @Override
    @PostConstruct
    public void injectLogRepository() {
        new LoanStorebond().setLogRepository(logRepository);
    }
}
