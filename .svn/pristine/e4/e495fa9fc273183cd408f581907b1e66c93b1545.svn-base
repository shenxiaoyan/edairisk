package com.liyang.domain.loan;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.Parent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jpa.query.expression.generic.SpecificationQueryRepository;
import com.liyang.domain.account.Account;
import com.liyang.domain.base.WorkflowEntityRepository;
import com.liyang.domain.loan.Loan.LoanType;

//@RepositoryRestResource(excerptProjection = UserProjection.class)
public interface LoanRepository extends WorkflowEntityRepository<Loan> ,SpecificationQueryRepository<Loan>{
	//显示账户下的贷款
	@Query("select l from Loan l inner join l.state s where s.stateCode=:stateCode and l.creditcard in (select a from Creditcard a where a.id=:creditcardId)")
	public Page<Loan> listOwnLoan(@Param("creditcardId") Integer creditcardId,@Param("stateCode")String stateCode,Pageable pageable);
	
	
		
	//select m from #{#entityName} m JOIN m.state s where s.stateCode = :stateCode
	//业务员费配到自己下的业务      这个可以改成业务人员登陆的id
	//@Query("select o from Loan o  inner join o.debtorUser u  where o.state.stateCode=:stateCode and u.id  in  (select u.id from User u where u.serviceUserId=?#{principal.id})")
	//public Page<Loan> getLoanByServiceUserId(@Param("stateCode")String stateCode, Pageable pageable);

	@Query("select o from Loan o  where o.serviceUser.id =?#{principal.id}")
	public Page<Loan> getLoanByServiceUserId(@Param("stateCode")String stateCode, Pageable pageable);
	
	@Query("select l from Loan l   where l.creditcard.id=:creditcardId and date_format(l.applyDate ,'%M-%d')=date_format(now(),'%M-%d') ")
	public List<Loan> findByCreditcardAndApplyDateDay(@Param("creditcardId") Integer creditcardId);
	
	@Query("select l from Loan l   where l.creditcard.id=:creditcardId and date_format(l.applyDate ,'%Y-%m')=date_format(now(),'%Y-%m') ")
	public List<Loan> findByCreditcardAndApplyDateMonth(@Param("creditcardId") Integer creditcardId);
	
	@Query("select l from Loan l where l.createdBy.id=:userid and l.loanType=:loanType and l.state.stateCode=:stateCode and l.remainAmount=:remainAmount")
	public Page<Loan> findByCreatedByAndState(@Param("userid") Integer userid ,@Param("loanType") LoanType loanType,@Param("stateCode") String stateCode,@Param("remainAmount") BigDecimal remainAmount,Pageable pageable);
	
	@Query("select l from Loan l where l.createdBy.id=:userid and l.loanType=:loanType and l.state.stateCode=:stateCode and l.remainAmount!=:remainAmount")
	public Page<Loan> findByCreatedBy(@Param("userid") Integer userid ,@Param("loanType") LoanType loanType,@Param("stateCode") String stateCode,@Param("remainAmount") BigDecimal remainAmount,Pageable pageable);
	
	@Query("select l from Loan l where l.createdBy.id=:userid and l.loanType=:loanType and l.state.stateCode=:stateCode")
	public Page<Loan> findByStateCode(@Param("stateCode") String stateCode,@Param("userid") Integer userid ,@Param("loanType") LoanType loanType,Pageable pageable);
	
	@Query("select l from Loan l  where l.creditcard.company.id=:companyId")
	public Page<Loan> findByCompanyId(@Param("companyId") Integer companyId,Pageable pageable);
	
	@Query("select l from Loan l where l.createdBy.id=:userid and l.loanType=:loanType and l.overdue=1")
	public List<Loan> findByCreatedByAndOverdue(@Param("userid") Integer userid,@Param("loanType") LoanType loanType);
	
	@Query("select l from Loan l where l.createdBy.id=:userid and l.loanType=:loanType and l.state.stateCode in('CREATED','NOTLEND')")
	public Page<Loan> findByStateAndCreatedBy(@Param("userid") Integer userid,@Param("loanType") LoanType loanType,Pageable pageable);
	
	@Query("select l from Loan l where l.creditcard.id=:creditcardId and l.state.stateCode in('ENABLED','NOTLEND','PAYOF')")
	public List<Loan> findByCreditcardId(@Param("creditcardId") Integer creditcardId);
	
	@Query("select count(distinct lc.id) from Loan l inner join l.creditcard lc inner join l.state ls "
			+ "where lc.product.id = 1 and lc.state.id = 2 and lc.creditBalance = lc.creditGrant and ls.stateCode in ('NOTLEND','ENABLED','PAYOF')")
	public int getOrderwdsjshSettleCreditcardSum();
	
	@Query("select count(distinct lc.id) from Loan l inner join l.creditcard lc inner join l.state ls "
			+ "where lc.product.id = 4 and lc.state.id = 2 and lc.creditBalance = lc.creditGrant and ls.stateCode in ('NOTLEND','ENABLED','PAYOF')")
	public int getOrdermdbtSettleCreditcardSum();
		
	@Query("select l from Loan l where l.state.stateCode = 'ENABLED' and datediff(now(),l.createdAt) = :days")
	public List<Loan> findByCreatedAt(@Param("days") int days);
}
