package com.liyang.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.collection.internal.PersistentSet;
import org.springframework.beans.factory.annotation.Autowired;

import com.liyang.domain.base.AbstractAuditorAct;
import com.liyang.domain.base.AbstractAuditorAct.ActGroup;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.AbstractAuditorLog;
import com.liyang.domain.base.AbstractAuditorState;
import com.liyang.domain.base.StateRepository;
import com.liyang.domain.role.Role;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserRepository;
import com.liyang.util.CommonUtil;
import com.liyang.util.FailReturnObject;
import com.liyang.util.ReturnObject.Level;

public abstract class AbstractAuditorService<T extends AbstractAuditorEntity, S extends AbstractAuditorState, A extends AbstractAuditorAct, L extends AbstractAuditorLog>
		implements ServiceImpl<T, S, A, L> {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TIMService timService;

	@Autowired
	private StateRepository<S> stateRepository;

	protected A getActByRole(Set<A> acts, String code, Role role) {
		for (A abstractAuditorAct : acts) {
			if (abstractAuditorAct.getActCode().equals(code)) {
				Set roles = abstractAuditorAct.getRoles();
				if (roles == null) {
					throw new FailReturnObject(1130, "没有角色操作动作:" + code, Level.ERROR);
				}
				for (Object object : roles) {
					if (((Role) object).getRoleCode().equals(role.getRoleCode())) {
						return abstractAuditorAct;
					}
				}
			}
		}
		return null;
	}

	@Override
	public List<A> findByIdInAndRolesRoleCode(List<Integer> ids, String roleCode) {
		// TODO Auto-generated method stub

		return getActRepository().findByIdInAndRolesRoleCode(ids, roleCode);
	}

	@Override
	public A getAct(T entity, String code, Role role) {

		if (role == null) {
			if (CommonUtil.getPrincipal() != null) {
				role = CommonUtil.getPrincipal().getRole();
			} else {
				role = new Role();
				role.setRoleCode("ANONYMOUS");
			}
		}
		Set<A> acts = new HashSet<A>();
		if (entity.getBeforeState() != null) {
			acts = entity.getBeforeState().getActs();
		} else {
			acts = entity.getState().getActs();
		}

		if (acts.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			throw new FailReturnObject(1170, sb.append("角色").append(role.getRoleCode().toString()).append("，状态")
					.append(entity.getBeforeState().getStateCode()).append("不允许").append(code).append("操作").toString(),
					Level.ERROR);
		}

		A act = getActByRole(acts, code, role);
		if (act == null) {
			StringBuilder sb2 = new StringBuilder();
			throw new FailReturnObject(660,
					sb2.append(entity.getClass().getSimpleName()).append("类").append("状态:")
							.append(entity.getBeforeState().getStateCode().toString()).append(",没有为角色")
							.append(role.getRoleCode().toString()).append("设置动作:").append(code),
					Level.ERROR);
		}
		return act;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.liyang.service.IActable#canAct(T, java.lang.String)
	 */
	@Override
	public final void canUpdateByOthers(T entity, A act) {
		Role role = new Role();
		role.setRoleCode("ANONYMOUS");
		if (CommonUtil.getPrincipal() != null) {
			role = CommonUtil.getPrincipal().getRole();
		}

		if (CommonUtil.getPrincipal() != null) {
			if (!CommonUtil.getCurrentUserRoleCode().equals("ADMINISTRATOR")) {
				if (entity.getCreatedBy() != null
						&& !CommonUtil.getPrincipal().getId().equals(entity.getCreatedBy().getId())) {
					Set<A> acts = new HashSet<A>();
					if (entity.getBeforeState() != null) {
						acts = entity.getBeforeState().getActs();
					} else {
						acts = entity.getState().getActs();
					}
					A actByRole = getActByRole(acts, "updateOthers", role);
					if (actByRole == null) {
						throw new FailReturnObject(679, "不允许角色" + role.getRoleCode().toString() + "修改其他人资料",
								Level.ERROR);
					}
				}
			}
		} else {
			throw new FailReturnObject(3123, "需要登录", Level.WARNING);
		}

	}

	protected L buildLogByEntity(T entity, Object linked) {
		L log = getLogInstance();
		log.setAct(entity.getLastAct());
		log.setActGroup(entity.getLastAct().getActGroup());
		log.setEntity(entity);
		log.setLabel(entity.getLastAct().getLabel());
		log.setBeforeState(entity.getBeforeState());
		log.setAfterState(entity.getState());
		Map entityToDiffMap = CommonUtil.entityToDiffMap(entity, linked);
		log.setDifference(CommonUtil.prettyPrint(entityToDiffMap));

		log.setAppCode(request.getHeader("app_code"));
		log.setClient(request.getHeader("client"));
		log.setImei(request.getHeader("imei"));
		log.setIp(request.getRemoteHost());
		if (request.getHeader("longitude") != null) {
			log.setLongitude(Double.valueOf(request.getHeader("longitude")));
			log.setLatitude(Double.valueOf(request.getHeader("latitude")));
		}

		return log;
	}

	@Override
	public void doActLog(T entity, Object linked) {

		L log = buildLogByEntity(entity, linked);

		getLogRepository().save(log);

		User principal = CommonUtil.getPrincipal();
		if (principal!=null && log.getLongitude() != null) {
			principal.setLatitude(log.getLatitude());
			principal.setLongitude(log.getLongitude());
			userRepository.save(principal);
		}

	}

	@Override
	public T doBeforeAct(T entity, A act, Object linked, User fromUser) {
		entity.setLastAct(act);
		if (act.getActCode().equals("update")) {
			canUpdateByOthers(entity, act);
		}

		if (entity.getCreatedByDepartment() == null && act.getActGroup().equals(ActGroup.OPERATE)) {
			User createdBy = CommonUtil.getPrincipal();
			if (createdBy != null && createdBy instanceof User) {
				entity.setCreatedByDepartment(createdBy.getDepartment());
			}
		}

		if (linked != null && entity.getLinkedKey() == null) {
			PersistentSet linkedset = (PersistentSet) linked;
			String role = linkedset.getRole();
			entity.setLinkedKey(role.substring(role.lastIndexOf(".") + 1));
		}
		StringBuilder sb3 = new StringBuilder();
		String doActStr = sb3.append("do").append(CommonUtil.toUpperCaseFirstOne(act.getActCode().trim().toLowerCase()))
				.toString();
		try {
			Method m2 = getClass().getDeclaredMethod(doActStr, entity.getClass());
			try {
				m2.invoke(this, entity);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				if (e.getCause() instanceof FailReturnObject) {
					throw (FailReturnObject) e.getCause();
				} else {
					String message = e.getMessage()==null?"":e.getMessage();
					message += "method:" + m2.getName() + "entity:" + entity.getLabel();
					throw new FailReturnObject(7512, message, Level.FATAL);
				}

			}
		} catch (NoSuchMethodException e) {

		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			throw new FailReturnObject(2332, "方法不可访问", Level.ERROR);
		}
		return entity;
	}

	@Override
	public T doAfterAct(T entity, Object linked, User fromUser) {

		if (fromUser == null && request.getParameter("fromUser") != null) {
			fromUser = userRepository.findByOpenid(request.getParameter("fromUser"));
		}

		if (entity.getLastAct().getActCode().equals("delete")) {
			entity.setState(stateRepository.findByStateCode("DELETED"));
		}

		doActLog(entity, linked);
		timService.doNotice(fromUser, entity, entity.getLastAct());

		return entity;
	}

}
