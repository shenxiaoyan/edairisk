package com.liyang.domain.approveloan;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jpa.query.expression.generic.SpecificationQueryRepository;

public interface ApproveLoanRepository extends JpaRepository<ApproveLoan, Integer> ,SpecificationQueryRepository<ApproveLoan>{
		
	@Query("select a from ApproveLoan a where a.person.id=:personId and a.approveLoanState.stateCode='ADOPT'")
	public ApproveLoan findByPersonAndStateCode(@Param("personId") Integer personId);
	
	@Query("select a from ApproveLoan a where a.person.id=:personId and a.approveLoanState.stateCode=:stateCode")
	public List<ApproveLoan> findByStateCode(@Param("personId") Integer personId,@Param("stateCode") String stateCode);
		
	@Query("select a from ApproveLoan a where a.person.id=:personId and a.approveLoanState.stateCode in ('CREATED','ADOPT')")
	public List<ApproveLoan> findByTwoStateCode(@Param("personId") Integer personId);
	
	@Query("select a from ApproveLoan a where a.id=:approveLoanId")
	public ApproveLoan findById(@Param("approveLoanId") Integer approveLoanId);
	
	
}