package com.liyang.domain.ordercdd;


import com.liyang.domain.base.WorkflowEntityRepository;
import com.liyang.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Collection;
import java.util.List;
import java.util.Set;

//@RepositoryRestResource(excerptProjection = UserProjection.class)
public interface OrdercddRepository extends WorkflowEntityRepository<Ordercdd> {
    @Query("select m from Ordercdd m JOIN m.state s where s.stateCode = :stateCode")
    public Page<Ordercdd> getByStateCode(@Param("stateCode") String stateCode, Pageable pageable);

    @Query("select o from Ordercdd o  inner join o.state state where o.applyMobile=?1 and state.stateCode=?2")
    public Ordercdd getByTelephoneAndStatCode(String telephone, String stateCode);
    @Query("select o from Ordercdd o  inner join o.state state where o.applyIdentityNo=?1 and state.stateCode=?2")
    public Ordercdd getByIdentityNoAndStatCode(String identityNo, String stateCode);

    //@Query("select o from Ordercdd o inner join o.createBy user where user.id=?1")
    @RestResource(exported = false)
    public Page<Ordercdd> getByUser(Integer uid, Pageable pageable);

    //查找用户
    //public User getUserByOrdercdd()
    @Query("select o from Ordercdd o where o.applyMobile=?1")
    @RestResource(exported = false)
    public Ordercdd getByMobile(String mobile);

    //查询没有分配的
    @Query("select o from Ordercdd o inner join o.state s where s.stateCode=:stateCode and o.isDistribution=0")
    public Page<Ordercdd> findAllByIsDistribution(@Param("stateCode") String stateCode, Pageable pageable);

    @Query("select o from Ordercdd o inner join o.state s where s.stateCode=:stateCode")
    public Page<Ordercdd> listOwnByStateCode(@Param("stateCode") @Value("ENABLED") String stateCode, Pageable pageable);
    //业务员费配到自己下的业务      这个可以改成业务人员登陆的id

    @Query("select o from Ordercdd o inner join o.state s where s.stateCode=:stateCode and o.isDistribution=1 and o.serviceUser.id= ?#{principal.id}")
    public Page<Ordercdd> getOrdercddByServiceUserId(@Param("stateCode") String stateCode, Pageable pageable);

    @Query("select o from Ordercdd o where o.serviceUser.id=:userid and o.state.stateCode=:stateCode")
    public Page<Ordercdd> getOrdercddByStateCodeAndUser(@Param("stateCode") String stateCode, @Param("userid") Integer userid, Pageable pageable);

    //根据申请人手机号查询
    public Ordercdd findByApplyMobile(String telephone);

    //根据申请人身份证号查询
    public Ordercdd findByApplyIdentityNo(String identityNo);

    @RestResource(exported = false)
    public List<Ordercdd> findAllByApplyMobileAndStateIn(String applyMobile, Collection<OrdercddState> states);

    @Query("select o from Ordercdd o where o.user.id= ?#{principal.id}")
//    @PreAuthorize("hasRole('USER')")
    List<Ordercdd> getOrdercddByUser();

//    @RestResource(exported = false)
//    Ordercdd findByApplyIdentityNo

    ///根据服务人员获取全部
    @Query(value = "select o from Ordercdd o where o.serviceUser.id =?#{principal.id}")
    Page<Ordercdd> getAllByServiceUser(Pageable pageable);


    @RestResource(exported = false)
    List<Ordercdd> findAllByServiceUserAndStateIn(User serviceUser,Collection<OrdercddState> states);
}
