package com.liyang.domain.ordermdbt;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.jpa.query.expression.generic.SpecificationQueryRepository;
import com.liyang.domain.base.WorkflowEntityRepository;
import com.liyang.domain.orderwdsjsh.Orderwdsjsh;
import com.liyang.domain.user.User;

//@RepositoryRestResource(excerptProjection = UserProjection.class)
public interface OrdermdbtRepository extends WorkflowEntityRepository<Ordermdbt> ,SpecificationQueryRepository<Ordermdbt>{

	
    @Query("select m from Ordermdbt m JOIN m.state s where s.stateCode = :stateCode")
    public Page<Ordermdbt> getByStateCode(@Param("stateCode") String stateCode, Pageable pageable);

    @Query("select o from Ordermdbt o  inner join o.state state where o.applyMobile=?1 and state.stateCode=?2")
    public Ordermdbt getByTelephoneAndStatCode(String telephone, String stateCode);

    //@Query("select o from Ordermdbt o inner join o.createBy user where user.id=?1")
    @RestResource(exported = false)
    public Page<Ordermdbt> getByUser(Integer uid, Pageable pageable);

    //查找用户
    //@Query("select u from User u inner join Ordermdbt o where o.createBy.id=")
    //public User getUserByOrdermdbt()
    @Query("select o from Ordermdbt o where o.applyMobile=?1")
    @RestResource(exported = false)
    public Ordermdbt getByMobile(String mobile);

    //查询没有分配的
    @Query("select o from Ordermdbt o inner join o.state s where s.stateCode=:stateCode and o.isDistribution=0")
    public Page<Ordermdbt> findAllByIsDistribution(@Param("stateCode") String stateCode, Pageable pageable);

    @Query("select o from Ordermdbt o inner join o.state s where s.stateCode=:stateCode")
    public Page<Ordermdbt> listOwnByStateCode(@Param("stateCode") @Value("ENABLED") String stateCode, Pageable pageable);
    //select m from #{#entityName} m JOIN m.state s where s.stateCode = :stateCode
    //业务员费配到自己下的业务      这个可以改成业务人员登陆的id
//		@Query("select o from Ordermdbt o inner join o.state s where s.stateCode=:stateCode and o.isDistribution=1 and o.createdBy in  (select u.id from User u where u.serviceUserId=?#{principal.id})")
//		public Page<Ordermdbt> getOrdermdbtByServiceUserId(@Param("stateCode")String stateCode, Pageable pageable);

    @Query("select o from Ordermdbt o inner join o.state s where s.stateCode=:stateCode and o.isDistribution=1 and o.serviceUser.id= ?#{principal.id}")
    public Page<Ordermdbt> getOrdermdbtByServiceUserId(@Param("stateCode") String stateCode, Pageable pageable);

    //		@Query("select o from Ordermdbt o where o.serviceUserId=:userid and o.state.stateCode=:stateCode")
//		public Page<Ordermdbt> getOrdermdbtByStateCodeAndUser(@Param("stateCode")String stateCode,@Param("userid")String userid, Pageable pageable);
    @Query("select o from Ordermdbt o where o.serviceUser.id=:userid and o.state.stateCode=:stateCode")
    public Page<Ordermdbt> getOrdermdbtByStateCodeAndUser(@Param("stateCode") String stateCode, @Param("userid") Integer userid, Pageable pageable);

    //根据申请人手机号查询
    public Ordermdbt findByApplyMobile(String telephone);

    //根据申请人身份证号查询
    public Ordermdbt findByApplyIdentityNo(String identityNo);

    @RestResource(exported = false)
    Ordermdbt findFirstByCreatedBy(User user);
    
    @Query("select o from Ordermdbt o  where o.createdBy.id=:userid ")
    public List<Ordermdbt> findByCreateBy( @Param("userid") Integer userid);
    
    @Query("select o from Ordermdbt o  inner join o.product pd where o.applyMobile=?1 and pd.id=?2")
    public Ordermdbt getByApplyIdentityNoAndProductid(String applyMobile, Integer productid);
    
    @Query("select o from Ordermdbt o where o.person.id=:personId ")
    public Ordermdbt findByPersonId(@Param("personId") Integer personId);
    
    @Query("select count(*) from Ordermdbt o where o.state.stateCode in ('CREATED','ENABLED')")
    public int getOrdermdbtAmount();
    
    @Query("select count(*) from Ordermdbt o where o.state.stateCode = 'DENIED'")
    public Integer getOrdermdbtDeniedSum();
    
    @Query(" select o from Ordermdbt o  where o.applyIdentityNo=:applyIdentityNo and o.state.stateCode not in ('DISABLED')")
    public Ordermdbt findByApplyIdentityNoAndState(@Param("applyIdentityNo") String applyIdentityNo);
}