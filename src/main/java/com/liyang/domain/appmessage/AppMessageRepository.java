package com.liyang.domain.appmessage;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.liyang.domain.base.EntityRepository;

public interface AppMessageRepository extends EntityRepository<AppMessage>{
	
	@Query("select a from AppMessage a join a.toUser u where u.id=:userid")
	public Page<AppMessage> findByToUser(@Param("userid") Integer userid, Pageable pageable);
	
	//查询用户下面是否有未读消息
	@Query("select count(1)  from AppMessage a join a.toUser u where u.id=:userid and reading=0")
	public Integer findByUserAndReading(@Param("userid") Integer userid);
}
