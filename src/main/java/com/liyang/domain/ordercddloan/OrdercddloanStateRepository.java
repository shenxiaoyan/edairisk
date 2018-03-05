package com.liyang.domain.ordercddloan;

import com.liyang.domain.base.StateRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Collection;
import java.util.List;

//@RepositoryRestResource(excerptProjection = AbstractWorkflowStateProjection.class)
public interface OrdercddloanStateRepository extends StateRepository<OrdercddloanState> {

    @RestResource(exported = false)
    public List<OrdercddloanState> findAllByStateCodeIn(Collection<String> codes);
}