package com.liyang.service;

import java.util.List;

import javax.persistence.Transient;

import com.liyang.domain.base.AbstractAuditorAct;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.AbstractAuditorLog;
import com.liyang.domain.base.AbstractAuditorState;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.role.Role;
import com.liyang.domain.user.User;

public interface ServiceImpl<T extends AbstractAuditorEntity, S extends AbstractAuditorState, A extends AbstractAuditorAct, L extends AbstractAuditorLog> {

	void sqlInit();
	
	@Transient
	LogRepository<L> getLogRepository();

	@Transient
	L getLogInstance();

	@Transient
	AuditorEntityRepository<T> getAuditorEntityRepository();
	
	
	@Transient
	ActRepository<A> getActRepository();
	
	void injectEntityService();

	void injectLogRepository();

	List<A> findByIdInAndRolesRoleCode(List<Integer> ids, String roleCode);
	

	A getAct(T entity, String code, Role role);

	void doActLog(T entity, Object linked);


	T doBeforeAct(T entity, A act, Object linked, User fromUser);


	T doAfterAct(T entity, Object linked, User fromUser);

	void canUpdateByOthers(T entity, A act);

}