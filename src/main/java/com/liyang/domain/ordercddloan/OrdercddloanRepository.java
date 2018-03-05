package com.liyang.domain.ordercddloan;


import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import com.liyang.domain.base.WorkflowEntityRepository;

public interface OrdercddloanRepository extends WorkflowEntityRepository<Ordercddloan> {
    @Query("select m from Ordercddloan m JOIN m.state s where s.stateCode = :stateCode")
    public Page<Ordercddloan> getByStateCode(@Param("stateCode") String stateCode, Pageable pageable);

    @Query("select o from Ordercddloan o  inner join o.state state where o.applyMobile=?1 and state.stateCode=?2")
    public Ordercddloan getByTelephoneAndStatCode(String telephone, String stateCode);
    @Query("select o from Ordercddloan o  inner join o.state state where o.applyIdentityNo=?1 and state.stateCode=?2")
    public Ordercddloan getByIdentityNoAndStatCode(String identityNo, String stateCode);

    //@Query("select o from Ordercdd o inner join o.createBy user where user.id=?1")
    @RestResource(exported = false)
    public Page<Ordercddloan> getByUser(Integer uid, Pageable pageable);

    //查找用户
    //public User getUserByOrdercdd()
    @Query("select o from Ordercddloan o where o.applyMobile=?1")
    @RestResource(exported = false)
    public Ordercddloan getByMobile(String mobile);

    //查询没有分配的
    @Query("select o from Ordercddloan o inner join o.state s where s.stateCode=:stateCode and o.isDistribution=0")
    public Page<Ordercddloan> findAllByIsDistribution(@Param("stateCode") String stateCode, Pageable pageable);

    @Query("select o from Ordercddloan o inner join o.state s where s.stateCode=:stateCode")
    public Page<Ordercddloan> listOwnByStateCode(@Param("stateCode") @Value("ENABLED") String stateCode, Pageable pageable);
    //业务员费配到自己下的业务      这个可以改成业务人员登陆的id

    @Query("select o from Ordercddloan o inner join o.state s where s.stateCode=:stateCode and o.isDistribution=1 and o.serviceUser.id= ?#{principal.id}")
    public Page<Ordercddloan> getOrdercddByServiceUserId(@Param("stateCode") String stateCode, Pageable pageable);

    @Query("select o from Ordercddloan o where o.serviceUser.id=:userid and o.state.stateCode=:stateCode")
    public Page<Ordercddloan> getOrdercddByStateCodeAndUser(@Param("stateCode") String stateCode, @Param("userid") Integer userid, Pageable pageable);

    //根据申请人手机号查询
    public Ordercddloan findByApplyMobile(String telephone);

    //根据申请人身份证号查询
    public Ordercddloan findByApplyIdentityNo(String identityNo);

    @RestResource(exported = false)
    public List<Ordercddloan> findAllByApplyMobileAndStateIn(String applyMobile, Collection<OrdercddloanState> states);

    @Query("select o from Ordercddloan o where o.user.id= ?#{principal.id}")
    @PreAuthorize("hasRole('USER')")
    List<Ordercddloan> getOrdercddByUser();
}
