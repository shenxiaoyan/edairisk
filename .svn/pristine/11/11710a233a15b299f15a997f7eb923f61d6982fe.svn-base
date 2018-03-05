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
import com.liyang.domain.certifcation.Certification;
import com.liyang.domain.certifcation.CertificationAct;
import com.liyang.domain.certifcation.CertificationActRepository;
import com.liyang.domain.certifcation.CertificationLog;
import com.liyang.domain.certifcation.CertificationLogRepository;
import com.liyang.domain.certifcation.CertificationRepository;
import com.liyang.domain.certifcation.CertificationState;
import com.liyang.domain.certifcation.CertificationStateRepository;


@Service
@Order(145)
public class CertificationService
		extends AbstractAuditorService<Certification, CertificationState, CertificationAct, CertificationLog> {

	@Autowired
	CertificationActRepository CertificationActRepository;

	@Autowired
	CertificationStateRepository CertificationStateRepository;

	@Autowired
	CertificationLogRepository CertificationLogRepository;

	@Autowired
	CertificationRepository CertificationRepository;

	

	@Override
	@PostConstruct
	public void sqlInit() {
		
			 long findAll = CertificationActRepository.count();
		        if(findAll == 0 ){
			CertificationAct save1 = CertificationActRepository.save(new CertificationAct("创建", "create", 10, ActGroup.UPDATE));
			CertificationAct save2 = CertificationActRepository.save(new CertificationAct("读取", "read", 20, ActGroup.READ));
			CertificationAct save3 = CertificationActRepository.save(new CertificationAct("更新", "update", 30, ActGroup.UPDATE));
			CertificationAct save4 = CertificationActRepository.save(new CertificationAct("删除", "delete", 40, ActGroup.UPDATE));
			CertificationAct save5 = CertificationActRepository.save(new CertificationAct("自己的列表", "listOwn", 50, ActGroup.READ));
			CertificationAct save6 = CertificationActRepository
					.save(new CertificationAct("部门的列表", "listOwnCertification", 60, ActGroup.READ));
			CertificationAct save7 = CertificationActRepository
					.save(new CertificationAct("部门和下属部门的列表", "listOwnCertificationAndChildren", 70, ActGroup.READ));
			CertificationAct save8 = CertificationActRepository
					.save(new CertificationAct("通知其他人", "noticeOther", 80, ActGroup.NOTICE));
			CertificationAct save9 = CertificationActRepository
					.save(new CertificationAct("通知给操作者", "noticeActUser", 90, ActGroup.NOTICE));
			CertificationAct save10 = CertificationActRepository
					.save(new CertificationAct("通知给展示人", "noticeShowUser", 100, ActGroup.NOTICE));

			CertificationStateRepository.save(new CertificationState("已创建", 0, "CREATED"));
			CertificationState CertificationState = new CertificationState("有效", 100, "ENABLED");
			CertificationState.setActs(Arrays.asList(save1, save2, save3, save4, save5, save6, save7).stream()
					.collect(Collectors.toSet()));
			CertificationStateRepository.save(CertificationState);
			CertificationStateRepository.save(new CertificationState("无效", 200, "DISABLED"));
			CertificationStateRepository.save(new CertificationState("已删除", 300, "DELETED"));
			
			
		
			
			

		}

	}

	@Override
	public LogRepository<CertificationLog> getLogRepository() {
		// TODO Auto-generated method stub
		return CertificationLogRepository;
	}

	@Override
	public AuditorEntityRepository<Certification> getAuditorEntityRepository() {
		// TODO Auto-generated method stub
		return CertificationRepository;
	}

	@Override
	@PostConstruct
	public void injectLogRepository() {
		new Certification().setLogRepository(CertificationLogRepository);

	}

	@Override
	public CertificationLog getLogInstance() {
		// TODO Auto-generated method stub
		return new CertificationLog();
	}

	@Override
	public ActRepository<CertificationAct> getActRepository() {
		// TODO Auto-generated method stub
		return CertificationActRepository;
	}

	@Override
	@PostConstruct
	public void injectEntityService() {
		new Certification().setService(this);

	}
	

}
