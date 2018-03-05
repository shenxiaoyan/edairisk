package com.liyang.domain.base;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.liyang.domain.user.User;
@NoRepositoryBean
public interface LogRepository<T extends AbstractAuditorLog> extends JpaRepository<T,Integer> {

	@Query(value="select t.noticeTo from #{#entityName} t  where t.createdBy = ?#{principal}  group by t.noticeTo.id order by  any_value(t.createdAt) desc")
	public Page<User> latestNoticeUser(Pageable page);
	
//	@RestResource(exported = false)
//	public List<T>  findByEntityAndCreatedByAndStateCodeNot(AbstractAuditorEntity entity,User user, String state, Sort sort);
//	@RestResource(exported = false)
//	public Page<T>  findByEntityAndCreatedByAndStateCodeNot(AbstractAuditorEntity entity,User user, String state, Pageable page);
//	
//	@RestResource(exported = false)
//	public List<T>  findByEntityAndStateCodeNot(AbstractAuditorEntity entity, String state);
//	@RestResource(exported = false)
//	public List<T>  findByEntityAndStateCodeNot(AbstractAuditorEntity entity, String state, Sort sort);
//	@RestResource(exported = false)
//	public Page<T>  findByEntityAndStateCodeNot(AbstractAuditorEntity entity, String state, Pageable page);
	
	

}
