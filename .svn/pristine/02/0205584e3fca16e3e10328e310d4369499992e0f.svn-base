package com.liyang.service;

import com.liyang.domain.bankcard.*;
import com.liyang.domain.base.AbstractAuditorAct.ActGroup;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.loan.*;
import com.liyang.domain.orderwdsjsh.Orderwdsjsh;
import com.liyang.domain.orderwdsjsh.OrderwdsjshRepository;
import com.liyang.domain.role.RoleRepository;
import com.liyang.util.FailReturnObject;
import com.liyang.util.ReturnObject.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Order(37)
public class BankcardService extends AbstractAuditorService<Bankcard, BankcardState,  BankcardAct,BankcardLog> {

	@Autowired
	BankcardActRepository actRepository;

	@Autowired
	BankcardStateRepository stateRepository;

	@Autowired
	BankcardLogRepository logRepository;

	@Autowired
	BankcardRepository bankcardRepository;


	@Override
	@PostConstruct
	public void sqlInit() {
		
			 long findAll = actRepository.count();
		        if(findAll == 0 ){
			BankcardAct save1 = actRepository.save(new BankcardAct("创建", "create", 10, ActGroup.UPDATE));
			BankcardAct save2 = actRepository.save(new BankcardAct("读取", "read", 20, ActGroup.READ));
			BankcardAct save3 = actRepository.save(new BankcardAct("更新", "update", 30, ActGroup.UPDATE));
			BankcardAct save4 = actRepository.save(new BankcardAct("删除", "delete", 40, ActGroup.UPDATE));
			BankcardAct save5 = actRepository.save(new BankcardAct("自己的列表", "listOwn", 50, ActGroup.READ));
			BankcardAct save6 = actRepository.save(new BankcardAct("部门的列表", "listOwnDepartment", 60, ActGroup.READ));
			BankcardAct save7 = actRepository
					.save(new BankcardAct("部门和下属部门的列表", "listOwnDepartmentAndChildren", 70, ActGroup.READ));
			BankcardAct save8 = actRepository.save(new BankcardAct("通知其他人", "noticeOther", 80, ActGroup.NOTICE));
			BankcardAct save9 = actRepository.save(new BankcardAct("通知操作者", "noticeActUser", 90, ActGroup.NOTICE));
			BankcardAct save10 = actRepository.save(new BankcardAct("通知展示人", "noticeShowUser", 100, ActGroup.NOTICE));

			BankcardState newState = new BankcardState("已创建", 0, "CREATED");
			newState = stateRepository.save(newState);

			BankcardState enableState = new BankcardState("有效", 100, "ENABLED");
			enableState.setActs(Arrays.asList(save1, save2, save3, save4, save5, save6, save7).stream()
					.collect(Collectors.toSet()));
			stateRepository.save(enableState);
			stateRepository.save(new BankcardState("无效", 200, "DISABLED"));
			stateRepository.save(new BankcardState("已删除", 300, "DELETED"));

		}

	}

	@Override
	public LogRepository<BankcardLog> getLogRepository() {
		// TODO Auto-generated method stub
		return logRepository;
	}

	@Override
	public AuditorEntityRepository<Bankcard> getAuditorEntityRepository() {

		return bankcardRepository;
	}

	@Override
	@PostConstruct
	public void injectLogRepository() {
		new Bankcard().setLogRepository(logRepository);

	}

	@Override
	public BankcardLog getLogInstance() {
		// TODO Auto-generated method stub
		return new BankcardLog();
	}

	@Override
	public ActRepository<BankcardAct> getActRepository() {
		// TODO Auto-generated method stub
		return actRepository;
	}

	@Override
	@PostConstruct
	public void injectEntityService() {
		new Bankcard().setService(this);

	}


}
