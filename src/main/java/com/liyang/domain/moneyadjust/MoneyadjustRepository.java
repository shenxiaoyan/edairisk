package com.liyang.domain.moneyadjust;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import com.liyang.domain.base.WorkflowEntityRepository;

//@RepositoryRestResource(excerptProjection = UserProjection.class)
public interface MoneyadjustRepository extends WorkflowEntityRepository<Moneyadjust> {
	//根据persondid找到账户
}