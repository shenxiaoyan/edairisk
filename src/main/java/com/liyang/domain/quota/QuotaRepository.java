package com.liyang.domain.quota;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jpa.query.expression.generic.SpecificationQueryRepository;
import com.liyang.domain.orderwdsjsh.Orderwdsjsh;

//@RepositoryRestResource(excerptProjection = UserProjection.class)
public interface QuotaRepository extends JpaRepository<Quota, Integer> ,SpecificationQueryRepository<Quota>{
	
	//@Query("select q from Quota q  where q.productrecordid=:id and date_format(q.applicationtime ,'%M-%d')=date_format(now(),'%M-%d') ")
	//public List<Quota> findByQuotaApplicationTime(@Param("id") Integer idsss);
	@Query("select q from Quota q  where q.creditcard.id=:creditcardId and q.stateCode in ('CREATED','ENABLED')  and datediff( now(),q.applicationTime)<7")		
	//@Query("select q from Quota q  where q.creditcard.id=:creditcardId and q.stateCode in ('CREATED','ENABLED')  and date_format(q.applicationTime ,'%Y-%m')=date_format(now(),'%Y-%m')")
	public List<Quota> findByCreditcardAndApplycationTime(@Param("creditcardId") Integer creditcardId);
		
}