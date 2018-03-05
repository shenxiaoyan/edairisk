package com.liyang.service;

import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.capitalproduct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import com.liyang.domain.base.AbstractAuditorAct.ActGroup;
@Service
public class CapitalproductServcie extends AbstractWorkflowService<Capitalproduct,CapitalproductWorkflow,CapitalproductAct,CapitalproductState,CapitalproductLog,CapitalproductFile> {

    @Autowired
    private  CapitalproductLogRepository logRepository;
    @Autowired
    private CapitalproductRepository capitalproductRepository;
    @Autowired
    private CapitalproductActRepository actRepository;
    @Autowired
    private CapitalproductStateRepository stateRepository;
    @Override
    public CapitalproductFile getFileLogInstance() {
        return new CapitalproductFile();
    }

    @Override
    @PostConstruct
    public void sqlInit() {
       
        	 long findAll = actRepository.count();
 	        if(findAll == 0 ){

            CapitalproductAct save1 = actRepository.save(new CapitalproductAct("创建", "create", 10, ActGroup.UPDATE));
            CapitalproductAct save2 = actRepository.save(new CapitalproductAct("读取", "read", 20, ActGroup.READ));
            CapitalproductAct save3 = actRepository.save(new CapitalproductAct("更新", "update", 30, ActGroup.UPDATE));
            CapitalproductAct save4 = actRepository.save(new CapitalproductAct("删除", "delete", 40, ActGroup.UPDATE));
            CapitalproductAct save5 = actRepository.save(new CapitalproductAct("自己的列表", "listOwn", 50, ActGroup.READ));
            CapitalproductAct save6 = actRepository.save(new CapitalproductAct("部门的列表", "listOwnDepartment", 60, ActGroup.READ));
            CapitalproductAct save7 = actRepository.save(new CapitalproductAct("部门和下属部门的列表", "listOwnDepartmentAndChildren", 70, ActGroup.READ));
            CapitalproductAct save8 = actRepository.save(new CapitalproductAct("通知其他人", "noticeOther", 80, ActGroup.NOTICE));
            CapitalproductAct save9 = actRepository.save(new CapitalproductAct("通知操作者", "noticeActUser", 90, ActGroup.NOTICE));
            CapitalproductAct save10 = actRepository.save(new CapitalproductAct("通知展示人", "noticeShowUser", 100, ActGroup.NOTICE));


            CapitalproductState newState = new CapitalproductState("已创建", 0, "CREATED");
            newState = stateRepository.save(newState);
            CapitalproductState enableState = new CapitalproductState("有效", 100, "ENABLED");
            enableState.setActs(Arrays.asList(save1, save2, save3, save4, save5, save6, save7).stream().collect(Collectors.toSet()));
            stateRepository.save(enableState);
            stateRepository.save(new CapitalproductState("无效", 200, "DISABLED"));
            stateRepository.save(new CapitalproductState("删除", 300, "DELETED"));

        }
    }

    @Override
    public LogRepository<CapitalproductLog> getLogRepository() {
        return logRepository;
    }

    @Override
    public CapitalproductLog getLogInstance() {
        return new CapitalproductLog();
    }

    @Override
    public AuditorEntityRepository<Capitalproduct> getAuditorEntityRepository() {
        return capitalproductRepository;
    }

    @Override
    public ActRepository<CapitalproductAct> getActRepository() {
        return actRepository;
    }

    @Override
    @PostConstruct
    public void injectEntityService() {
        new Capitalproduct().setService(this);
    }

    @Override
    @PostConstruct
    public void injectLogRepository() {
        new Capitalproduct().setLogRepository(logRepository);
    }
}
