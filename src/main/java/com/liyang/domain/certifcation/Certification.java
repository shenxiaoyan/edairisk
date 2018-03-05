package com.liyang.domain.certifcation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.service.AbstractAuditorService;
import com.liyang.service.AbstractWorkflowService;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 认证
 */
@Entity
@Table(name = "certification")
public class Certification extends AbstractAuditorEntity<CertificationState, CertificationAct, CertificationLog>{
	@Transient
	private static AbstractAuditorService service;

	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = 1L;

	@Transient
	private static ActRepository actRepository;
	
	@Transient
	private static LogRepository logRepository;
	



	@JsonIgnore
	@Transient
	public ActRepository getActRepository() {
		// TODO Auto-generated method stub
		return actRepository;
	}

	public void setActRepository(ActRepository actRepository) {
		Certification.actRepository = actRepository;
		
	}

	@Override
	public AbstractAuditorService getService() {
		return Certification.service;
	}

	@Override
	public void setService(AbstractAuditorService service) {
		Certification.service=service;
	}

	@Override
	@JsonIgnore
	@Transient
	public CertificationLog getLogInstance() {
		// TODO Auto-generated method stub
		return new CertificationLog();
	}

	@Override
	@JsonIgnore
	@Transient
	public LogRepository getLogRepository() {
		// TODO Auto-generated method stub
		return logRepository;
	}

	@Override
	public void setLogRepository(LogRepository logRepository) {
		Certification.logRepository = logRepository;
		
	}



	

}