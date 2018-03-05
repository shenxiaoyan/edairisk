package com.liyang.domain.riskmodel;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.EntityRepository;

//@RepositoryRestResource(excerptProjection = RoleProjection.class)
public interface RiskmodelRepository extends AuditorEntityRepository<Riskmodel> {
	
	
}
