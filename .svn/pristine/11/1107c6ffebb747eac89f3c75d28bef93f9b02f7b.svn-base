package com.liyang.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.base.AbstractAuditorAct.ActGroup;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.department.DepartmentAct;
import com.liyang.domain.riskmodel.Riskmodel;
import com.liyang.domain.riskmodel.RiskmodelAct;
import com.liyang.domain.riskmodel.RiskmodelActRepository;
import com.liyang.domain.riskmodel.RiskmodelLog;
import com.liyang.domain.riskmodel.RiskmodelLogRepository;
import com.liyang.domain.riskmodel.RiskmodelRepository;
import com.liyang.domain.riskmodel.RiskmodelState;
import com.liyang.domain.riskmodel.RiskmodelStateRepository;
import com.liyang.domain.role.Role;
import com.liyang.domain.role.RoleAct;
import com.liyang.domain.role.RoleActRepository;
import com.liyang.domain.role.RoleLog;
import com.liyang.domain.role.RoleLogRepository;
import com.liyang.domain.role.RoleRepository;
import com.liyang.domain.role.RoleState;
import com.liyang.domain.role.RoleStateRepository;
import com.liyang.domain.user.UserAct;
import com.liyang.domain.user.UserActRepository;

@Service
@Order(1)
public class RiskmodelService extends AbstractAuditorService<Riskmodel,RiskmodelState,RiskmodelAct,RiskmodelLog> {

	@Autowired
	RiskmodelActRepository roleActRepository;
	
	@Autowired
	RiskmodelStateRepository roleStateRepository;
	
	@Autowired
	RiskmodelRepository roleRepository;
	
	@Autowired
	RiskmodelLogRepository roleLogRepository;
	
	@Autowired
	UserActRepository userActRepository;
	
	@Override
	@PostConstruct 
	public void sqlInit() {
		
			long findAll = roleActRepository.count();
	        if(findAll == 0 ){
			RiskmodelAct save1 = roleActRepository.save(new RiskmodelAct("创建","create",10,ActGroup.UPDATE));
			RiskmodelAct save2 = roleActRepository.save(new RiskmodelAct("读取","read",20,ActGroup.READ));
			RiskmodelAct save3 = roleActRepository.save(new RiskmodelAct("更新","update",30,ActGroup.UPDATE));
			RiskmodelAct save4 = roleActRepository.save(new RiskmodelAct("删除","delete",40,ActGroup.UPDATE));
			RiskmodelAct save5 = roleActRepository.save(new RiskmodelAct("自己的列表","listOwn",50,ActGroup.READ));
			RiskmodelAct save6 = roleActRepository.save(new RiskmodelAct("部门的列表","listOwnDepartment",60,ActGroup.READ));
			RiskmodelAct save7 = roleActRepository.save(new RiskmodelAct("部门和下属部门的列表","listOwnDepartmentAndChildren",70,ActGroup.READ));
			RiskmodelAct save8 = roleActRepository.save(new RiskmodelAct("通知其他人","noticeOther",80,ActGroup.NOTICE));
			RiskmodelAct save9 = roleActRepository.save(new RiskmodelAct("通知操作者","noticeActUser",90,ActGroup.NOTICE));
			RiskmodelAct save10 = roleActRepository.save(new RiskmodelAct("通知展示人","noticeShowUser",100,ActGroup.NOTICE));
			
	
			roleStateRepository.save(new RiskmodelState("已创建",0,"CREATED"));
			RiskmodelState roleState = new RiskmodelState("有效",100,"ENABLED");
			roleState.setActs(Arrays.asList(save1,save2,save3,save4,save5,save6,save7).stream().collect(Collectors.toSet()));
			RiskmodelState enableState = roleStateRepository.save(roleState);
			roleStateRepository.save(new RiskmodelState("无效",200,"DISABLED"));
			roleStateRepository.save(new RiskmodelState("删除",300,"DELETED"));
			

		}
		
	}

	@Override
	public LogRepository<RiskmodelLog> getLogRepository() {
		// TODO Auto-generated method stub
		return roleLogRepository;
	}



	@Override
	public AuditorEntityRepository<Riskmodel> getAuditorEntityRepository() {
		// TODO Auto-generated method stub
		return roleRepository;
	}


	@Override
	@PostConstruct 
	public void injectLogRepository() {
		new Role().setLogRepository(roleLogRepository);
		
	}

	@Override
	public RiskmodelLog getLogInstance() {
		// TODO Auto-generated method stub
		return new RiskmodelLog();
	}

	@Override
	public ActRepository<RiskmodelAct> getActRepository() {
		// TODO Auto-generated method stub
		return roleActRepository;
	}

	@Override
	@PostConstruct 
	public void injectEntityService() {
		new Role().setService(this);
		
	}
	

}
