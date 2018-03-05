package com.liyang.domain.bankcard;

import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.WorkflowEntityRepository;
import org.springframework.data.repository.query.Param;

import com.liyang.domain.base.EntityRepository;

public interface BankcardRepository extends AuditorEntityRepository<Bankcard> {
	
}
