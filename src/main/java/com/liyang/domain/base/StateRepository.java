package com.liyang.domain.base;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

@NoRepositoryBean
public interface StateRepository<T extends AbstractAuditorState> extends EntityRepository<T> {
	public T findByStateCode(@Param("stateCode") String stateCode);
}
