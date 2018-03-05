package com.liyang.domain.account;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.liyang.domain.base.WorkflowEntityRepository;
import com.liyang.domain.orderwdsjsh.Orderwdsjsh;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Repository;

//@RepositoryRestResource(excerptProjection = UserProjection.class)
public interface AccountRepository extends WorkflowEntityRepository<Account> {
	//根据persondid找到账户
	//public Account findByPerson_id(@Param("personId")Integer personId);
	
	//public Account findByAccountIdentity(@Param("accountIdentity")Long accountIdentity);


    //根据user_id查account
    public Account findAccountByUserId(Integer user_id);
	
}