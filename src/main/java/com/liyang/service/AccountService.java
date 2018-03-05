package com.liyang.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import com.liyang.domain.user.User;
import com.liyang.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.liyang.domain.account.Account;
import com.liyang.domain.account.AccountAct;
import com.liyang.domain.account.AccountActRepository;
import com.liyang.domain.account.AccountFile;
import com.liyang.domain.account.AccountLog;
import com.liyang.domain.account.AccountLogRepository;
import com.liyang.domain.account.AccountRepository;
import com.liyang.domain.account.AccountState;
import com.liyang.domain.account.AccountStateRepository;
import com.liyang.domain.account.AccountWorkflow;
import com.liyang.domain.base.AbstractAuditorAct.ActGroup;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.orderwdsjsh.OrderwdsjshRepository;

@Service
@Order(10043)
public class AccountService
		extends AbstractWorkflowService<Account,AccountWorkflow, AccountAct,AccountState,AccountLog,AccountFile> {

	@Value("${spring.wlqz.wechat.OPEN_ACC_SUCCESS}")
	private String OPEN_ACC_SUCCESS;
	
	@Autowired
	AccountActRepository accountActRepository;
	
	@Autowired
	AccountStateRepository accountStateRepository;

	@Autowired
	AccountLogRepository accountLogRepository;
	
	@Autowired
	OrderwdsjshRepository orderwdsjshRepository;
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	WlqzWechatPubService wechatPubService;
	@Override
	@PostConstruct
	public void sqlInit() {
		 long findAll = accountActRepository.count();
	        if(findAll == 0 ){

			AccountAct save1 = accountActRepository.save(new AccountAct("创建", "create", 10, ActGroup.UPDATE));
			AccountAct save2 = accountActRepository.save(new AccountAct("读取", "read", 20, ActGroup.READ));
			AccountAct save3 = accountActRepository.save(new AccountAct("更新", "update", 30, ActGroup.UPDATE));
			AccountAct save4 = accountActRepository.save(new AccountAct("删除", "delete", 40, ActGroup.UPDATE));
			AccountAct save5 = accountActRepository.save(new AccountAct("自己的列表", "listOwn", 50, ActGroup.READ));
			AccountAct save6 = accountActRepository
					.save(new AccountAct("部门的列表", "listOwnDeparment", 60, ActGroup.READ));
			AccountAct save7 = accountActRepository
					.save(new AccountAct("部门和下属部门的列表", "listOwnDeparmentAndChildren", 70, ActGroup.READ));
			AccountAct save8 = accountActRepository
					.save(new AccountAct("通知其他人", "noticeOther", 80, ActGroup.NOTICE));
			AccountAct save9 = accountActRepository
					.save(new AccountAct("通知给操作者", "noticeActUser", 90, ActGroup.NOTICE));
			AccountAct save10 = accountActRepository
					.save(new AccountAct("通知给展示人", "noticeShowUser", 100, ActGroup.NOTICE));

			accountStateRepository.save(new AccountState("已创建", 0, "CREATED"));
			AccountState AccountState = new AccountState("有效", 100, "ENABLED");
			AccountState.setActs(Arrays.asList(save1, save2, save3, save4, save5, save6, save7).stream()
					.collect(Collectors.toSet()));
			accountStateRepository.save(AccountState);
			accountStateRepository.save(new AccountState("无效", 200, "DISABLED"));
			accountStateRepository.save(new AccountState("已删除", 300, "DELETED"));
			
			

		}

	}

	@Override
	public LogRepository<AccountLog> getLogRepository() {
		// TODO Auto-generated method stub
		return accountLogRepository;
	}

	@Override
	public AuditorEntityRepository<Account> getAuditorEntityRepository() {
		// TODO Auto-generated method stub
		return accountRepository;
	}

	@Override
	@PostConstruct
	public void injectLogRepository() {
		new Account().setLogRepository(accountLogRepository);

	}

	@Override
	public AccountLog getLogInstance() {
		// TODO Auto-generated method stub
		return new AccountLog();
	}

	@Override
	public ActRepository<AccountAct> getActRepository() {
		// TODO Auto-generated method stub
		return accountActRepository;
	}

	@Override
	@PostConstruct
	public void injectEntityService() {
		new Account().setService(this);

	}

	@Override
	public AccountFile getFileLogInstance() {
		// TODO Auto-generated method stub
		return new AccountFile();
	}


	/**
	 *根据用户新建默认的account,一般用在新建用户之后
	 * 1.WechatLoginService --- 160 line
	 * 2.UserRestEvent
	 * 3.UserService->login
	 * @param user
	 * @return Account
	 */
	public Account createdDefaultAccountByUser(User user)
	{
		Account old_account = accountRepository.findAccountByUserId(user.getId());
		System.out.println(old_account);
		if(old_account!=null){
			return old_account;
		}
		Account account=new Account();
		account.setUser(user);
		account.setDescription("缺省的");
		account.setCreatedBy(CommonUtil.getPrincipal());
		account.setCreatedBy(CommonUtil.getPrincipal());
		accountRepository.save(account);
		return account;
	}


}
