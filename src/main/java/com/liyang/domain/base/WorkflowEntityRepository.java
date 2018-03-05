package com.liyang.domain.base;

import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface WorkflowEntityRepository<T extends AbstractWorkflowEntity> extends AuditorEntityRepository<T> {

}
