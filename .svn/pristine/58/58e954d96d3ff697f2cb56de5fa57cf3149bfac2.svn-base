package com.liyang.domain.role;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.AbstractAuditorLog;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.department.Department;
import com.liyang.domain.department.Departmenttype;
import com.liyang.domain.menu.Menu;
import com.liyang.service.AbstractAuditorService;
import com.liyang.service.MenuService;
import com.liyang.util.CommonUtil;
import com.liyang.util.TreeNode;
import com.liyang.util.TreeNodeImpl;

@Entity
@Table(name = "role")
@Info(label="角色")
public class Role extends AbstractAuditorEntity<RoleState, RoleAct,RoleLog>{


	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = 1L;


	
	@Transient
	private static LogRepository logRepository;
	
	@Transient
	private static AbstractAuditorService service;
	

	@Column(name="role_code")
	@Info(label="角色Code",placeholder="如 COMPANY_OWNER",tip="",help="角色唯一标识",secret="全大写字母")	   
	private String roleCode;

	@ManyToMany(mappedBy="visibleRoles")
	private Set<Menu> visibleMenus;


	@ManyToOne
	@JoinColumn(name="departmenttype_id")
	private Departmenttype departmenttype;
	
	@Transient
	private List<?> visibleMenuTree;
	

	public enum DefaultCode {
		ADMINISTRATOR,
		ANONYMOUS, 
		USER
		
	}

	/* (non-Javadoc)
	 * @see com.liyang.domain.RoleProjection#getRoleCode()
	 */
	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	@Transient
	public  List<?> getVisibleMenuTree() {
		return  this.visibleMenuTree;
	}
	
	public void setupVisibleMenuTree(){
		this.visibleMenuTree = CommonUtil.listToTree(getVisibleMenus());
	}

	public Set<Menu> getVisibleMenus() {
		return visibleMenus;
	}

	public void setVisibleMenus(Set<Menu> visibleMenus) {
		this.visibleMenus = visibleMenus;
	}



	public Departmenttype getDepartmenttype() {
		return departmenttype;
	}

	public void setDepartmenttype(Departmenttype departmenttype) {
		this.departmenttype = departmenttype;
	}

	@Override
	@JsonIgnore
	@Transient
	public RoleLog getLogInstance() {
		// TODO Auto-generated method stub
		return new RoleLog();
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
		Role.logRepository = logRepository;
		
	}

	@Override
	@JsonIgnore
	@Transient
	public AbstractAuditorService getService() {
		// TODO Auto-generated method stub
		return service;
	}

	@Override
	public void setService(AbstractAuditorService service) {
		Role.service = service;
	}
	

}