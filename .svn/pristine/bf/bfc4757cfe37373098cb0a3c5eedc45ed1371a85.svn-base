package com.liyang.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.liyang.domain.base.AbstractAuditorAct.ActGroup;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.enterprise.Enterprise;
import com.liyang.domain.enterprise.EnterpriseAct;
import com.liyang.domain.enterprise.EnterpriseActRepository;
import com.liyang.domain.enterprise.EnterpriseFile;
import com.liyang.domain.enterprise.EnterpriseLog;
import com.liyang.domain.enterprise.EnterpriseLogRepository;
import com.liyang.domain.enterprise.EnterpriseRepository;
import com.liyang.domain.enterprise.EnterpriseState;
import com.liyang.domain.enterprise.EnterpriseStateRepository;
import com.liyang.domain.enterprise.EnterpriseWorkflow;

@Service
@Order(129)
public class EnterpriseService
		extends AbstractWorkflowService<Enterprise,EnterpriseWorkflow, EnterpriseAct,EnterpriseState,EnterpriseLog,EnterpriseFile> {

	@Autowired
	EnterpriseActRepository enterpriseActRepository;

	@Autowired
	EnterpriseStateRepository enterpriseStateRepository;

	@Autowired
	EnterpriseLogRepository enterpriseLogRepository;

	@Autowired
	EnterpriseRepository enterpriseRepository;

	@Override
	@PostConstruct
	public void sqlInit() {
		
			long findAll = enterpriseActRepository.count();
	        if(findAll == 0 ){
			EnterpriseAct save1 = enterpriseActRepository.save(new EnterpriseAct("创建", "create", 10, ActGroup.UPDATE));
			EnterpriseAct save2 = enterpriseActRepository.save(new EnterpriseAct("读取", "read", 20, ActGroup.READ));
			EnterpriseAct save3 = enterpriseActRepository.save(new EnterpriseAct("更新", "update", 30, ActGroup.UPDATE));
			EnterpriseAct save4 = enterpriseActRepository.save(new EnterpriseAct("删除", "delete", 40, ActGroup.UPDATE));
			EnterpriseAct save5 = enterpriseActRepository.save(new EnterpriseAct("自己的列表", "listOwn", 50, ActGroup.READ));
			EnterpriseAct save6 = enterpriseActRepository
					.save(new EnterpriseAct("部门的列表", "listOwnDepartment", 60, ActGroup.READ));
			EnterpriseAct save7 = enterpriseActRepository
					.save(new EnterpriseAct("部门和下属部门的列表", "listOwnDepartmentAndChildren", 70, ActGroup.READ));
			EnterpriseAct save8 = enterpriseActRepository
					.save(new EnterpriseAct("通知其他人", "noticeOther", 80, ActGroup.NOTICE));
			EnterpriseAct save9 = enterpriseActRepository
					.save(new EnterpriseAct("通知给操作者", "noticeActUser", 90, ActGroup.NOTICE));
			EnterpriseAct save10 = enterpriseActRepository
					.save(new EnterpriseAct("通知给展示人", "noticeShowUser", 100, ActGroup.NOTICE));

			enterpriseStateRepository.save(new EnterpriseState("已创建", 0, "CREATED"));
			EnterpriseState EnterpriseState = new EnterpriseState("有效", 100, "ENABLED");
			EnterpriseState.setActs(Arrays.asList(save1, save2, save3, save4, save5, save6, save7).stream()
					.collect(Collectors.toSet()));
			enterpriseStateRepository.save(EnterpriseState);
			enterpriseStateRepository.save(new EnterpriseState("无效", 200, "DISABLED"));
			enterpriseStateRepository.save(new EnterpriseState("已删除", 300, "DELETED"));
			

		}

	}

	@Override
	public LogRepository<EnterpriseLog> getLogRepository() {
		// TODO Auto-generated method stub
		return enterpriseLogRepository;
	}

	@Override
	public AuditorEntityRepository<Enterprise> getAuditorEntityRepository() {
		// TODO Auto-generated method stub
		return enterpriseRepository;
	}

	@Override
	@PostConstruct
	public void injectLogRepository() {
		new Enterprise().setLogRepository(enterpriseLogRepository);

	}

	@Override
	public EnterpriseLog getLogInstance() {
		// TODO Auto-generated method stub
		return new EnterpriseLog();
	}

	@Override
	public ActRepository<EnterpriseAct> getActRepository() {
		// TODO Auto-generated method stub
		return enterpriseActRepository;
	}

	@Override
	@PostConstruct
	public void injectEntityService() {
		new Enterprise().setService(this);

	}

	@Override
	public EnterpriseFile getFileLogInstance() {
		// TODO Auto-generated method stub
		return new EnterpriseFile();
	}







}
