package com.liyang.domain.riskmodel;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.EntityRepository;
import com.liyang.domain.base.StateRepository;


//@RepositoryRestResource(excerptProjection = MenuProjection.class)
public interface RiskmodelStateRepository extends StateRepository<RiskmodelState> {
	
}
