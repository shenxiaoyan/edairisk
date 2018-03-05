package com.liyang.service;

import com.liyang.domain.base.AbstractAuditorAct.ActGroup;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.creditcard.Creditcard;
import com.liyang.domain.creditrepayplan.*;
import com.liyang.domain.department.Departmenttype;
import com.liyang.domain.department.DepartmenttypeRepository;
import com.liyang.domain.loan.Loan;
import com.liyang.domain.ordercdd.Ordercdd;
import com.liyang.domain.role.RoleRepository;
import com.liyang.domain.user.User;
import com.liyang.util.CommonUtil;
import com.liyang.util.FailReturnObject;
import com.liyang.util.ReturnObject;
import com.liyang.util.WechatImageAsyncFetchEvent;

import javassist.expr.NewArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import static org.mockito.Matchers.intThat;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Order(313)
public class CreditrepayplanService extends
		AbstractWorkflowService<Creditrepayplan, CreditrepayplanWorkflow, CreditrepayplanAct, CreditrepayplanState, CreditrepayplanLog, CreditrepayplanFile> {
	@Autowired
	CreditrepayplanActRepository creditrepayplanActRepository;

	@Autowired
	CreditrepayplanStateRepository creditrepayplanStateRepository;

	@Autowired
	CreditrepayplanLogRepository creditrepayplanLogRepository;

	@Autowired
	CreditrepayplanRepository creditrepayplanRepository;
	//
	@Autowired
	CreditrepayplanWorkflowRepository creditrepayplanWorkflowRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	WlqzWechatPubService wechatPubService;

	@Override
	@PostConstruct
	public void sqlInit() {
		
			long findAll = creditrepayplanActRepository.count();
	        if(findAll == 0 ){
			CreditrepayplanAct save1 = creditrepayplanActRepository
					.save(new CreditrepayplanAct("创建", "create", 10, ActGroup.UPDATE));
			CreditrepayplanAct save2 = creditrepayplanActRepository
					.save(new CreditrepayplanAct("读取", "read", 20, ActGroup.READ));
			CreditrepayplanAct save3 = creditrepayplanActRepository
					.save(new CreditrepayplanAct("更新", "update", 30, ActGroup.UPDATE));
			CreditrepayplanAct save4 = creditrepayplanActRepository
					.save(new CreditrepayplanAct("删除", "delete", 40, ActGroup.UPDATE));
			CreditrepayplanAct save5 = creditrepayplanActRepository
					.save(new CreditrepayplanAct("自己的列表", "listOwn", 50, ActGroup.READ));
			CreditrepayplanAct save6 = creditrepayplanActRepository
					.save(new CreditrepayplanAct("部门的列表", "listOwnDepartment", 60, ActGroup.READ));
			CreditrepayplanAct save7 = creditrepayplanActRepository
					.save(new CreditrepayplanAct("部门和下属部门的列表", "listOwnDepartmentAndChildren", 70, ActGroup.READ));
			CreditrepayplanAct save8 = creditrepayplanActRepository
					.save(new CreditrepayplanAct("通知其他人", "noticeOther", 80, ActGroup.NOTICE));
			CreditrepayplanAct save9 = creditrepayplanActRepository
					.save(new CreditrepayplanAct("通知操作者", "noticeActUser", 90, ActGroup.NOTICE));
			CreditrepayplanAct save10 = creditrepayplanActRepository
					.save(new CreditrepayplanAct("通知展示人", "noticeShowUser", 100, ActGroup.NOTICE));

			CreditrepayplanState newState = new CreditrepayplanState("已创建", 0, "CREATED");
			newState = creditrepayplanStateRepository.save(newState);

			CreditrepayplanState enableState = new CreditrepayplanState("有效", 100, "ENABLED");
			enableState.setActs(Arrays.asList(save1, save2, save3, save4, save5, save6, save7).stream()
					.collect(Collectors.toSet()));
			creditrepayplanStateRepository.save(enableState);
			creditrepayplanStateRepository.save(new CreditrepayplanState("无效", 200, "DISABLED"));
			creditrepayplanStateRepository.save(new CreditrepayplanState("已删除", 300, "DELETED"));

			creditrepayplanStateRepository.save(new CreditrepayplanState("待出账", 30, "ACCOUNT"));
			creditrepayplanStateRepository.save(new CreditrepayplanState("已出账", 20, "ACCOUNTED"));
			creditrepayplanStateRepository.save(new CreditrepayplanState("已结清", 10, "CLOSED"));

		}

	}

	@Override
	public LogRepository<CreditrepayplanLog> getLogRepository() {
		// TODO Auto-generated method stub
		return creditrepayplanLogRepository;
	}

	@Override
	public AuditorEntityRepository<Creditrepayplan> getAuditorEntityRepository() {

		return creditrepayplanRepository;
	}

	@Override
	@PostConstruct
	public void injectLogRepository() {
		new Creditrepayplan().setLogRepository(creditrepayplanLogRepository);

	}

	@Override
	public CreditrepayplanLog getLogInstance() {
		// TODO Auto-generated method stub
		return new CreditrepayplanLog();
	}

	@Override
	public ActRepository<CreditrepayplanAct> getActRepository() {
		// TODO Auto-generated method stub
		return creditrepayplanActRepository;
	}

	@Override
	@PostConstruct
	public void injectEntityService() {
		new Creditrepayplan().setService(this);

	}

	@Override
	public CreditrepayplanFile getFileLogInstance() {
		// TODO Auto-generated method stub
		return new CreditrepayplanFile();
	}

	public void save(Creditrepayplan Creditrepayplan) {
		// wechatPubService.pushOpenAccMessage(toUser, url, templateId,
		// childMap);
		creditrepayplanRepository.save(Creditrepayplan);
	}

	public void insertPlayPlanByMdbt(Loan loan) {
		Integer periodNumber = loan.getPeriodNumber(); // 分期数，或叫还款次数
		Loan.PeriodCode periodCode = loan.getPeriodCode(); // 还款时间单位
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(loan.getUseDate());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		BigDecimal principal = new BigDecimal(0);
		for (int i = 0; i < periodNumber; i++) {
			Creditrepayplan creditrepayplan = new Creditrepayplan();
			creditrepayplan.setLoanSn(loan.getOrderNo());
			creditrepayplan.setTerm(i+1);
			creditrepayplan.setDebtorName(loan.getDebtorName());
			creditrepayplan.setPlanSn(dateFormat.format(new Date()) + loan.getDebtorUser().getId() + "-" + (i + 1));
			creditrepayplan.setCreditcard(loan.getCreditcard());
			creditrepayplan.setLoan(loan);
			creditrepayplan.setIsOverdue(0);
			if (periodNumber - i == 1) {
				principal = loan.getPrincipal();// 最后一期
			}
			creditrepayplan.setPrincipal(principal);
			switch (periodCode) {
			case DAY:
				if (i == 0) {
					calendar.add(Calendar.DATE, 29);
				} else {
					calendar.add(Calendar.DATE, 30);
				}

				break;
			default:
				throw new FailReturnObject(1534, "分期按天算", ReturnObject.Level.ERROR);
			}
			Date debtorRepaymentDate = calendar.getTime();
			creditrepayplan.setInterest(loan.getTotalInterest().divide(new BigDecimal(3))); // 每期利息
			creditrepayplan.setDebtorRepaymentDate(debtorRepaymentDate); // 还款日期
			creditrepayplan.setFees(creditrepayplan.getPrincipal().add(creditrepayplan.getInterest())); // 还款总和
			creditrepayplan.setLeftAmount(creditrepayplan.getPrincipal().add(creditrepayplan.getInterest()));

			String planState = "ACCOUNT";// 未出账
			if (i == 0) {// 第一期是已出账
				planState = "ACCOUNTED";
			}
			creditrepayplan.setState(creditrepayplanStateRepository.findByStateCode(planState));// 待出账

//			Calendar calendar1 = Calendar.getInstance();
//			calendar1.setTime(debtorRepaymentDate);
//			calendar1.add(Calendar.DAY_OF_MONTH, loan.getDebtorExtentedRepaymentDays());
//			creditrepayplan.setDebtorExtentedRepaymentDate(calendar1.getTime());
			creditrepayplan.setWorkflow(creditrepayplanWorkflowRepository.findOne(1));
			creditrepayplanRepository.save(creditrepayplan);
		}
	}

	public List<Creditrepayplan> insertPlayPlanByOrdercdd(Loan loan) {
		List<Creditrepayplan> list = new ArrayList<>();
		int periodNumber = loan.getPeriodNumber();// 期数
		Ordercdd ordercdd = loan.getOrdercdd();
		Creditcard creditcard = loan.getCreditcard();

		Loan.PeriodCode periodCode = loan.getPeriodCode();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();

		User debtorUser = loan.getDebtorUser();

		BigDecimal principal = new BigDecimal(0);
		for (int i = 0; i < periodNumber; i++) {
			Creditrepayplan creditrepayplan = new Creditrepayplan();
			creditrepayplan.setLoanSn(loan.getLoanSn());//
			creditrepayplan.setOrderSn(ordercdd.getCddSn());
			creditrepayplan.setPlanSn(dateFormat.format(now) + debtorUser.getId() + "-" + (i + 1));
			creditrepayplan.setCreditcard(creditcard);
			creditrepayplan.setLoan(loan);

			if (periodNumber - i == 1) {
				principal = loan.getPrincipal();// 最后一期
			}
			creditrepayplan.setPrincipal(principal);// 本金
			// 罚息关联在loan

			switch (periodCode) {
			case MONTH:
				calendar.add(Calendar.MONTH, 1);
				break;
			default:
				throw new FailReturnObject(1534, "分期应该是月份", ReturnObject.Level.ERROR);
			}
			Date debtorRepaymentDate = calendar.getTime();
			creditrepayplan.setDebtorRepaymentDate(debtorRepaymentDate);// 还款日期,因为在执行放款操作才执行本段代码，项目经理说就是当前日期+一个月

			creditrepayplan.setInterest(loan.getPrincipal()
					.multiply(loan.getDebtorInterest().divide(BigDecimal.valueOf(12), 2, BigDecimal.ROUND_UP)));

			creditrepayplan.setFees(creditrepayplan.getPrincipal().add(creditrepayplan.getInterest()));// 总和

			String planState = "ACCOUNT";// 但出账
			if (i == 0) {// 第一期是已出账
				planState = "ACCOUNTED";
			}
			creditrepayplan.setState(creditrepayplanStateRepository.findByStateCode(planState));// 待出账

			creditrepayplan.setServiceUser(loan.getServiceUser());

			creditrepayplan.setLeftAmount(creditrepayplan.getFees());// 等于总和
			creditrepayplan.setDebtorMobile(ordercdd.getApplyMobile());
			creditrepayplan.setDebtorName(ordercdd.getRealName());

			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(debtorRepaymentDate);
			calendar1.add(Calendar.DAY_OF_MONTH, loan.getDebtorExtentedRepaymentDays());
			creditrepayplan.setDebtorExtentedRepaymentDate(calendar1.getTime());

			creditrepayplan.setWorkflow(creditrepayplanWorkflowRepository.findOne(1));

			creditrepayplanRepository.save(creditrepayplan);
		}

		return list;
	}
}
