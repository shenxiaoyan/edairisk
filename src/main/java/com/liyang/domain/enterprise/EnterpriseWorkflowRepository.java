package com.liyang.domain.enterprise;

import com.liyang.domain.base.EntityRepository;
import com.liyang.domain.base.WorkflowRepository;

//@RepositoryRestResource(excerptProjection = AbstractWorkflowProjection.class)
public interface EnterpriseWorkflowRepository extends WorkflowRepository<EnterpriseWorkflow> {
	
}