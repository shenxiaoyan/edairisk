package com.liyang.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jpa.query.expression.QueryExpSpecificationBuilder;
import com.jpa.query.expression.generic.GenericQueryExpSpecification;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.base.AbstractAuditorAct.ActGroup;
import com.liyang.domain.base.AbstractWorkflowAct.ActType;
import com.liyang.domain.person.Person;
import com.liyang.domain.person.PersonAct;
import com.liyang.domain.person.PersonActRepository;
import com.liyang.domain.person.PersonFile;
import com.liyang.domain.person.PersonLog;
import com.liyang.domain.person.PersonLogRepository;
import com.liyang.domain.person.PersonRepository;
import com.liyang.domain.person.PersonState;
import com.liyang.domain.person.PersonStateRepository;
import com.liyang.domain.person.PersonWorkflow;
import com.liyang.domain.person.PersonWorkflowRepository;
import com.liyang.domain.quota.Quota;
import com.liyang.domain.role.Role;
import com.liyang.domain.role.RoleRepository;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserAct;
import com.liyang.domain.user.UserActRepository;
import com.liyang.domain.user.UserLog;
import com.liyang.domain.user.UserLogRepository;
import com.liyang.domain.user.UserRepository;
import com.liyang.domain.user.UserState;
import com.liyang.domain.user.UserStateRepository;
import com.liyang.domain.user.UserWorkflow;
import com.liyang.domain.user.UserWorkflowRepository;
@Service
@Order(20)
public class PersonService extends AbstractWorkflowService<Person, PersonWorkflow, PersonAct, PersonState, PersonLog, PersonFile>{
	@Autowired
	PersonActRepository personActRepository;
	
	@Autowired
	PersonStateRepository personStateRepository;
	
	@Autowired
	PersonLogRepository  personLogRepository;
	
	@Autowired
	PersonRepository  personRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	
	@Autowired
	PersonWorkflowRepository personWorkflowRepository;
	
	
	
	@Override
	@PostConstruct 
	public void sqlInit() {
		
			long findAll = personActRepository.count();
	        if(findAll == 0 ){
			PersonAct save1 = personActRepository.save(new PersonAct("创建","create",10,ActGroup.UPDATE));
			PersonAct save2 = personActRepository.save(new PersonAct("读取","read",20,ActGroup.READ));
			PersonAct save3 = personActRepository.save(new PersonAct("更新","update",30,ActGroup.UPDATE));
			PersonAct save4 = personActRepository.save(new PersonAct("删除","delete",40,ActGroup.UPDATE));
			PersonAct save5 = personActRepository.save(new PersonAct("自己的列表","listOwn",50,ActGroup.READ));
			PersonAct save6 = personActRepository.save(new PersonAct("部门的列表","listOwnDepartment",60,ActGroup.READ));
			PersonAct save7 = personActRepository.save(new PersonAct("部门和下属部门的列表","listOwnDepartmentAndChildren",70,ActGroup.READ));
			PersonAct save8 = personActRepository.save(new PersonAct("通知其他人","noticeOther",80,ActGroup.NOTICE));
			PersonAct save9 = personActRepository.save(new PersonAct("通知操作者","noticeActUser",90,ActGroup.NOTICE));
			PersonAct save10 = personActRepository.save(new PersonAct("通知展示人","noticeShowUser",100,ActGroup.NOTICE));
			
	
			PersonState newState =new PersonState("已创建",0,"CREATED");
			newState = personStateRepository.save(newState);
			PersonState enableState = new PersonState("有效",100,"ENABLED");
			enableState.setActs(Arrays.asList(save1,save2,save3,save4,save5,save6,save7).stream().collect(Collectors.toSet()));
			personStateRepository.save(enableState);
			personStateRepository.save(new PersonState("无效",200,"DISABLED"));
			personStateRepository.save(new PersonState("已删除",300,"DELETED"));
			
		}
		
	}

	@Override
	public LogRepository<PersonLog> getLogRepository() {
		// TODO Auto-generated method stub
		return personLogRepository;
	}

	@Override
	public AuditorEntityRepository<Person> getAuditorEntityRepository() {
		
		return personRepository;
	}


	@Override
	@PostConstruct 
	public void injectLogRepository() {
		new Person().setLogRepository(personLogRepository);
		
	}

	@Override
	public PersonLog getLogInstance() {
		// TODO Auto-generated method stub
		return new PersonLog();
	}

	@Override
	public ActRepository<PersonAct> getActRepository() {
		// TODO Auto-generated method stub
		return personActRepository;
	}

	@Override
	@PostConstruct 
	public void injectEntityService() {
		new Person().setService(this);
		
	}

	@Override
	public PersonFile getFileLogInstance() {
		// TODO Auto-generated method stub
		return new PersonFile();
	}
	
	
    //根据条件动态查询并分页
    public Page<Person> getPersonList(String stateCode,String name,String identity,
    		                          String  accountStatus,String telephone,Pageable pageable){ 
    	
	    GenericQueryExpSpecification<Person> queryExpression = new GenericQueryExpSpecification<Person>();
	    queryExpression.add(QueryExpSpecificationBuilder.eq("state.stateCode", stateCode, true));
	    queryExpression.add(QueryExpSpecificationBuilder.eq("name", name, true));
	    queryExpression.add(QueryExpSpecificationBuilder.eq("identity", identity, true));
	    queryExpression.add(QueryExpSpecificationBuilder.eq("accountStatus", accountStatus, true));
	    queryExpression.add(QueryExpSpecificationBuilder.eq("telephone", telephone, true));
		
		return personRepository.findAll(queryExpression,pageable);	
    }


}
