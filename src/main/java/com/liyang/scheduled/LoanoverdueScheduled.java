package com.liyang.scheduled;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.liyang.domain.creditrepayplan.Creditrepayplan;
import com.liyang.domain.creditrepayplan.CreditrepayplanRepository;
import com.liyang.domain.loan.Loan;
import com.liyang.domain.loan.LoanRepository;
import com.liyang.domain.loan.Loanoverdue;
import com.liyang.domain.loan.LoanoverdueRepository;

/**
 * 判断loan是否逾期 并计算逾期罚金
 * 
 * @author Administrator
 *
 */
@Component
public class LoanoverdueScheduled {

	// 还款计划表状态
	private static final String STATECODE_ACCOUNT = "ACCOUNT";// 待出账
	private static final String STATECODE_ACCOUNTED = "ACCOUNTED";// 已出账

	@Resource
	private CreditrepayplanRepository planRepository;
	@Resource
	private LoanoverdueRepository loanoverdueRepository;
	@Resource
	private LoanRepository loanRepository;
	
	/**
	 * 计算已经激活的计划的逾期
	 */
	//@Scheduled(cron = "0 0 1 * * ?")
	//@Scheduled(fixedRate = 1000*60)
	public void scheduled() {
		//获取已出账、剩余金额不为0的计划
		List<Creditrepayplan> list = planRepository.getByStatCodeAndLeftAmountGreaterThan(STATECODE_ACCOUNTED, new BigDecimal("0"));
		if (list==null) {
			return;
		}
		//循环所有计划，只判断并修改计划是否逾期，
		//如果逾期，将逾期信息加入到逾期信息表，不负责计算
		for (Creditrepayplan creditrepayplan : list) {
			if (creditrepayplan == null) {
				continue;
			}
			checkAndSaveIsOverdue(creditrepayplan);
		}
		
		//逾期信息表所有有效的逾期记录，获取的是最新的逾期记录，刚刚逾期的也已经插入进去了
		Set<Loanoverdue> loanoverdues = loanoverdueRepository.findByStatus(0);//有效的逾期信息
		if (loanoverdues == null) {
			return;
		}
		for (Loanoverdue loanoverdue : loanoverdues) {
			handleLoanoverdue(loanoverdue);
		}
	}
	
	/**验证并保存还款计划是否逾期
	 * @param plan
	 */
	@Transactional
	private void checkAndSaveIsOverdue(Creditrepayplan plan) {
		if (plan.getIsOverdue() == 1) {//已经逾期不再修改
			return;
		}
		Date currentDate = new Date();
		Date planDate = plan.getDebtorRepaymentDate();
		if (currentDate.after(planDate)) {//已经逾期
			plan.setIsOverdue(1);
			planRepository.save(plan);
			//标记loan已经逾期
			Loan loan = plan.getLoan();
			loan.setOverdue(1);
			loanRepository.save(loan);
			//正常的计划已经逾期，将自身的金额加入到逾期信息表
			Loanoverdue loanoverdue = loanoverdueRepository.findEnable(plan.getLoan().getId());
			//如果存在逾期信息，只加入计划剩余金额到逾期信息表
			if (loanoverdue != null) {
				loanoverdue.setHistoryAmount(plan.getLeftAmount().add(loanoverdue.getHistoryAmount()));
				loanoverdue.setOverdueAmount(plan.getLeftAmount().add(loanoverdue.getOverdueAmount()));
				//不计算逾期罚息
				loanoverdueRepository.save(loanoverdue);
			}else {//如果不存在逾期信息，创建一条
				Loanoverdue insert = new Loanoverdue();
				insert.setCreatedAt(new Date());
				insert.setHistoryAmount(plan.getLeftAmount());
				insert.setOverdueAmount(plan.getLeftAmount());
				insert.setLoan(plan.getLoan());
				insert.setStatus(0);//0：逾期中；1：已结清
				//insert.setOverdueDays((int) (currentDate.getTime() - planDate.getTime()) / (1000*3600*24));   这样算会导致用户百分百逾期
				insert.setOverdueDays(-(loan.getDebtorExtentedRepaymentDays()));   //宽限日期     开始为负数
				insert.setPenalSum(new BigDecimal(0));
				Loanoverdue save = loanoverdueRepository.save(insert);
				System.out.println(save.getOverdueDays());
			}
		}
	}
	
	/**计算逾期信息表中逾期罚金
	 * @param loanoverdue
	 */
	@Transactional
	private void handleLoanoverdue(Loanoverdue loanoverdue) {
		Integer overdueDays = loanoverdue.getOverdueDays();// 逾期天数
		int days = overdueDays == null ? 0 : overdueDays.intValue();
		days += 1;//逾期天数加一
		Loan loan = loanoverdue.getLoan();
		BigDecimal penalSum = loanoverdue.getPenalSum();//罚金
		penalSum = penalSum == null?new BigDecimal("0"):penalSum;
		/**计算罚金*/
		//贷款本金
		BigDecimal principal = loan.getPrincipal();
		if(days>0){
			//一天的罚金
			BigDecimal addValue = principal.multiply(new BigDecimal(0.0005));
			//新罚金
			BigDecimal newPenalSum = addValue.add(penalSum);
			loanoverdue.setPenalSum(newPenalSum);
		}
		loanoverdue.setOverdueDays(days);
		loanoverdueRepository.save(loanoverdue);
	}
}
