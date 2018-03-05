package com.liyang.domain.department;

import org.springframework.data.repository.query.Param;

import com.liyang.domain.base.EntityRepository;
import com.liyang.domain.base.StateRepository;
import com.liyang.domain.department.Departmenttype.DepartmenttypeCode;
//@RepositoryRestResource(excerptProjection = DepartmentProjection.class)
public interface DepartmenttypeRepository extends EntityRepository<Departmenttype> {
	public Departmenttype findByDepartmenttypeCode(@Param("departmenttype_code") DepartmenttypeCode departmenttype_code);
}