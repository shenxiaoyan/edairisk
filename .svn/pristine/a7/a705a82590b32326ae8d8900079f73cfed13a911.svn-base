package com.liyang.service;

import static org.hamcrest.CoreMatchers.nullValue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import com.liyang.domain.ordercdd.Ordercdd;
import com.liyang.domain.ordercdd.OrdercddRepository;
import com.liyang.domain.ordermdbt.Ordermdbt;
import com.liyang.domain.ordermdbt.OrdermdbtRepository;
import com.liyang.domain.orderrzzl.Orderrzzl;
import com.liyang.domain.orderrzzl.OrderrzzlRepository;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpa.query.expression.QueryExpSpecificationBuilder;
import com.jpa.query.expression.generic.GenericQueryExpSpecification;
import com.liyang.domain.bank.BankRepository;
import com.liyang.domain.base.AbstractAuditorAct.ActGroup;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.creditcard.Creditcard;
import com.liyang.domain.creditcard.CreditcardAct;
import com.liyang.domain.creditcard.CreditcardActRepository;
import com.liyang.domain.creditcard.CreditcardFile;
import com.liyang.domain.creditcard.CreditcardLog;
import com.liyang.domain.creditcard.CreditcardLogRepository;
import com.liyang.domain.creditcard.CreditcardRepository;
import com.liyang.domain.creditcard.CreditcardState;
import com.liyang.domain.creditcard.CreditcardStateRepository;
import com.liyang.domain.creditcard.CreditcardWorkflow;
import com.liyang.domain.creditrepayment.Creditrepayment;
import com.liyang.domain.creditrepayment.CreditrepaymentRepository;
import com.liyang.domain.loan.Loan;
import com.liyang.domain.loan.LoanRepository;
import com.liyang.domain.loan.Loanoverdue;
import com.liyang.domain.loan.LoanoverdueRepository;
import com.liyang.domain.orderwdsjsh.Orderwdsjsh;
import com.liyang.domain.orderwdsjsh.OrderwdsjshRepository;
import com.liyang.domain.orderwdxyd.Orderwdxyd;
import com.liyang.domain.orderwdxyd.OrderwdxydRepository;
import com.liyang.domain.person.Person;
import com.liyang.domain.person.PersonRepository;
import com.liyang.domain.person.PersonStateRepository;
import com.liyang.dto.LoaningInfo;
import com.liyang.dto.PaymentingInfo;
import com.liyang.util.DateUtil;
import com.liyang.util.FailReturnObject;
import com.liyang.util.ReturnObject.Level;

import javassist.compiler.ast.NewExpr;

@Service
@Order(10043)
public class CreditcardService extends
		AbstractWorkflowService<Creditcard, CreditcardWorkflow, CreditcardAct, CreditcardState, CreditcardLog, CreditcardFile> {

	@Value("${spring.wlqz.wechat.OPEN_ACC_SUCCESS}")
	private String OPEN_ACC_SUCCESS;

	@Autowired
	CreditcardActRepository creditcardActRepository;

	@Autowired
	CreditcardLogRepository creditcardLogRepository;

	@Autowired
	CreditcardRepository creditcardRepository;

	@Autowired
	CreditcardStateRepository creditcardStateRepository;

	@Autowired
	WlqzWechatPubService wechatPubService;

	@Autowired
	PersonRepository personRepository;

	@Autowired
	BankRepository bankRepository;

	@Autowired
	PersonStateRepository personStateRepository;

	@Autowired
	OrderwdsjshRepository orderwdsjshRepository;

	@Autowired
	OrderwdsjshService orderwdsjshService;
	@Autowired
	OrdercddRepository ordercddRepository;
	@Autowired
	OrderrzzlRepository orderrzzlRepository;
	@Autowired
	OrdermdbtRepository ordermdbtRepository;
	@Autowired
	OrderwdxydRepository orderwdxydRepository;
	@Autowired
	LoanRepository loanRepository;
	@Autowired
	CreditrepaymentRepository creditrepaymentRepository;
	@Autowired
	LoanoverdueRepository loanoverdueRepository;

	@Override
	@PostConstruct
	public void sqlInit() {

		long findAll = creditcardActRepository.count();
		if (findAll == 0) {
			CreditcardAct save1 = creditcardActRepository.save(new CreditcardAct("创建", "create", 10, ActGroup.UPDATE));
			CreditcardAct save2 = creditcardActRepository.save(new CreditcardAct("读取", "read", 20, ActGroup.READ));
			CreditcardAct save3 = creditcardActRepository.save(new CreditcardAct("更新", "update", 30, ActGroup.UPDATE));
			CreditcardAct save4 = creditcardActRepository.save(new CreditcardAct("删除", "delete", 40, ActGroup.UPDATE));
			CreditcardAct save5 = creditcardActRepository
					.save(new CreditcardAct("自己的列表", "listOwn", 50, ActGroup.READ));
			CreditcardAct save6 = creditcardActRepository
					.save(new CreditcardAct("部门的列表", "listOwnDeparment", 60, ActGroup.READ));
			CreditcardAct save7 = creditcardActRepository
					.save(new CreditcardAct("部门和下属部门的列表", "listOwnDeparmentAndChildren", 70, ActGroup.READ));
			CreditcardAct save8 = creditcardActRepository
					.save(new CreditcardAct("通知其他人", "noticeOther", 80, ActGroup.NOTICE));
			CreditcardAct save9 = creditcardActRepository
					.save(new CreditcardAct("通知给操作者", "noticeActUser", 90, ActGroup.NOTICE));
			CreditcardAct save10 = creditcardActRepository
					.save(new CreditcardAct("通知给展示人", "noticeShowUser", 100, ActGroup.NOTICE));

			creditcardStateRepository.save(new CreditcardState("已创建", 0, "CREATED"));
			CreditcardState CreditcardState = new CreditcardState("有效", 100, "ENABLED");
			CreditcardState.setActs(Arrays.asList(save1, save2, save3, save4, save5, save6, save7).stream()
					.collect(Collectors.toSet()));
			creditcardStateRepository.save(CreditcardState);
			creditcardStateRepository.save(new CreditcardState("无效", 200, "DISABLED"));
			creditcardStateRepository.save(new CreditcardState("已删除", 300, "DELETED"));

		}

	}

	@Override
	public LogRepository<CreditcardLog> getLogRepository() {
		// TODO Auto-generated method stub
		return creditcardLogRepository;
	}

	@Override
	public AuditorEntityRepository<Creditcard> getAuditorEntityRepository() {
		// TODO Auto-generated method stub
		return creditcardRepository;
	}

	@Override
	@PostConstruct
	public void injectLogRepository() {
		new Creditcard().setLogRepository(creditcardLogRepository);

	}

	@Override
	public CreditcardLog getLogInstance() {
		// TODO Auto-generated method stub
		return new CreditcardLog();
	}

	@Override
	public ActRepository<CreditcardAct> getActRepository() {
		// TODO Auto-generated method stub
		return creditcardActRepository;
	}

	@Override
	@PostConstruct
	public void injectEntityService() {
		new Creditcard().setService(this);

	}

	@Override
	public CreditcardFile getFileLogInstance() {
		// TODO Auto-generated method stub
		return new CreditcardFile();
	}

	/**
	 * 开户 orderwdsjsh CREATED/DENIED-->ENABLED person CREATED--->ENABLED
	 * 
	 * @param creditcard
	 */
	@Transactional
	public void save(Creditcard creditcard, String beidouMessge) {
		// Person
		// person=personRepository.getByTelephoneAndStateCode(creditcard.getCreditcardIdentity()+"","ENABLED");
		String identity = orderwdsjshRepository.findByApplyMobile(creditcard.getCreditcardIdentity())
				.getApplyIdentityNo();
		Person person = personRepository.findByIdentity(identity);
		person.setState(personStateRepository.findByStateCode("ENABLED"));
		orderwdsjshService.adoptApply(creditcard.getCreditcardIdentity() + "");
		creditcard.setState(creditcardStateRepository.findByStateCode("ENABLED"));
		Orderwdsjsh orderwdsjsh = orderwdsjshRepository
				.getByTelephoneAndStatCode(creditcard.getCreditcardIdentity() + "", "ENABLED");

		creditcardRepository.save(creditcard);
		if (orderwdsjsh != null) {
			orderwdsjsh.setDebtorCreditcard(creditcard);
		}

		wechatPubService.pushOpenAccMessage(orderwdsjsh.getCreatedBy(), "申请通过", person.getName(), person.getTelephone(),
				OPEN_ACC_SUCCESS, beidouMessge);
	}

	@Transactional(readOnly = true)
	public Creditcard findByCreditcardIdentity(String identity) {
		return creditcardRepository.findByCreditcardIdentity(identity);
	}

	/**
	 * 开户 orderwdsjsh CREATED/DENIED-->ENABLED person CREATED--->ENABLED
	 * 
	 * @param creditcard
	 */
	@Transactional // 从车抵贷来的保存
	public void saveFromOrdercdd(Creditcard creditcard, String message) {
		Person person = personRepository.findByIdentity(creditcard.getCreditcardIdentity());
		creditcard.setState(creditcardStateRepository.findByStateCode("ENABLED"));
		Ordercdd ordercdd = ordercddRepository.getByIdentityNoAndStatCode(creditcard.getCreditcardIdentity(), "AUDIT");
		creditcardRepository.save(creditcard);
		if (ordercdd != null) {
			ordercdd.setDebtorCreditcard(creditcard);
		}
		wechatPubService.pushOpenAccMessage(ordercdd.getUser(), "申请通过", person.getName(), person.getTelephone(), null,
				message);
	}

	/**
	 * 客户信息统计搜索
	 * 
	 * @param productid
	 * @param name
	 * @param telephone
	 * @param identity
	 * @param pageable
	 * @return
	 */
	public List<Creditcard> getCreditcardInfo(Integer productid, String name, String telephone, String identity) {
		GenericQueryExpSpecification<Creditcard> queryExpression = new GenericQueryExpSpecification<Creditcard>();
		queryExpression.add(QueryExpSpecificationBuilder.eq("person.name", name, true));
		queryExpression.add(QueryExpSpecificationBuilder.eq("person.telephone", telephone, true));
		queryExpression.add(QueryExpSpecificationBuilder.eq("product.id", productid, true));
		queryExpression.add(QueryExpSpecificationBuilder.eq("person.identity", identity, true));
		List<Creditcard> list = creditcardRepository.findAll(queryExpression);
		return list;

	}

	/**
	 * 通过法人和产品Id查询对应的产品
	 * 
	 * @param productId
	 * @param personId
	 * @return
	 */
	public Object getProductByPersonAndPID(Integer productId, Integer personId) {
		Object object = null;
		switch (productId) {
		case 1:
			object = orderwdsjshRepository.findByPersonId(personId);
			break;
		case 2:
			object = orderwdxydRepository.findByPersonId(personId);
			break;
		case 3:
			object = orderrzzlRepository.findByPersonId(personId);
			break;
		case 4:
			object = ordermdbtRepository.findByPersonId(personId);
			break;
		default:
			break;
		}
		return object;
	}

	/**
	 * 在借客户未还清借款详情 目前只有sjsh、mdbts
	 * 
	 * @param creditcardId
	 */
	public List<LoaningInfo> getLoanByCreditcard(Creditcard creditcard) {
		List<Loan> loans = loanRepository.findByCreditcardId(creditcard.getId());
		List<LoaningInfo> loaningInfos = new ArrayList<LoaningInfo>();
		switch (creditcard.getProduct().getId()) {
		case 1:

			for (Loan loan : loans) {
				List<Creditrepayment> creditrepayments = creditrepaymentRepository
						.findByLoanSnLike("%" + getValueFromJson(loan.getInformation(), "id").toString() + "%");
				if (!creditcard.getCreditBalance().equals(creditcard.getCreditGrant()) && creditrepayments != null
						&& creditrepayments.size() > 0) {
					Creditrepayment creditrepayment = creditrepayments.get(creditrepayments.size() - 1);
					if ((double) getValueFromJson(creditrepayment.getInformation(), "remainAmount") <= 0) {
						// loans.remove(loan);
						continue;
					}
				}
				LoaningInfo loaningInfo = new LoaningInfo();
				loaningInfo.setLoanAmount(
						new BigDecimal(getValueFromJson(loan.getInformation(), "loanAmount").toString()));
				loaningInfo.setLoanDate(getValueFromJson(loan.getInformation(), "loanStartDate").toString());
				loaningInfo.setLoanLimitDate(getValueFromJson(loan.getInformation(), "loanEndDate").toString());
				loaningInfo.setPaymentingInfos(paymentingSJSHInfo(creditrepayments, loan));
				loaningInfos.add(loaningInfo);
			}

			break;
		case 4:
			if (creditcard.getCreditBalance().equals(creditcard.getCreditGrant())) {
				for (Loan loan : loans) {
					if (!loan.getState().getStateCode().equals("PAYOF")) {
						continue;
					}
					LoaningInfo loaningInfo = new LoaningInfo();
					List<Creditrepayment> creditrepayments = creditrepaymentRepository
							.getCreditRepaymentsByLoan(loan.getId());
					List<Loanoverdue> loanoverdues = loanoverdueRepository.findByLoanId(loan.getId());
					// loaningInfo.setRemainAmount(loan.getRemainAmount());
					loaningInfo.setPaymentingInfos(paymentingMDBTInfo(creditrepayments, loan));
					loaningInfo.setLoanoverdues(loanoverdues);
					loaningInfo.setLoanAmount(loan.getPrincipal());
					loaningInfo.setLoanDate(DateUtil.getStrByDate(loan.getUseDate(), "yyyy-MM-dd HH:mm:ss"));
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(loan.getUseDate());
					calendar.add(Calendar.DATE, 90);
					loaningInfo.setLoanLimitDate(DateUtil.getStrByDate(calendar.getTime(), "yyyy-MM-dd HH:mm:ss"));
					loaningInfos.add(loaningInfo);
				}

			} else {
				for (Loan loan : loans) {
					if (!loan.getState().getStateCode().equals("ENABLED")) {
						// loans.remove(loan);
						continue;
					}
					LoaningInfo loaningInfo = new LoaningInfo();
					List<Creditrepayment> creditrepayments = creditrepaymentRepository
							.getCreditRepaymentsByLoan(loan.getId());
					List<Loanoverdue> loanoverdues = loanoverdueRepository.findByLoanId(loan.getId());
					loaningInfo.setRemainAmount(loan.getRemainAmount());
					loaningInfo.setPaymentingInfos(paymentingMDBTInfo(creditrepayments, loan));
					loaningInfo.setLoanoverdues(loanoverdues);
					loaningInfo.setLoanAmount(loan.getPrincipal());
					loaningInfo.setLoanDate(DateUtil.getStrByDate(loan.getUseDate(), "yyyy-MM-dd HH:mm:ss"));
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(loan.getUseDate());
					calendar.add(Calendar.DATE, 90);
					loaningInfo.setLoanLimitDate(DateUtil.getStrByDate(calendar.getTime(), "yyyy-MM-dd HH:mm:ss"));
					loaningInfos.add(loaningInfo);
				}
			}
			break;
		default:
			break;
		}
		return loaningInfos;
	}

	/**
	 * MDBT
	 * 
	 * @param list
	 * @param loan
	 * @return
	 */
	public List<PaymentingInfo> paymentingMDBTInfo(List<Creditrepayment> list, Loan loan) {
		List<PaymentingInfo> paymentingInfos = new ArrayList<PaymentingInfo>();
		BigDecimal remainAmount = loan.getPrincipal();
		for (Creditrepayment creditrepayment : list) {
			if (creditrepayment.getState().getId() != 2) {
				continue;
			}
			PaymentingInfo paymentingInfo = new PaymentingInfo();
			paymentingInfo.setPayDate(
					DateUtil.getStrByDate(creditrepayment.getDebtorActualRepaymentDate(), "yyyy-MM-dd HH:mm:ss"));
			paymentingInfo.setRealPayAmount(creditrepayment.getPayAmount());
			paymentingInfo.setPrincipal(creditrepayment.getPrincipal());
			paymentingInfo.setInterinest(creditrepayment.getInterest());
			remainAmount = remainAmount.subtract(creditrepayment.getPrincipal());
			paymentingInfo.setPaymantRemain(remainAmount);
			paymentingInfos.add(paymentingInfo);
		}
		return paymentingInfos;
	}

	/**
	 * sjsh
	 * 
	 * @param list
	 * @param loan
	 * @return
	 */
	public List<PaymentingInfo> paymentingSJSHInfo(List<Creditrepayment> list, Loan loan) {
		List<PaymentingInfo> paymentingInfos = new ArrayList<PaymentingInfo>();
		BigDecimal paymentRemain = new BigDecimal(getValueFromJson(loan.getInformation(), "loanAmount").toString());
		for (Creditrepayment creditrepayment : list) {
			PaymentingInfo paymentingInfo = new PaymentingInfo();
			paymentingInfo.setPayDate(
					getValueFromJson(creditrepayment.getInformation(), "payDate").toString().substring(0, 8));
			List list2 = (List) getValueFromJson(creditrepayment.getInformation(), "payPlans");
			Map map = (Map) list2.get(0);
			paymentingInfo.setPrincipal(new BigDecimal(map.get("planCorpus").toString()));
			paymentingInfo.setInterinest(new BigDecimal(map.get("planInterest").toString()));
			paymentingInfo.setPunishInterinest(new BigDecimal(map.get("penatly").toString()));
			paymentRemain = paymentRemain.subtract(paymentingInfo.getPrincipal());
			paymentingInfo.setPaymantRemain(paymentRemain);
			paymentingInfo.setOverdue((Integer) map.get("isOverdue"));
			paymentingInfo.setPayof(1);
			paymentingInfos.add(paymentingInfo);
		}
		return paymentingInfos;
	}

	public Object getValueFromJson(String originalData, String key) {
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> map = null;
		try {
			map = objectMapper.readValue(originalData, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new FailReturnObject(6659, e.getMessage(), Level.FATAL);
		}
		return map.get(key);
	}
	
	
	public List<Orderwdsjsh> getSjshNoPass(String name, String telephone, String identity) {
		GenericQueryExpSpecification<Orderwdsjsh> queryExpression = new GenericQueryExpSpecification<Orderwdsjsh>();
		queryExpression.add(QueryExpSpecificationBuilder.eq("realName", name, true));
		queryExpression.add(QueryExpSpecificationBuilder.eq("applyMobile", telephone, true));
		queryExpression.add(QueryExpSpecificationBuilder.eq("applyIdentityNo", identity, true));
		queryExpression.add(QueryExpSpecificationBuilder.eq("state.stateCode", "DENIED", true));
		List<Orderwdsjsh> list = orderwdsjshRepository.findAll(queryExpression);
		return list;

	}
	
	public List<Ordermdbt> getMdbtNoPass(String name, String telephone, String identity) {
		GenericQueryExpSpecification<Ordermdbt> queryExpression = new GenericQueryExpSpecification<Ordermdbt>();
		queryExpression.add(QueryExpSpecificationBuilder.eq("realName", name, true));
		queryExpression.add(QueryExpSpecificationBuilder.eq("applyMobile", telephone, true));
		queryExpression.add(QueryExpSpecificationBuilder.eq("applyIdentityNo", identity, true));
		queryExpression.add(QueryExpSpecificationBuilder.eq("state.stateCode", "DENIED", true));
		List<Ordermdbt> list = ordermdbtRepository.findAll(queryExpression);
		return list;

	}
	
	public List<Orderrzzl> getRzzlNoPass(String name, String telephone, String identity) {
		GenericQueryExpSpecification<Orderrzzl> queryExpression = new GenericQueryExpSpecification<Orderrzzl>();
		queryExpression.add(QueryExpSpecificationBuilder.eq("realName", name, true));
		queryExpression.add(QueryExpSpecificationBuilder.eq("applyMobile", telephone, true));
		queryExpression.add(QueryExpSpecificationBuilder.eq("applyIdentityNo", identity, true));
		queryExpression.add(QueryExpSpecificationBuilder.eq("state.stateCode", "DENIED", true));
		List<Orderrzzl> list = orderrzzlRepository.findAll(queryExpression);
		return list;

	}
	
	public List<Orderwdxyd> getWdxydNoPass(String name, String telephone, String identity) {
		GenericQueryExpSpecification<Orderwdxyd> queryExpression = new GenericQueryExpSpecification<Orderwdxyd>();
		queryExpression.add(QueryExpSpecificationBuilder.eq("realName", name, true));
		queryExpression.add(QueryExpSpecificationBuilder.eq("applyMobile", telephone, true));
		queryExpression.add(QueryExpSpecificationBuilder.eq("applyIdentityNo", identity, true));
		queryExpression.add(QueryExpSpecificationBuilder.eq("state.stateCode", "DENIED", true));
		List<Orderwdxyd> list = orderwdxydRepository.findAll(queryExpression);
		return list;

	}
	
}
