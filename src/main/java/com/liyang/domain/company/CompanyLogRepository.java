package com.liyang.domain.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.liyang.domain.base.LogRepository;
//@RepositoryRestResource(excerptProjection = DepartmentLogProjection.class)
public interface CompanyLogRepository extends LogRepository<CompanyLog> {

}