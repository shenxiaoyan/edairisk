package com.liyang.domain.person;



import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jpa.query.expression.generic.SpecificationQueryRepository;
import com.liyang.domain.base.WorkflowEntityRepository;
import com.liyang.domain.quota.Quota;

//@RepositoryRestResource(excerptProjection = RoleProjection.class)
public interface PersonRepository extends WorkflowEntityRepository<Person>, SpecificationQueryRepository<Person> {
	@Query("select p from Person p inner join p.state state where p.telephone=:telephone and state.stateCode=:stateCode")
	public Person getByTelephoneAndStateCode(@Param("telephone")String telephone,@Param("stateCode")String stateCode);
	
	public Person findByIdentity(String identity);

	public Person findByTelephone(String telephone);
}
