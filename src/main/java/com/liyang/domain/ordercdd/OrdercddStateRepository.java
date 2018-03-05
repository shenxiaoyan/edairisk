package com.liyang.domain.ordercdd;

import com.liyang.domain.base.StateRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Collection;
import java.util.List;

//@RepositoryRestResource(excerptProjection = AbstractWorkflowStateProjection.class)
public interface OrdercddStateRepository extends StateRepository<OrdercddState> {

    @RestResource(exported = false)
    List<OrdercddState> findAllByStateCodeIn(Collection<String> codes);
}