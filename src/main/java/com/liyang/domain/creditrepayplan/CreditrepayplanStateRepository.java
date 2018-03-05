package com.liyang.domain.creditrepayplan;

import com.liyang.domain.base.StateRepository;

//@RepositoryRestResource(excerptProjection = AbstractWorkflowStateProjection.class)
public interface CreditrepayplanStateRepository extends StateRepository<CreditrepayplanState> {
	public CreditrepayplanState findByStateCode(String stateCode);
}