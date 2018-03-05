package com.liyang.domain.loan;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jpa.query.expression.generic.SpecificationQueryRepository;
import com.timevale.tgtext.awt.geom.p;

public interface LoanoverdueRepository extends SpecificationQueryRepository<Loanoverdue>,JpaRepository<Loanoverdue, Integer> {
	
	@Query("select l from Loanoverdue l where l.loan.id=:loanId and l.status=0")
	public Loanoverdue findEnable(@Param("loanId") Integer loanId);
	
	public Set<Loanoverdue> findByStatus(Integer status);
	
	@Query("select l from Loanoverdue l where l.status=:status")
	public List<Loanoverdue> findByStatus2(@Param("status") Integer status);
	
	@Query("select l from Loanoverdue l where l.loan.id=:loanId")
	public List<Loanoverdue> findByLoanId(@Param("loanId") Integer loanId);
	
}
