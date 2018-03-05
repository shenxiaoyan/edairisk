package com.liyang.domain.moneywidthdraw;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import com.liyang.domain.base.WorkflowEntityRepository;

//@RepositoryRestResource(excerptProjection = UserProjection.class)
public interface MoneywidthdrawRepository extends WorkflowEntityRepository<Moneywidthdraw> {
	//根据persondid找到账户
}