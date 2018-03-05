package com.liyang.domain.ordersbdy;


import com.liyang.domain.base.WorkflowEntityRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

//@RepositoryRestResource(excerptProjection = UserProjection.class)
public interface OrdersbdyRepository extends WorkflowEntityRepository<Ordersbdy> {

    @Query("select o from Ordersbdy o inner join o.state s where s.stateCode=:stateCode")
    public Page<Ordersbdy> listOwnByStateCode(@Param("stateCode") @Value("ENABLED") String stateCode, Pageable pageable);

}
