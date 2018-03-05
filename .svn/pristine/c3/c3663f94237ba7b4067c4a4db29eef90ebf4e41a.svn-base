package com.liyang.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.print.attribute.standard.PresentationDirection;

import com.liyang.domain.person.Person;
import com.liyang.domain.person.PersonRepository;
import com.liyang.domain.product.ProductRepository;
import com.liyang.domain.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liyang.domain.appmessage.AppMessage;
import com.liyang.domain.appmessage.AppMessageRepository;
import com.liyang.domain.approveloan.ApproveLoan;
import com.liyang.domain.approveloan.ApproveLoanRepository;
import com.liyang.domain.approveloan.ApproveLoanStateRepository;
import com.liyang.domain.capitalproduct.Capitalproduct.RepaymentMethodCode;
import com.liyang.domain.creditcard.Creditcard;
import com.liyang.domain.creditrepayment.Creditrepayment;
import com.liyang.domain.creditrepayment.CreditrepaymentRepository;
import com.liyang.domain.creditrepayment.CreditrepaymentStateRepository;
import com.liyang.domain.department.DepartmentRepository;
import com.liyang.domain.loan.Loan;
import com.liyang.domain.loan.LoanStateRepository;
import com.liyang.domain.orderwdsjsh.Orderwdsjsh;
import com.liyang.domain.orderwdsjsh.OrderwdsjshRepository;
import com.liyang.service.CreditcardService;
import com.liyang.service.LoanService;
import com.liyang.service.OrderwdsjshService;
import com.liyang.service.WlqzWechatPubService;
import com.liyang.service.XinGePushService;
import com.liyang.util.Base64;
import com.liyang.util.CommonUtil;
import com.liyang.util.FailReturnObject;
import com.liyang.util.ReturnObject.Level;

import net.sf.json.JSONObject;

/**
 * 贝兜接口接收
 * 
 * @author win7
 *
 */
@Controller
public class BeidouController {

	@Value("${spring.wlqz.wechat.WE_MESSAGE_URL}")
	private String WE_MESSAGE_URL;
	@Value("${spring.wlqz.wechat.OPEN_ACC_APPLY}")
	private String OPEN_ACC_APPLY;
	@Value("${spring.wlqz.wechat.OPEN_ACC_SUCCESS}")
	private String OPEN_ACC_SUCCESS;

	private int i = 0;
	@Autowired
	private CreditcardService creditcardService;
	@Autowired
	private OrderwdsjshService orderwdsjshService;
	@Autowired
	private OrderwdsjshRepository orderwdsjshRepository;
	@Autowired
	private WlqzWechatPubService wechatPubService;
	@Autowired
	private OrderwdsjshRepository orderWdsjshRepository;
	@Autowired
	private LoanService loanService;
	@Autowired
	private CreditrepaymentRepository repaymentRepository;
	@Autowired
	private CreditrepaymentStateRepository repaymentStateRepository;
	@Autowired
	private LoanStateRepository loanStateRepository;
	@Autowired
	private XinGePushService xinGePushService;
	@Autowired
	private AppMessageRepository appMessageRepository;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private DepartmentRepository c;
	@Autowired
	ApproveLoanRepository approveLoanRepository;
	@Autowired
	ApproveLoanStateRepository approveLoanStateRepository;

	@RequestMapping(value = "/beidou", method = RequestMethod.POST)
	@ResponseBody
	public String beidouCallback(@RequestBody String json) {
		String str;
		try {
			str = URLDecoder.decode(json, "utf8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new FailReturnObject(1241, "json字符串格式错误", Level.ERROR);
		}
		// if (json == null) {
		// throw new FailReturnObject(998, "Body中json数据不存在",Level.ERROR);
		// }
		// 我用 18702621932 他也用这个手机号 那么产生的结果是两个人共一个账户
		String type = (String) getValueFromJson(str, "type");
		System.out.println(str);
		if (type.equals("1")) {
			String content;
			try {
				content = new String(Base64.decodeBase64((String) getValueFromJson(str, "content")));
			} catch (Exception e) {
				e.printStackTrace();
				throw new FailReturnObject(1524, "json字符串content格式错误", Level.ERROR);
			}
			String code = (String) getValueFromJson(str, "code");
			// 授信失败改变orderWdsjsh 状态
			if ("1".equals(code)) {
				System.out.println(content + ":" + str);
				Orderwdsjsh orderwdsjsh = orderWdsjshRepository
						.getByMobile((String) getValueFromJson(content, "mobile"));
				wechatPubService.pushOpenAccMessage(orderwdsjsh.getCreatedBy(), "申请失败", orderwdsjsh.getRealName(),
						(String) getValueFromJson(content, "mobile"), OPEN_ACC_APPLY,
						(String) getValueFromJson(str, "message"));

				String message = xinGePushService.getShouXinFailedMessage(content);
				xinGePushService.pushAppMessage(orderwdsjsh.getCreatedBy(), orderwdsjsh.getProduct(), "授信进度通知", message,
						2);

				orderwdsjshService.changeStateNotpass((String) getValueFromJson(content, "mobile"),
						(String) getValueFromJson(str, "message"));
				return "fail";
			}
			// 开户建立person 与 creditcard的关联

			orderwdsjshService.adoptApply((String) getValueFromJson(content, "mobile"));

			System.out.println(content);
			// 授信成功 一个手机号一个账号
			// Creditcard creditcard = creditcardService
			// .findByCreditcardIdentity((String) getValueFromJson(content,
			// "mobile"));
			// if (creditcard == null) {
			
			Creditcard creditcard = new Creditcard();
			creditcard.setSort(new Random().nextInt(1000));
			
			// joshua: to-do 要固化成贝兜随借随还的产品
			// creditcard.setOrdertype(orderTypeRepository.getByOrderCode(Ordertype_bak.OrdertypeCode.ORDERWDSJSH));
			creditcard.setCreditcardIdentity((String) getValueFromJson(content, "mobile"));// 贷款人手机号
			if (getValueFromJson(content, "amount") instanceof Double) {
				creditcard.setCreditGrant(new BigDecimal((Double) getValueFromJson(content, "amount")));// 额度
				creditcard.setCreditBalance(new BigDecimal((Double) getValueFromJson(content, "amount")));
			} else if (getValueFromJson(content, "amount") instanceof Integer) {
				creditcard.setCreditGrant(new BigDecimal((Integer) getValueFromJson(content, "amount")));// 额度
				creditcard.setCreditBalance(new BigDecimal((Integer) getValueFromJson(content, "amount")));
			}
			// creditcard.setPerson(personRepository.getByTelephoneAndStateCode(creditcard.getCreditcardIdentity(),"ENABLED"));
			// 不能通过手机号去查person、只能通过手机号查产品实体、从实体取得person
			creditcard
					.setPerson(orderwdsjshRepository.findByApplyMobile(creditcard.getCreditcardIdentity()).getPerson());
			creditcard.setProduct(productRepository.findFirstByLabel("网点随借随还"));// 沈卫说一定是
																				// "网点随借随还"
			creditcardService.save(creditcard, (String) getValueFromJson(str, "message"));

			// }
			Orderwdsjsh orderwdsjsh = orderWdsjshRepository.getByMobile((String) getValueFromJson(content, "mobile"));
			String message = xinGePushService.getShouXinSuccessMessage(content);
			xinGePushService.pushAppMessage(orderwdsjsh.getCreatedBy(), orderwdsjsh.getProduct(), "授信进度通知", message, 2);

			return "success";
			// 2借款结果通知，
		} else if (type.equals("2")) {

			String content;
			try {
				content = new String(Base64.decodeBase64((String) getValueFromJson(str, "content")));
			} catch (Exception e) {
				e.printStackTrace();
				throw new FailReturnObject(2514, "json字符串content格式错误", Level.ERROR);
			}
			String code = (String) getValueFromJson(str, "code");
			// 贷款失败
			if ("1".equals(code)) {
				Orderwdsjsh orderwdsjsh = orderWdsjshRepository
						.getByMobile((String) getValueFromJson(content, "mobile"));
				wechatPubService.pushLoanMessage(orderwdsjsh.getCreatedBy(), orderwdsjsh.getRealName(),
						getValueFromJson(content, "loanAmount") + "", (String) getValueFromJson(str, "message"),
						(String) getValueFromJson(content, "accountType"), OPEN_ACC_SUCCESS);
				System.out.println(content + ":" + str);
				return "fail";
			} else if ("2".equals(code)) {
				return "放款中";
			}
			Orderwdsjsh orderwdsjsh = orderWdsjshRepository.getByMobile((String) getValueFromJson(content, "mobile"));
			System.out.println("借款qqqqq:" + content);
			//白羊加
			if(orderwdsjsh != null){				
				//账号状态
				String accountStatus = orderwdsjsh.getPerson().getAccountStatus();
				if("ABNORMAL".equals(accountStatus)){ 
					Integer personId = orderwdsjsh.getPerson().getId();
					ApproveLoan approveLoan = approveLoanRepository.findByPersonAndStateCode(personId);
					if(approveLoan != null){
						approveLoan.setApproveLoanState(approveLoanStateRepository.findByStateCode("DISABLED"));
						approveLoanRepository.save(approveLoan);
					}
				}
			}
			
//			Creditcard creditcard = creditcardService
//					.findByCreditcardIdentity((String) getValueFromJson(content, "mobile"));
			Creditcard creditcard = orderwdsjsh.getDebtorCreditcard();
			Loan loan = new Loan();
			loan.setState(loanStateRepository.findByStateCode("ENABLED"));
			// 总信额-借款
			if (getValueFromJson(content, "loanAmount") instanceof Double) {
				creditcard.setCreditBalance(creditcard.getCreditBalance()
						.subtract(new BigDecimal((Double) getValueFromJson(content, "loanAmount"))));
				loan.setPrincipal(new BigDecimal((Double) getValueFromJson(content, "loanAmount")));// 实际借款
			} else if (getValueFromJson(content, "loanAmount") instanceof Integer) {
				creditcard.setCreditBalance(creditcard.getCreditBalance()
						.subtract(new BigDecimal((Integer) getValueFromJson(content, "loanAmount"))));
				loan.setPrincipal(new BigDecimal((Integer) getValueFromJson(content, "loanAmount")));// 实际借款
			}

			loan.setCreditcard(creditcard);

			// loan.setDebtorRepaymentDay(CommonUtil.NumToDate((String)
			// getValueFromJson(content, "loanEndDate")));
			loan.setRepaymentMethodCode(RepaymentMethodCode.LEND_REPAY_ON_DEMAND);
			loan.setInformation(content);
			loan.setDebtorPerson(creditcard.getPerson());
			loan.setCreditorLoanSn((String) getValueFromJson(content, "id"));
			loan.setCreatedByDepartment(c.findOne(6));
			loanService.save(loan, (String) getValueFromJson(str, "message"), getValueFromJson(content, "accountType"));
			return "index";
			// 3还款结果通知
		} else if (type.equals("3")) {
			// templatePost("http://localhost/rest/repaymentResults",str);
			String code = (String) getValueFromJson(str, "code");

			String content;
			try {
				content = new String(Base64.decodeBase64((String) getValueFromJson(str, "content")));
				System.out.println("借款qqqqq:" + content);
			} catch (Exception e) {
				e.printStackTrace();
				throw new FailReturnObject(63296, "json字符串content格式错误", Level.ERROR);
			}
//			Creditcard creditcard = creditcardService
//					.findByCreditcardIdentity((String) getValueFromJson(content, "mobile"));
			Orderwdsjsh orderwdsjsh = orderWdsjshRepository.getByMobile((String) getValueFromJson(content, "mobile"));
			Creditcard creditcard = orderwdsjsh.getDebtorCreditcard();
			Map map = JSONObject.fromObject(content);

			List<Object> list = (List<Object>) map.get("payPlans");

			int index = 0;
			if (list != null) {
				index = list.size() - 1;
			}

			Map toMap = JSONObject.fromObject(list.get(index).toString());
			// 支付失败
			if ("1".equals(code)) {
				wechatPubService.pushRepaymentMessage(orderwdsjsh.getCreatedBy(), orderwdsjsh.getRealName(), "",
						toMap.get("planCorpus") + "", "还款失败",
						CommonUtil.NumToDateString((String) getValueFromJson(content, "payDate")),
						(String) getValueFromJson(str, "message"), WE_MESSAGE_URL);
				String message = xinGePushService.getRepaymentFailedMessage(content);
				xinGePushService.pushAppMessage(orderwdsjsh.getCreatedBy(), orderwdsjsh.getProduct(), "还款进度通知", message,
						2);
				return "faile";
			}
			Creditrepayment creditrepayment = new Creditrepayment();
			creditrepayment.setCreditcard(creditcard);// 贷款账户
			creditrepayment
					.setDebtorActualRepaymentDate(CommonUtil.NumToDate((String) getValueFromJson(content, "payDate")));// 还款日期
			System.out.println(list.toString());
			creditrepayment.setPrincipal(new BigDecimal(Double.parseDouble(toMap.get("planCorpus") + "")));// 本金
			creditrepayment.setInterest(new BigDecimal(Double.parseDouble(toMap.get("planInterest") + "")));// 利息planInertest
			/*
			 * if (toMap.get("planCorpus") instanceof Double) {
			 * repayment.setPrincipal( new BigDecimal((Double)
			 * getValueFromJson(list.get(0).toString(), "planCorpus")));// 本金 }
			 * else { repayment.setPrincipal( new BigDecimal((Integer)
			 * getValueFromJson(list.get(0).toString(), "planCorpus")));// 本金 }
			 */
			creditrepayment.setIsOverdue(Integer.parseInt(toMap.get("isOverdue") + ""));/// 是否逾期
			creditrepayment.setPunishinterest(new BigDecimal(Double.parseDouble(toMap.get("penatly") + "")));// 罚息
			creditcard.setCreditBalance(creditcard.getCreditBalance()
					.add(new BigDecimal(Double.parseDouble(toMap.get("planCorpus") + ""))));
			creditrepayment.setInformation(content);
			creditrepayment.setState(repaymentStateRepository.findByStateCode("ENABLED"));
			creditrepayment.setCreatedByDepartment(c.findOne(6));
			repaymentRepository.save(creditrepayment);
			wechatPubService.pushRepaymentMessage(orderwdsjsh.getCreatedBy(), orderwdsjsh.getRealName(), "",
					toMap.get("planCorpus") + "", "还款成功",
					CommonUtil.NumToDateString((String) getValueFromJson(content, "payDate")),
					(String) getValueFromJson(str, "message"),
					// WE_MESSAGE_URL);
					OPEN_ACC_SUCCESS);
			String message = xinGePushService.getRepaymentSuccessMessage(content);
			xinGePushService.pushAppMessage(orderwdsjsh.getCreatedBy(), orderwdsjsh.getProduct(), "还款进度通知", message, 2);
			return "index";
		} else {
			// NOTPASS 背篼未通过状态 notPassAction 没有通过状态
			throw new FailReturnObject(999, "type接口类型不正确", Level.FATAL);
		}

	}

	private List<Object> jsonToList(String json) {
		ObjectMapper objectMapper = new ObjectMapper();
		List<Object> list = null;
		try {
			list = objectMapper.readValue(json, List.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new FailReturnObject(1529, e.getMessage(), Level.FATAL);
		}
		return list;
	}

	public static String replace(String str, String oldSubStr, String newSubStr) {
		StringBuffer sb = new StringBuffer();
		int i = 0;
		int j = 0;
		int len = oldSubStr.length();
		while ((i = str.indexOf(oldSubStr, j)) != -1) {
			sb.append(str.substring(j, i));
			sb.append(newSubStr);
			j = i + len;
		}
		sb.append(str.substring(j, str.length()));
		return sb.toString();
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

	public void templatePost(String url, String originalData) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/json;charset=UTF-8"));
		HttpEntity<String> entity = new HttpEntity<String>(originalData, headers);
		restTemplate.postForEntity(url, entity, String.class);
	}
}
