package com.liyang.scheduled;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.liyang.domain.creditrepayment.Creditrepayment;
import com.liyang.domain.creditrepayplan.Creditrepayplan;
import com.liyang.domain.creditrepayplan.CreditrepayplanRepository;
import com.liyang.domain.creditrepayplan.CreditrepayplanState;
import com.liyang.domain.creditrepayplan.CreditrepayplanStateRepository;
import com.liyang.domain.loan.Loanoverdue;
import com.liyang.domain.loan.LoanoverdueRepository;
import com.liyang.service.WlqzWechatPubService;
import com.liyang.service.XinGePushService;
import com.liyang.util.DateUtil;
import com.liyang.util.ProductUtil;

/**
 * 信用卡还款计划定时计算
 */
@Component
public class CreditrepayplanScheduled {
	// 还款计划表状态
	private static final String STATECODE_ACCOUNT = "ACCOUNT";// 待出账
	private static final String STATECODE_ACCOUNTED = "ACCOUNTED";// 已出账

	@Resource
	private CreditrepayplanRepository planRepository;
	@Resource
	private CreditrepayplanStateRepository planStateRepository;
	@Resource
	private XinGePushService xinGePushService;
	@Resource
	private LoanoverdueRepository loanoverdueRepository;
	@Resource
	private WlqzWechatPubService wlqzWechatPubService;

	/**
	 * 定时计算还款计划，推送逾期消息
	 */
	//@Scheduled(cron = "0 0 12 * * ?")
	//@Scheduled(fixedRate = 1000*60*2)
	public void pushOverdueMessage() {
		List<Loanoverdue> loanoverdues = loanoverdueRepository.findByStatus2(0);
		if (loanoverdues == null) {
			return;
		}
		for (Loanoverdue loanoverdue : loanoverdues) {
			if (loanoverdue.getPenalSum().compareTo(BigDecimal.valueOf(0)) > 0) {
				String content = "尊敬的用户您好，您单号为" + loanoverdue.getLoan().getOrderNo() + "的借款已逾期" + "\n逾期天数："
						+ loanoverdue.getOverdueDays() + "\n逾期金额：" + loanoverdue.getOverdueAmount().setScale(2) + "\n逾期罚金："
						+ loanoverdue.getPenalSum().setScale(2) + "\n请及时进行还款操作";
				xinGePushService.pushAppMessage(loanoverdue.getLoan().getCreatedBy(),
						loanoverdue.getLoan().getCreditcard().getProduct(), "逾期提醒", content, 2);
			} else {
				String content = "尊敬的用户您好，您单号为" + loanoverdue.getLoan().getOrderNo() + "的借款即将逾期，请尽快进行还款操作，届时将产生逾期罚金";
				xinGePushService.pushAppMessage(loanoverdue.getLoan().getCreatedBy(),
						loanoverdue.getLoan().getCreditcard().getProduct(), "逾期提醒", content, 2);
			}

		}

	}

	/**
	 * 定时计算还款计划日期，提前推送还款提醒消息
	 */
	//@Scheduled(cron = "0 0 12 * * ?")
	//@Scheduled(fixedRate = 1000*60*2)
	public void pushAppMessage() {

		List<Creditrepayplan> creditrepayplans = planRepository.getByStatCode(STATECODE_ACCOUNTED);
		if (creditrepayplans == null) {
			return;
		}
		for (Creditrepayplan creditrepayplan : creditrepayplans) {
			if (creditrepayplan.getTerm() == 3) {
				Date payDate = creditrepayplan.getDebtorRepaymentDate();
				Date date = new Date();
				if (differentDaysByMillisecond(date, payDate) == 15) {
					String content = "尊敬的用户您好，您于"
							+ DateUtil.getStrByDate(creditrepayplan.getDebtorExtentedRepaymentDate(),
									"yyyy-MM-dd HH:mm:ss")
							+ "需还款本金和利息共"
							+ creditrepayplan.getPrincipal().add(creditrepayplan.getInterest()).setScale(2);
					xinGePushService.pushAppMessage(creditrepayplan.getLoan().getCreatedBy(),
							creditrepayplan.getCreditcard().getProduct(), "还款提醒", content, 2);
					wlqzWechatPubService.pushRepaymentRemindMessage(creditrepayplan.getLoan().getCreatedBy(),
							DateUtil.getStrByDate(creditrepayplan.getDebtorRepaymentDate(), "yyyy-MM-dd HH:mm:ss"),
							creditrepayplan.getLeftAmount().setScale(2).toString(),
							creditrepayplan.getLoan().getOrderNo());
				}
			} else {
				Date payDate = creditrepayplan.getDebtorRepaymentDate();
				Date date = new Date();
				if (differentDaysByMillisecond(date, payDate) == 5) {
					String content = "尊敬的用户您好，您于" + DateUtil
							.getStrByDate(creditrepayplan.getDebtorExtentedRepaymentDate(), "yyyy-MM-dd HH:mm:ss")
							+ "需还款利息共" + creditrepayplan.getInterest().setScale(2);
					xinGePushService.pushAppMessage(creditrepayplan.getLoan().getCreatedBy(),
							creditrepayplan.getCreditcard().getProduct(), "还款提醒", content, 2);
					wlqzWechatPubService.pushRepaymentRemindMessage(creditrepayplan.getLoan().getCreatedBy(),
							DateUtil.getStrByDate(creditrepayplan.getDebtorRepaymentDate(), "yyyy-MM-dd HH:mm:ss"),
							creditrepayplan.getLeftAmount().setScale(2).toString(),
							creditrepayplan.getLoan().getOrderNo());
				}
			}
		}

	}

	/**
	 * 定时计算还款计划日期，激活还款计划
	 */
	@Scheduled(cron = "0 0 0 * * ?")
	// @Scheduled(fixedRate = 1000*60*10)
	public void activePlan() {
		// 待出账计划
		List<Creditrepayplan> list = planRepository.getByStatCode(STATECODE_ACCOUNT);
		if (list == null) {
			return;
		}
		CreditrepayplanState accounted = planStateRepository.findByStateCode(STATECODE_ACCOUNTED);
		// 循环计算时间
		List<Creditrepayplan> changeList = new ArrayList<>();
		Date currentDate = new Date();
		Date planDate = null;

		// Calendar calendar = Calendar.getInstance();
		// calendar.add(Calendar.DATE, 30);
		// currentDate = calendar.getTime();

		for (Creditrepayplan creditrepayplan : list) {
			if (creditrepayplan == null) {
				continue;
			}
			planDate = creditrepayplan.getDebtorRepaymentDate();
			if (planDate.before(currentDate)) {// 如果借方正常还款日期已经过了，马上激活
				creditrepayplan.setState(accounted);
				changeList.add(creditrepayplan);
				continue;
			}
			long difTime = planDate.getTime() - currentDate.getTime();
			if (difTime < (1000 * 60 * 60 * 24 * 30L)) {// 如果计划日期和当前日期的差值小于30天，激活
				creditrepayplan.setState(accounted);
				changeList.add(creditrepayplan);
				continue;
			}
		}
		if (changeList.isEmpty()) {
			return;
		}
		planRepository.save(changeList);
	}

	public static int differentDaysByMillisecond(Date date1, Date date2) {
		int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
		return days;
	}
}
