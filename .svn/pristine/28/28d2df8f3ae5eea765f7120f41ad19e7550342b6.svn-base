package com.liyang.listener;

import com.liyang.domain.user.*;
import com.liyang.service.AccountService;
import com.liyang.service.TIMService;
import com.liyang.util.CommonUtil;
import com.liyang.util.TIMSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRestEventListener extends WorkflowRestEventListener<User, UserWorkflow, UserAct, UserState, UserLog, UserFile> {

    @Autowired
    private TIMService timService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountService accountService;
    @Override
    protected void onAfterCreate(User entity) {

        entity.setSig(TIMSignature.generate(entity.getId().toString()).urlSig);
        userRepository.save(entity);
        timService.addUser(entity.getId().toString(), entity.getUsername(), "");
        //创建默认account
        //begin 新建完user创建默认的account --Jax
        accountService.createdDefaultAccountByUser(entity);
        //end
        super.onAfterCreate(entity);
    }
}
