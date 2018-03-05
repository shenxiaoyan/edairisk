package com.liyang.domain.user;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.jpa.query.expression.generic.SpecificationQueryRepository;
import com.liyang.domain.base.WorkflowEntityRepository;
import com.liyang.domain.department.Department;
import com.liyang.domain.orderrzzl.Orderrzzl;
import com.liyang.domain.role.Role;


public interface UserRepository extends WorkflowEntityRepository<User>,SpecificationQueryRepository<User>{
    @Query("select u from User u where u.unionid=?1")
    @RestResource(exported = false)
    public List<User> findByUnionid(String unionid);

    public User findByOpenid(@Param("openid") String openid);

    public List<User> findByRoleRoleCode(@Param("roleCode") String roleCode);

    @RestResource(exported = false)
    public List<User> findByDepartmentAndRole(Department department, Role role);

    @RestResource(exported = false)
    public List<User> findByDepartmentIn(List<Department> ids);

    public User findByUsername(@Param("username") String username);

    @RestResource(exported = false)
    public User findUserByUsernameAndPassword(String username, String password);
/*	@Query("select us from User us where us.id in (select s.serviceUserId from User s  where s in (select u from Orderwdsjsh o inner join o.createdBy u where o.id=:orderwdsjshId))")
    public User getByOrderWdsjsh(@Param("orderwdsjshId")Integer id);
*/

//    public Page<User> findByState_StateCode(String stateCode,Pageable pageable);

    @RestResource(exported = false)

//    @Query(value = "select u from User u where u.id= :id")
    UserInfoProjection findUserById(Integer id);

    @Query(value = "select u from User u join u.role r join u.state s where r.roleCode!=:role_code and s.stateCode=:stateCode and u.createdByDepartment in ?#{principal?.childrenDepartments}")
    Page<User> findAllByRoleNot(@Param("role_code")String roleCode,@Param("stateCode")String stateCode, Pageable pageable);
    
    @Query(value = "select u from User u join u.role r join u.state s where r.roleCode=:role_code and s.stateCode=:stateCode and u.createdByDepartment in ?#{principal?.childrenDepartments}")
    Page<User> findAllByRole(@Param("role_code")String role_code, @Param("stateCode")String stateCode,Pageable pageable);
    
    @Query(value = "select u from User u join u.role r join u.state s where r.roleCode=:role_code and s.stateCode=:stateCode")
    Page<User> findAllByRoleAndState(@Param("role_code")String role_code, @Param("stateCode")String stateCode,Pageable pageable);
    

    @Query(value = "select u from  User  u join  u.department d where d.id=:departmentId")
    Page<User> findAllByDepartment_Id(@Param("departmentId") Integer departmentId,Pageable pageable);


    @RestResource(exported = false)//多角色通知
    List<User> findAllByDepartmentAndRoleIn(Department department,Collection<Role> roles);
    
    @Query("select u from User u where u.id=:userid")
    User findByUserId(@Param("userid") Integer userid);
    
    
    @Query("select u from User u  where u.company.id=:companyId")
    public List<User> findByCompany(@Param("companyId") Integer companyId);

    @Query("SELECT  u.username from User u where u.smsNoticer=1")
    public List<String> findUsernameBySmsNoticer();
    
    
}