package com.liyang.domain.department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.EntityRepository;
//@RepositoryRestResource(excerptProjection = DepartmentProjection.class)
public interface DepartmentActRepository extends ActRepository<DepartmentAct> {

}