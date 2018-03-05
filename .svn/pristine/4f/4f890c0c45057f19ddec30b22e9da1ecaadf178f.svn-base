package com.liyang.domain.department;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.EntityRepository;
//@RepositoryRestResource(excerptProjection = DepartmentProjection.class)
public interface DepartmentRepository extends AuditorEntityRepository<Department> {
    @Query("select d from Department d join d.enterprise e where e.id=:eID")
    Page<Department> findAllByEnterpriseId(@Param("eID") Integer enId, Pageable pageable);
}