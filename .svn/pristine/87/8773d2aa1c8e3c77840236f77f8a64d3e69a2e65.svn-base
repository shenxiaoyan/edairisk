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
public class RoleService extends AbstractAuditorService<Role,RoleState,RoleAct,RoleLog> {

	@Autowired
	RoleActRepository roleActRepository;
	
	@Autowired
	RoleStateRepository roleStateRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	RoleLogRepository roleLogRepository;
	
	@Autowired
	UserActRepository userActRepository;
	
	@Override
	@PostConstruct 
	public void sqlInit() {
		
			long findAll = roleActRepository.count();
	        if(findAll == 0 ){
			RoleAct save1 = roleActRepository.save(new RoleAct("创建","create",10,ActGroup.UPDATE));
			RoleAct save2 = roleActRepository.save(new RoleAct("读取","read",20,ActGroup.READ));
			RoleAct save3 = roleActRepository.save(new RoleAct("更新","update",30,ActGroup.UPDATE));
			RoleAct save4 = roleActRepository.save(new RoleAct("删除","delete",40,ActGroup.UPDATE));
			RoleAct save5 = roleActRepository.save(new RoleAct("自己的列表","listOwn",50,ActGroup.READ));
			RoleAct save6 = roleActRepository.save(new RoleAct("部门的列表","listOwnDepartment",60,ActGroup.READ));
			RoleAct save7 = roleActRepository.save(new RoleAct("部门和下属部门的列表","listOwnDepartmentAndChildren",70,ActGroup.READ));
			RoleAct save8 = roleActRepository.save(new RoleAct("通知其他人","noticeOther",80,ActGroup.NOTICE));
			RoleAct save9 = roleActRepository.save(new RoleAct("通知操作者","noticeActUser",90,ActGroup.NOTICE));
			RoleAct save10 = roleActRepository.save(new RoleAct("通知展示人","noticeShowUser",100,ActGroup.NOTICE));
			
	
			roleStateRepository.save(new RoleState("已创建",0,"CREATED"));
			RoleState roleState = new RoleState("有效",100,"ENABLED");
			roleState.setActs(Arrays.asList(save1,save2,save3,save4,save5,save6,save7).stream().collect(Collectors.toSet()));
			RoleState enableState = roleStateRepository.save(roleState);
			roleStateRepository.save(new RoleState("无效",200,"DISABLED"));
			roleStateRepository.save(new RoleState("已删除",300,"DELETED"));
			
			
			for(Role.DefaultCode roleCode : Role.DefaultCode.values()){
				Role role = new Role();
				role.setRoleCode(roleCode.toString());
				role.setState(enableState);
				Role save = roleRepository.save(role);
				if(roleCode.toString().equals("ADMINISTRATOR")){
					
					save1.setRoles(new HashSet<Role>(Arrays.asList(save)));
					save2.setRoles(new HashSet<Role>(Arrays.asList(save)));
					save3.setRoles(new HashSet<Role>(Arrays.asList(save)));
					save4.setRoles(new HashSet<Role>(Arrays.asList(save)));
					roleActRepository.save(save1);
					roleActRepository.save(save2);
					roleActRepository.save(save3);
					roleActRepository.save(save4);
					
				}
			}
			
			
			
			
			UserAct applyActCode = userActRepository.findByActCode("apply");
			Role role = roleRepository.findByRoleCode("USER");
			if(applyActCode!=null){
				applyActCode.setRoles(new HashSet<Role>(Arrays.asList(role)));
				userActRepository.save(applyActCode);
			}

		}
		
	}

	@Override
	public LogRepository<RoleLog> getLogRepository() {
		// TODO Auto-generated method stub
		return roleLogRepository;
	}



	@Override
	public AuditorEntityRepository<Role> getAuditorEntityRepository() {
		// TODO Auto-generated method stub
		return roleRepository;
	}


	@Override
	@PostConstruct 
	public void injectLogRepository() {
		new Role().setLogRepository(roleLogRepository);
		
	}

	@Override
	public RoleLog getLogInstance() {
		// TODO Auto-generated method stub
		return new RoleLog();
	}

	@Override
	public ActRepository<RoleAct> getActRepository() {
		// TODO Auto-generated method stub
		return roleActRepository;
	}

	@Override
	@PostConstruct 
	public void injectEntityService() {
		new Role().setService(this);
		
	}
	

}
