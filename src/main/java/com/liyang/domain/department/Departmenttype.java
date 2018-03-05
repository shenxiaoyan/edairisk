package com.liyang.domain.department;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.AbstractAuditorLog;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.BaseEntity;
import com.liyang.domain.base.IVisibility;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.role.Role;
import com.liyang.domain.user.User;
import com.liyang.service.AbstractAuditorService;
import com.liyang.util.CommonUtil;
import com.liyang.util.TreeNode;


@Entity
@Table(name = "departmenttype")
@Order(11111111)
public class Departmenttype extends BaseEntity{


	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = 1L;



	
	@Enumerated(EnumType.STRING)
	@Column(name="departmenttype_code")
	private DepartmenttypeCode departmenttypeCode;

	
	
	
	@OneToMany(mappedBy = "departmenttype")
	private Set<Department> departments;
	
	
	@OneToMany(mappedBy = "departmenttype")
	private Set<Role> roles;





	public DepartmenttypeCode getDepartmenttypeCode() {
		return departmenttypeCode;
	}


	public void setDepartmenttypeCode(DepartmenttypeCode departmenttypeCode) {
		this.departmenttypeCode = departmenttypeCode;
	}


	public Set<Department> getDepartments() {
		return departments;
	}


	public void setDepartments(Set<Department> departments) {
		this.departments = departments;
	}


	public Set<Role> getRoles() {
		return roles;
	}


	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public static enum DepartmenttypeCode{
		STORE, 
		GROUP, 
		WULIU_COMPANY, 
		CREDITOR, 
		DEBTOR, 
		LAW, 
		DISTRIBUTOR
	}
	








}