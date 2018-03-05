package com.liyang.domain.product;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.liyang.domain.base.WorkflowEntityRepository;
import com.liyang.domain.department.Department;
import com.liyang.domain.role.Role;

//@RepositoryRestResource(excerptProjection = UserProjection.class)
public interface ProductRepository extends WorkflowEntityRepository<Product> {

    Product findFirstByLabel(String label);
    
    
}