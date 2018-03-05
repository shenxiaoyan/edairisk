package com.liyang.domain.department;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.enterprise.Enterprise;
import com.liyang.domain.product.Product;
import com.liyang.domain.user.User;
import com.liyang.service.AbstractAuditorService;


@Entity
@Table(name = "department")
@Cacheable
@Info(label="部门/门店",placeholder="",tip="",help="",secret="")	   
public class Department extends AbstractAuditorEntity<DepartmentState,DepartmentAct,DepartmentLog>{


	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = 1L;

	
	@Transient
	private static LogRepository logRepository;
	
	@Transient
	private static AbstractAuditorService service;

	@ManyToOne
	@JoinColumn(name = "departmenttype_id")
	@Info(label="部门类型",placeholder="",tip="",help="",secret="")	   
	private Departmenttype departmenttype;

	@ManyToOne
	@JoinColumn(name = "enterprise_id")
	@Info(label="所属企业",placeholder="",tip="",help="",secret="")	   
	private Enterprise enterprise;	
		
	@Info(label="排序",placeholder="",tip="",help="",secret="")	   
	private Integer sort;
	
	@OneToMany(mappedBy = "department")
	private Set<User> employees;
	
	@OneToMany(mappedBy = "department")
	private Set<Product> products;

	
//	@JsonBackReference(value="departmentTree")
	@ManyToOne(fetch=FetchType.LAZY)
	@Info(label="父级",placeholder="",tip="",help="",secret="")	   
	private Department parent;
	
//	@JsonManagedReference(value="departmentTree")
	@OneToMany(mappedBy = "parent")  
	private Set<Department> children = new HashSet<Department>();
	
	
	@Transient
	@JsonIgnore
	@Info(label="父级id",placeholder="",tip="",help="",secret="")	   
	private Integer parentId;



	public Departmenttype getDepartmenttype() {
		return departmenttype;
	}



	public void setDepartmenttype(Departmenttype departmenttype) {
		this.departmenttype = departmenttype;
	}



	public Set<User> getEmployees() {
		return employees;
	}



	public void setEmployees(Set<User> employees) {
		this.employees = employees;
	}



	public Department getParent() {
		return parent;
	}



	public void setParent(Department parent) {
		this.parent = parent;
	}



	public Set<Department> getChildren() {
		return children;
	}



	public void setChildren(Set<Department> children) {
		this.children = children;
	}



	public Integer getSort() {
		// TODO Auto-generated method stub
		return sort;
	}



	public void setSort(Integer sort) {
		this.sort = sort;
		
	}



	public Integer getParentId() {
		// TODO Auto-generated method stub
		return parent == null?0:parent.getId();
	}



	public void setParentId(Integer id) {
		this.parentId = parentId;
		
	}




	@Override
	@JsonIgnore
	@Transient
	public DepartmentLog getLogInstance() {
		// TODO Auto-generated method stub
		return new DepartmentLog();
	}



	@Override
	@JsonIgnore
	@Transient
	public LogRepository getLogRepository() {
		return logRepository;
	}
	@Override
	public void setLogRepository(LogRepository logRepository) {
		Department.logRepository = logRepository;
		
	}


	@JsonIgnore
	@Override
	@Transient
	public AbstractAuditorService getService() {
		// TODO Auto-generated method stub
		return service;
	}



	@Override
	public void setService(AbstractAuditorService service) {
		// TODO Auto-generated method stub
		Department.service = service;
	}



	public Enterprise getEnterprise() {
		return enterprise;
	}



	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}














}