package com.liyang.domain.company;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jpa.query.expression.generic.SpecificationQueryRepository;
import com.liyang.domain.base.AuditorEntityRepository;
//@RepositoryRestResource(excerptProjection = DepartmentProjection.class)
public interface CompanyRepository extends AuditorEntityRepository<Company> ,SpecificationQueryRepository<Company>{
	
	//根据公司名称查询
	@Query("select c from Company c where c.companyName=:companyName")
	public Company findByCompanyName(@Param("companyName") String companyName);
	
	//根据工商号码查询
	@Query("select c from Company c where c.companyNumber=:companyNumber")
	public Company findByCompanyNumber(@Param("companyNumber") String companyNumber);
	
	@Query("select c from Company  c where c.createdBy.id=:userid")
	public Company findByCreateBy(@Param("userid") Integer userid);
   
}