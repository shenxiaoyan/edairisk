package com.liyang.domain.creditrepayplan;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.liyang.domain.base.WorkflowEntityRepository;
import com.liyang.domain.creditrepayment.Creditrepayment;
import org.springframework.data.rest.core.annotation.RestResource;

//@RepositoryRestResource(excerptProjection = UserProjection.class)
public interface CreditrepayplanRepository extends WorkflowEntityRepository<Creditrepayplan> {
	//根据creditcardId找某张信用卡的所有还款计划列表
	@Query("select r from Creditrepayplan r inner join r.creditcard a where a.id=:creditcardId")
	public Page<Creditrepayplan> getCreditRepayplanByCreditcard(@Param("creditcardId")Integer creditcardId,Pageable pageable);

	//根据LoanId找到某笔贷款的还款计划列表
	@Query("select r from Creditrepayplan r inner join r.loan a where a.id=:loanId")
	public Page<Creditrepayplan> getCreditRepayplanByLoan(@Param("loanId")Integer loanId,Pageable pageable);
	
	@Query("select r from Creditrepayplan r inner join r.loan a where a.id=:loanId")
	public List<Creditrepayplan> getByLoan(@Param("loanId")Integer loanId);
	
	@Query("select r from Creditrepayplan r inner join r.state state where state.stateCode=?1")
	public List<Creditrepayplan> getByStatCode(String stateCode);
	
	@Query("select r from Creditrepayplan r inner join r.state state where state.stateCode=?1 and r.leftAmount > ?2")
	public List<Creditrepayplan> getByStatCodeAndLeftAmountGreaterThan(String stateCode,BigDecimal leftAmount);
	@RestResource(exported = false)
	Creditrepayplan findByPlanSn(String planSN);
	
	@Query("select c from Creditrepayplan  c  join c.loan l where c.term=:term and l.id=:loanId")
	public Creditrepayplan findByLoanIdAndTerm(@Param("loanId") Integer loanId,@Param("term") Integer term);
	
	@Query("select c  from  Creditrepayplan c  where c.isOverdue=:overdue")
	public List<Creditrepayplan> findByIsOverdue(@Param("overdue") Integer overdue);
}