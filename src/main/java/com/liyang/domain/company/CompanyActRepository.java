package com.liyang.domain.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.EntityRepository;
//@RepositoryRestResource(excerptProjection = DepartmentProjection.class)
public interface CompanyActRepository extends ActRepository<CompanyAct> {

}