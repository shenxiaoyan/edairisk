package com.liyang.domain.approveloan;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jpa.query.expression.generic.SpecificationQueryRepository;
import com.liyang.domain.orderwdsjsh.Orderwdsjsh;

//@RepositoryRestResource(excerptProjection = UserProjection.class)
public interface ApproveLoanStateRepository extends JpaRepository<ApproveLoanState, Integer> {
		
	@Query("select a from ApproveLoanState a where a.stateCode=:stateCode")
	public ApproveLoanState findByStateCode(@Param("stateCode") String stateCode);

}