package com.liyang.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import com.liyang.domain.department.*;
import com.liyang.domain.orderrzzl.Orderrzzl;
import com.liyang.util.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jpa.query.expression.QueryExpSpecificationBuilder;
import com.jpa.query.expression.generic.GenericQueryExpSpecification;
import com.liyang.domain.base.AbstractAuditorAct;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.AbstractWorkflowAct.ActType;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.base.AbstractAuditorAct.ActGroup;
import com.liyang.domain.role.Role;
import com.liyang.domain.role.RoleAct;
import com.liyang.domain.role.RoleRepository;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserAct;
import com.liyang.domain.user.UserActRepository;
import com.liyang.domain.user.UserLog;
import com.liyang.domain.user.UserLogRepository;
import com.liyang.domain.user.UserRepository;
import com.liyang.domain.user.UserState;
import com.liyang.domain.user.UserStateRepository;
import com.liyang.domain.user.UserFile;
import com.liyang.domain.user.UserWorkflow;
import com.liyang.domain.user.UserWorkflowRepository;
import com.liyang.util.CommonUtil;
import com.liyang.util.FailReturnObject;
import com.liyang.util.MD5Util;
import com.liyang.util.ReturnObject.Level;

@Service
@Order(100)
public class UserService extends AbstractWorkflowService<User, UserWorkflow, UserAct, UserState, UserLog, UserFile>
        implements UserDetailsService {

    @Autowired
    UserActRepository userActRepository;

    @Autowired
    UserStateRepository userStateRepository;

    @Autowired
    UserLogRepository userLogRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserWorkflowRepository userWorkflowRepository;
    @Autowired
    TIMService timService;

    @Autowired
    HttpServletRequest request;
    @Autowired
    CacheManager cacheManager;
    @Autowired
    AccountService accountService;
    @Value(value = "${spring.enableSmsCode:false}")
    Boolean enableSmsCode;

    @Override
    @PostConstruct
    public void sqlInit() {
        System.out.println("userService初始化");
        
        	long findAll = userActRepository.count();
	        if(findAll == 0 ){
            UserWorkflow userApplyWorkflow = new UserWorkflow();
            userApplyWorkflow.setLabel("入职流程");
            userApplyWorkflow = userWorkflowRepository.save(userApplyWorkflow);

            UserAct save1 = userActRepository.save(new UserAct("创建", "create", 10, ActGroup.UPDATE));
            UserAct save2 = userActRepository.save(new UserAct("读取", "read", 20, ActGroup.READ));
            UserAct save3 = userActRepository.save(new UserAct("更新", "update", 30, ActGroup.UPDATE));
            UserAct save4 = userActRepository.save(new UserAct("删除", "delete", 40, ActGroup.UPDATE));
            UserAct save5 = userActRepository.save(new UserAct("自己的列表", "listOwn", 50, ActGroup.READ));
            UserAct save6 = userActRepository.save(new UserAct("部门的列表", "listOwnDepartment", 60, ActGroup.READ));
            UserAct save7 = userActRepository
                    .save(new UserAct("部门和下属部门的列表", "listOwnDepartmentAndChildren", 70, ActGroup.READ));
            UserAct save8 = userActRepository.save(new UserAct("通知其他人", "noticeOther", 80, ActGroup.NOTICE));
            UserAct save9 = userActRepository.save(new UserAct("通知操作者", "noticeActUser", 90, ActGroup.NOTICE));
            UserAct save10 = userActRepository.save(new UserAct("通知展示人", "noticeShowUser", 100, ActGroup.NOTICE));
            UserAct save11 = userActRepository.save(new UserAct("修改其他人信息", "updateOthers", 110, ActGroup.UPDATE));

            UserState newState = new UserState("待创建", 0, "UNBORN");
            newState.setActs(Arrays.asList(save1).stream().collect(Collectors.toSet()));
            newState = userStateRepository.save(newState);

            UserState applyState = userStateRepository.save(new UserState("待审核", 100, "APPLIED"));
            UserState enableState = new UserState("有效", 300, "ENABLED");

            enableState.setActs(Arrays.asList(save1, save2, save3, save4, save5, save6, save7, save11).stream()
                    .collect(Collectors.toSet()));
            userStateRepository.save(enableState);
            userStateRepository.save(new UserState("无效", 400, "DISABLED"));
            userStateRepository.save(new UserState("已删除", 500, "DELETED"));

            userApplyWorkflow.setStates(new HashSet<UserState>(Arrays.asList(newState, applyState)));
            userApplyWorkflow = userWorkflowRepository.save(userApplyWorkflow);

            save1.setActType(ActType.START);
            save1.setStartWorkflow(userApplyWorkflow);
            save1.setTargetState(applyState);

            Role role = roleRepository.findByRoleCode("USER");
            if (role != null) {
                save1.setRoles(new HashSet<Role>(Arrays.asList(role)));
            }
            userActRepository.save(save1);

        }

    }

    @Override
    public LogRepository<UserLog> getLogRepository() {
        // TODO Auto-generated method stub
        return userLogRepository;
    }

    @Override
    public AuditorEntityRepository<User> getAuditorEntityRepository() {

        return userRepository;
    }

    @Override
    @PostConstruct
    public void injectLogRepository() {
        new User().setLogRepository(userLogRepository);

    }

    @Override
    public UserLog getLogInstance() {
        // TODO Auto-generated method stub
        return new UserLog();
    }

    @Override
    public ActRepository<UserAct> getActRepository() {
        // TODO Auto-generated method stub
        return userActRepository;
    }

    @Override
    @PostConstruct
    public void injectEntityService() {
        new User().setService(this);

    }

    @Override
    public UserFile getFileLogInstance() {
        // TODO Auto-generated method stub
        return new UserFile();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        System.out.println("username:" + user.getUsername() + ";password:" + user.getPassword());
        List<Department> ownAndChildrenDepartments = CommonUtil.ownAndChildrenDepartments(user.getDepartment());
        user.setChildrenDepartments(ownAndChildrenDepartments);
        return user;
    }

    public void doCreate(User user) {

        if (user.getUsername() != null && user.getPassword() != null) {
            if (user.getUsername() == null && "".equals(user.getUsername().trim())) {
                throw new FailReturnObject(8661, "用户名不为空", Level.INFO);
            }
            if (user.getPassword() == null && "".equals(user.getPassword().trim())) {
                throw new FailReturnObject(8661, "密码不为空", Level.INFO);
            }
            if (!user.getPassword().equals(user.getPasswordAgain())) {
                throw new FailReturnObject(7667, "两次密码不一致", Level.INFO);
            }

            User user2 = userRepository.findByUsername(user.getUsername());
            if (user2 != null) {
                throw new FailReturnObject(5441, "用户名已经存在", Level.INFO);
            }
//			user.setPassword(MD5Util.encode(user.getPassword()));

            if (user.getDepartment() == null && CommonUtil.getPrincipal() != null
                    && CommonUtil.getPrincipal().getDepartment() == null) {
                user.setDepartment(CommonUtil.getPrincipal().getDepartment());
            }

            Role role = roleRepository.findByRoleCode("USER");
            user.setRole(role);
        }

    }

    public User register(HttpServletRequest request) {
//        User user = new User();
//        if (request.getParameter("username") == null || "".equals(request.getParameter("username").trim())) {
//            throw new FailReturnObject(8661, "用户名不为空", Level.INFO);
//        }
//        user.setUsername(request.getParameter("username"));
//        if (request.getParameter("password") == null || "".equals(request.getParameter("password").trim())) {
//            throw new FailReturnObject(8661, "密码不为空", Level.INFO);
//        }
//        System.out.println(request.getParameter("passwordAgain"));
//        System.out.println(request.getParameter("password"));
//        System.out.println(!request.getParameter("passwordAgain").equals(request.getParameter("passwordAgain")));
//        System.out.println(!request.getParameter("password").equals(request.getParameter("passwordAgain")));
//        if (!request.getParameter("password").equals(request.getParameter("passwordAgain"))) {
//            throw new FailReturnObject(7667, "两次密码不一致", Level.INFO);
//        }
//        user.setPassword(request.getParameter("password"));
//
//        User user2 = userRepository.findByUsername(user.getUsername());
//        if (user2 != null) {
//            throw new FailReturnObject(5441, "用户名已经存在", Level.INFO);
//        }
//
//        UserState userState = userStateRepository.findByStateCode("ENABLED");
//        Role role = roleRepository.findByRoleCode("USER");
//        user.setState(userState);
//        user.setRole(role);
//        user = userRepository.save(user);
//        accountService.createdDefaultAccountByUser(user);
//        return user;

        return  baseRegister(request.getParameter("username"),request.getParameter("password"),"ENABLED");
    }

    public User baseRegister(String username,String password,String stateCode)
    {
        if (username == null || "".equals(username.trim())) {
            throw new FailReturnObject(8661, "用户名不为空", Level.INFO);
        }
        if ((userRepository.findByUsername(username))!= null) {
            throw new FailReturnObject(5441, "用户名已经存在", Level.INFO);
        }
        User user = new User();
        user.setUsername(username);

        if (password == null || "".equals(password.trim())) {
            throw new FailReturnObject(8661, "密码不为空", Level.INFO);
        }
        user.setPassword(password);
        UserState userState = userStateRepository.findByStateCode(stateCode);
        Role role = roleRepository.findByRoleCode("USER");
        user.setState(userState);
        user.setRole(role);
        user = userRepository.save(user);
        accountService.createdDefaultAccountByUser(user);
        return user;

    }

    /**
     * 修改密码
     *
     * @param user
     */
    public void doModifypassword(User user) {
        User user2 = userRepository.findUserByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (user2 == null) {
            throw new FailReturnObject(1455, "修改失败", Level.INFO);
        }
        user2.setPassword(user.getNewPassword());
        System.out.println("新密码："+user.getNewPassword());
        userRepository.save(user2);
    }

    public void resetPassword(HttpServletRequest  request) {
        verifySmsCode(request.getParameter("username"),request.getParameter("smscode"));
        User user1 = userRepository.findByUsername(request.getParameter("username"));
        user1.setPassword(request.getParameter("password"));
        userRepository.save(user1);
    }

    @Deprecated
    public void verifySmsCode(String mobile, HttpServletRequest request) {
        if(mobile==null){
        	
            throw new FailReturnObject(1543,"请输入手机号",Level.INFO);
        }
        String smsCode = cacheManager.getCache("smsCode").get(mobile, String.class);
        System.out.println("smscode----"+smsCode);
        if (request.getParameter("smscode") == null || !request.getParameter("smscode").equals(smsCode)) {
            throw new FailReturnObject(2536, "验证码输入错误", ReturnObject.Level.INFO);
        }
        cacheManager.getCache("smsCode").put(mobile,null);
    }
    public void verifySmsCode(String mobile, String  smscode) {
        if(mobile==null){
            throw new FailReturnObject(1543,"请输入手机号",Level.INFO);
        }

        if(!enableSmsCode){
            if(!"0000".equals(smscode)){
                throw new FailReturnObject(2536, "验证码输入错误", ReturnObject.Level.INFO);
            }
            return;
        }


        String smsCode = cacheManager.getCache("smsCode").get(mobile, String.class);
        System.out.println("smscode----"+smsCode);
        if (smscode == null || !smscode.equals(smsCode)) {
            throw new FailReturnObject(2536, "验证码输入错误", ReturnObject.Level.INFO);
        }
        cacheManager.getCache("smsCode").put(mobile,null);
    }
    
    
    //根据条件动态查询user并分页
    public Page<User> getUsers(User user,Pageable pageable){ 
    	
	    GenericQueryExpSpecification<User> queryExpression = new GenericQueryExpSpecification<User>();
	    queryExpression.add(QueryExpSpecificationBuilder.eq("state.stateCode", user.getState().getStateCode(), true));
	    queryExpression.add(QueryExpSpecificationBuilder.eq("nickname", user.getNickname(), true));
		queryExpression.add(QueryExpSpecificationBuilder.eq("username", user.getUsername(), true));
		
		return userRepository.findAll(queryExpression,pageable);

    }
    
    //根据条件动态查询user并分页
    public Page<User> getBackgroundUsers(String roleCode,String stateCode,Pageable pageable){ 
    	
		return userRepository.findAllByRoleNot(roleCode,stateCode,pageable);
    }

    //根据companyid查用户
    public List<User> getUserByCompany(Integer companyId){   	
    	List<User> userList = userRepository.findByCompany(companyId);
    	return userList;
    }
    

}
