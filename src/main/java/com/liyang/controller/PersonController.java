package com.liyang.controller;

import static org.mockito.Matchers.contains;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.attribute.standard.PagesPerMinute;
import javax.websocket.server.PathParam;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.liyang.domain.creditcard.Creditcard;
import com.liyang.domain.creditcard.CreditcardRepository;
import com.liyang.domain.loan.Loan;
import com.liyang.domain.loan.LoanRepository;
import com.liyang.domain.ordermdbt.Ordermdbt;
import com.liyang.domain.ordermdbt.OrdermdbtRepository;
import com.liyang.domain.orderrzzl.Orderrzzl;
import com.liyang.domain.orderwdsjsh.Orderwdsjsh;
import com.liyang.domain.orderwdsjsh.OrderwdsjshRepository;
import com.liyang.domain.orderwdxyd.Orderwdxyd;
import com.liyang.domain.person.Person;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserRepository;
import com.liyang.dto.CreditcardInfo;
import com.liyang.dto.LoaningInfo;
import com.liyang.dto.NoPassPersonInfo;
import com.liyang.dto.PersonInfo;
import com.liyang.service.CreditcardService;
import com.liyang.service.PersonService;
import com.liyang.util.FailReturnObject;
import com.liyang.util.PageInfo;
import com.liyang.util.ResponseBody;
import com.liyang.util.ReturnObject;

@RestController
@RequestMapping("/person")
public class PersonController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PersonService PersonService;
	@Autowired
	private CreditcardService creditcardService;
	@Autowired
	private CreditcardRepository creditcardRepository;
	@Autowired
	private LoanRepository loanRepository;
	@Autowired
	OrderwdsjshRepository orderwdsjshRepository;
	@Autowired
	OrdermdbtRepository ordermdbtRepository;

	@GetMapping("/getAccountStatus")
	public ResponseBody getPersonAccountStatus(@PathParam("userid") Integer userid) {
		User user = userRepository.findByUserId(userid);
		if (user == null) {
			throw new FailReturnObject(1328, "无此用户", ReturnObject.Level.WARNING);
		}
		String accountStatus = user.getPerson().getAccountStatus();
		return new ResponseBody(accountStatus);
	}

	@GetMapping("/list")
	public ResponseBody getPersonList(String stateCode, String name, String identity, String accountStatus,
			String telephone, Pageable pageable) {

		Page<Person> personPage = PersonService.getPersonList(stateCode, name, identity, accountStatus, telephone,
				pageable);
		Integer number = personPage.getNumber();
		Integer size = personPage.getSize();
		long totalElements = personPage.getTotalElements();
		Integer totalPages = personPage.getTotalPages();

		List<PersonInfo> personInfoList = new ArrayList<>();
		for (Person ps : personPage) {

			PersonInfo personInfo = new PersonInfo();
			BeanUtils.copyProperties(ps, personInfo);
			personInfoList.add(personInfo);

		}
		// 自定义的page对象
		PageInfo pageInfo = new PageInfo(number, size, totalElements, totalPages, personInfoList);
		// 返回的最外层对象
		ResponseBody responseBody = new ResponseBody(pageInfo);
		return responseBody;
	}

	@GetMapping("/getPersonid")
	public ResponseBody getPerson(@PathParam("userid") Integer userid) {
		User user = userRepository.findByUserId(userid);
		if (user == null) {
			throw new FailReturnObject(1328, "无此用户", ReturnObject.Level.WARNING);
		}
		Person person = user.getPerson();
		if (person == null) {
			throw new FailReturnObject(1329, "此用户未关联法人", ReturnObject.Level.WARNING);
		}
		Integer personId = person.getId();
		return new ResponseBody(personId);
	}

	/**
	 * 还款客户统计表
	 * 
	 * @param productid
	 * @param name
	 * @param telephone
	 * @param identity
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/getcreditcardrepay", method = RequestMethod.GET)
	public ResponseBody getCreditcardInfoRepay(Integer productid, String name, String telephone, String identity,
			Pageable pageable) {
		List<Creditcard> creditcards = creditcardService.getCreditcardInfo(productid, name, telephone, identity);
		Collections.reverse(creditcards);
		List<Creditcard> listCreditcards =  new ArrayList<Creditcard>();
		for (Creditcard creditcard : creditcards) {
			List<Loan> loans = loanRepository.findByCreditcardId(creditcard.getId());
			if (loans != null && loans.size() > 0) {
				listCreditcards.add(creditcard);
			}
		}
		List<CreditcardInfo> list = new ArrayList<CreditcardInfo>();
		for (Creditcard creditcard : listCreditcards) {
			if (creditcard.getCreditBalance().compareTo(creditcard.getCreditGrant()) == 0) {
				CreditcardInfo creditcardInfo = new CreditcardInfo();
				creditcardInfo.setCreditBalance(creditcard.getCreditBalance());
				creditcardInfo.setCreditGrant(creditcard.getCreditGrant());
				creditcardInfo.setCreditLoan(creditcard.getCreditGrant().subtract(creditcard.getCreditBalance()));
				creditcardInfo.setName(creditcard.getPerson().getName());
				creditcardInfo.setProductLabel(creditcard.getProduct().getLabel());
				creditcardInfo.setTelephone(creditcard.getPerson().getTelephone());
				creditcardInfo.setId(creditcard.getId());
				creditcardInfo.setMargin(creditcard.getCreditGrant().multiply(new BigDecimal(0.05)).doubleValue());
				creditcardInfo.setIdentity(creditcard.getPerson().getIdentity());
				switch (creditcard.getProduct().getId()) {
				case 1:
					Orderwdsjsh orderwdsjsh = (Orderwdsjsh) creditcardService
							.getProductByPersonAndPID(creditcard.getProduct().getId(), creditcard.getPerson().getId());
					if (orderwdsjsh != null) {
						creditcardInfo.setAgentBrand(orderwdsjsh.getAgentBrand());
						creditcardInfo.setApplyDayPatchExpress(orderwdsjsh.getApplyDayPatchExpress());
						creditcardInfo.setApplyDayPickExpress(orderwdsjsh.getApplyDayPickExpress());
						creditcardInfo.setBranchesName(orderwdsjsh.getApplyEnterpriseName());
						creditcardInfo.setBusinessArea(orderwdsjsh.getApplyEnterpriseReigionName());
						creditcardInfo.setServiceName(orderwdsjsh.getServiceName());
					}

					break;
				case 2:
					Orderwdxyd orderwdxyd = (Orderwdxyd) creditcardService
							.getProductByPersonAndPID(creditcard.getProduct().getId(), creditcard.getPerson().getId());
					if (orderwdxyd != null) {
						creditcardInfo.setAgentBrand(orderwdxyd.getAgentBrand());
						creditcardInfo.setApplyDayPatchExpress(orderwdxyd.getApplyDayPatchExpress());
						creditcardInfo.setApplyDayPickExpress(orderwdxyd.getApplyDayPickExpress());
						creditcardInfo.setBranchesName(orderwdxyd.getApplyEnterpriseName());
						creditcardInfo.setBusinessArea(orderwdxyd.getApplyEnterpriseReigionName());
						creditcardInfo.setServiceName(orderwdxyd.getServiceName());
					}
					break;
				case 3:
					Orderrzzl orderrzzl = (Orderrzzl) creditcardService
							.getProductByPersonAndPID(creditcard.getProduct().getId(), creditcard.getPerson().getId());
					if (orderrzzl != null) {
						creditcardInfo.setAgentBrand(orderrzzl.getAgentBrand());
						creditcardInfo.setApplyDayPatchExpress(orderrzzl.getApplyDayPatchExpress());
						creditcardInfo.setApplyDayPickExpress(orderrzzl.getApplyDayPickExpress());
						creditcardInfo.setBranchesName(orderrzzl.getApplyEnterpriseName());
						creditcardInfo.setBusinessArea(orderrzzl.getApplyEnterpriseReigionName());
						creditcardInfo.setServiceName(orderrzzl.getServiceName());
					}
					break;
				case 4:
					Ordermdbt ordermdbt = (Ordermdbt) creditcardService
							.getProductByPersonAndPID(creditcard.getProduct().getId(), creditcard.getPerson().getId());
					if (ordermdbt != null) {
						creditcardInfo.setAgentBrand(ordermdbt.getAgentBrand());
						creditcardInfo.setApplyDayPatchExpress(ordermdbt.getApplyDayPatchExpress());
						creditcardInfo.setApplyDayPickExpress(ordermdbt.getApplyDayPickExpress());
						creditcardInfo.setBranchesName(ordermdbt.getApplyEnterpriseName());
						creditcardInfo.setBusinessArea(ordermdbt.getApplyEnterpriseReigionName());
						creditcardInfo.setServiceName(ordermdbt.getServiceName());
					}
					break;
				default:
					break;
				}
				list.add(creditcardInfo);
			}
		}
		Integer total = list.size();
		Integer pages = 0;
		if (total % pageable.getPageSize() > 0) {
			pages = total / pageable.getPageSize() + 1;
		} else {
			pages = total / pageable.getPageSize();
		}
		Integer startInteger = pageable.getPageNumber() * pageable.getPageSize() + 1;
		Integer endInteger = startInteger - 1 + pageable.getPageSize();
		if (total <= endInteger) {
			list = list.subList(startInteger - 1, total);
		} else {
			list = list.subList(startInteger - 1, endInteger);
		}
		PageInfo pageInfo = new PageInfo(pageable.getPageNumber(), pageable.getPageSize(), total, pages, list);
		return new ResponseBody(pageInfo);
	}

	/**
	 * 在借客户统计表
	 * 
	 * @param productid
	 * @param name
	 * @param telephone
	 * @param identity
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/getcreditcardloan", method = RequestMethod.GET)
	public ResponseBody getCreditcardInfoLoan(Integer productid, String name, String telephone, String identity,
			Pageable pageable) {
		List<Creditcard> creditcards = creditcardService.getCreditcardInfo(productid, name, telephone, identity);
		Collections.reverse(creditcards);
		List<CreditcardInfo> list = new ArrayList<CreditcardInfo>();
		for (Creditcard creditcard : creditcards) {
			if (creditcard.getCreditBalance().compareTo(creditcard.getCreditGrant()) < 0) {
				CreditcardInfo creditcardInfo = new CreditcardInfo();
				creditcardInfo.setCreditBalance(creditcard.getCreditBalance());
				creditcardInfo.setCreditGrant(creditcard.getCreditGrant());
				creditcardInfo.setCreditLoan(creditcard.getCreditGrant().subtract(creditcard.getCreditBalance()));
				creditcardInfo.setName(creditcard.getPerson().getName());
				creditcardInfo.setProductLabel(creditcard.getProduct().getLabel());
				creditcardInfo.setTelephone(creditcard.getPerson().getTelephone());
				creditcardInfo.setId(creditcard.getId());
				creditcardInfo.setMargin(creditcard.getCreditGrant().multiply(new BigDecimal(0.05)).doubleValue());
				creditcardInfo.setIdentity(creditcard.getPerson().getIdentity());
				switch (creditcard.getProduct().getId()) {
				case 1:
					Orderwdsjsh orderwdsjsh = (Orderwdsjsh) creditcardService
							.getProductByPersonAndPID(creditcard.getProduct().getId(), creditcard.getPerson().getId());
					if (orderwdsjsh != null) {
						creditcardInfo.setAgentBrand(orderwdsjsh.getAgentBrand());
						creditcardInfo.setApplyDayPatchExpress(orderwdsjsh.getApplyDayPatchExpress());
						creditcardInfo.setApplyDayPickExpress(orderwdsjsh.getApplyDayPickExpress());
						creditcardInfo.setBranchesName(orderwdsjsh.getApplyEnterpriseName());
						creditcardInfo.setBusinessArea(orderwdsjsh.getApplyEnterpriseReigionName());
						creditcardInfo.setServiceName(orderwdsjsh.getServiceName());
					}

					break;
				case 2:
					Orderwdxyd orderwdxyd = (Orderwdxyd) creditcardService
							.getProductByPersonAndPID(creditcard.getProduct().getId(), creditcard.getPerson().getId());
					if (orderwdxyd != null) {
						creditcardInfo.setAgentBrand(orderwdxyd.getAgentBrand());
						creditcardInfo.setApplyDayPatchExpress(orderwdxyd.getApplyDayPatchExpress());
						creditcardInfo.setApplyDayPickExpress(orderwdxyd.getApplyDayPickExpress());
						creditcardInfo.setBranchesName(orderwdxyd.getApplyEnterpriseName());
						creditcardInfo.setBusinessArea(orderwdxyd.getApplyEnterpriseReigionName());
						creditcardInfo.setServiceName(orderwdxyd.getServiceName());
					}
					break;
				case 3:
					Orderrzzl orderrzzl = (Orderrzzl) creditcardService
							.getProductByPersonAndPID(creditcard.getProduct().getId(), creditcard.getPerson().getId());
					if (orderrzzl != null) {
						creditcardInfo.setAgentBrand(orderrzzl.getAgentBrand());
						creditcardInfo.setApplyDayPatchExpress(orderrzzl.getApplyDayPatchExpress());
						creditcardInfo.setApplyDayPickExpress(orderrzzl.getApplyDayPickExpress());
						creditcardInfo.setBranchesName(orderrzzl.getApplyEnterpriseName());
						creditcardInfo.setBusinessArea(orderrzzl.getApplyEnterpriseReigionName());
						creditcardInfo.setServiceName(orderrzzl.getServiceName());
					}
					break;
				case 4:
					Ordermdbt ordermdbt = (Ordermdbt) creditcardService
							.getProductByPersonAndPID(creditcard.getProduct().getId(), creditcard.getPerson().getId());
					if (ordermdbt != null) {
						creditcardInfo.setAgentBrand(ordermdbt.getAgentBrand());
						creditcardInfo.setApplyDayPatchExpress(ordermdbt.getApplyDayPatchExpress());
						creditcardInfo.setApplyDayPickExpress(ordermdbt.getApplyDayPickExpress());
						creditcardInfo.setBranchesName(ordermdbt.getApplyEnterpriseName());
						creditcardInfo.setBusinessArea(ordermdbt.getApplyEnterpriseReigionName());
						creditcardInfo.setServiceName(ordermdbt.getServiceName());
					}
					break;
				default:
					break;
				}
				list.add(creditcardInfo);
			}
		}
		Integer total = list.size();
		Integer pages = 0;
		if (total % pageable.getPageSize() > 0) {
			pages = total / pageable.getPageSize() + 1;
		} else {
			pages = total / pageable.getPageSize();
		}
		Integer startInteger = pageable.getPageNumber() * pageable.getPageSize() + 1;
		Integer endInteger = startInteger - 1 + pageable.getPageSize();
		if (total <= endInteger) {
			list = list.subList(startInteger - 1, total);
		} else {
			list = list.subList(startInteger - 1, endInteger);
		}
		PageInfo pageInfo = new PageInfo(pageable.getPageNumber(), pageable.getPageSize(), total, pages, list);
		return new ResponseBody(pageInfo);
	}

	/**
	 * 未做客户统计
	 * 
	 * @param productid
	 * @param name
	 * @param telephone
	 * @param identity
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/getnopassperson")
	public ResponseBody getNoPassPerson(Integer productid, String name, String telephone, String identity,
			Pageable pageable) {
		List<NoPassPersonInfo> list = new ArrayList<NoPassPersonInfo>();
		if (productid != null) {
			switch (productid) {
			case 1:
				List<Orderwdsjsh> orderwdsjshs = creditcardService.getSjshNoPass(name, telephone, identity);
				list = getResponseSjsh(orderwdsjshs);
				break;
			case 2:
				List<Orderwdxyd> orderwdxyds = creditcardService.getWdxydNoPass(name, telephone, identity);
				list = getResponseWdxyd(orderwdxyds);
				break;
			case 3:
				List<Orderrzzl> orderrzzls = creditcardService.getRzzlNoPass(name, telephone, identity);
				list = getResponseRzzl(orderrzzls);
				break;
			case 4:
				List<Ordermdbt> ordermdbts = creditcardService.getMdbtNoPass(name, telephone, identity);
				list = getResponseMdbt(ordermdbts);
				break;
			default:
				break;
			}
		} else {
			List<Orderwdsjsh> orderwdsjshList = creditcardService.getSjshNoPass(name, telephone, identity);
			List<Orderwdxyd> orderwdxydlList = creditcardService.getWdxydNoPass(name, telephone, identity);
			List<Orderrzzl> orderrzzllList = creditcardService.getRzzlNoPass(name, telephone, identity);
			List<Ordermdbt> ordermdbtlList = creditcardService.getMdbtNoPass(name, telephone, identity);
			for (NoPassPersonInfo noPassPersonInfo : getResponseSjsh(orderwdsjshList)) {
				list.add(noPassPersonInfo);
			}
			for (NoPassPersonInfo noPassPersonInfo : getResponseMdbt(ordermdbtlList)) {
				list.add(noPassPersonInfo);
			}
			for (NoPassPersonInfo noPassPersonInfo : getResponseRzzl(orderrzzllList)) {
				list.add(noPassPersonInfo);
			}
			for (NoPassPersonInfo noPassPersonInfo : getResponseWdxyd(orderwdxydlList)) {
				list.add(noPassPersonInfo);
			}

		}

		Integer total = list.size();
		Integer pages = 0;
		if (total % pageable.getPageSize() > 0) {
			pages = total / pageable.getPageSize() + 1;
		} else {
			pages = total / pageable.getPageSize();
		}
		Integer startInteger = pageable.getPageNumber() * pageable.getPageSize() + 1;
		Integer endInteger = startInteger - 1 + pageable.getPageSize();
		if (total <= endInteger) {
			list = list.subList(startInteger - 1, total);
		} else {
			list = list.subList(startInteger - 1, endInteger);
		}
		PageInfo pageInfo = new PageInfo(pageable.getPageNumber(), pageable.getPageSize(), total, pages, list);
		return new ResponseBody(pageInfo);
	}

	public List<NoPassPersonInfo> getResponseMdbt(List<Ordermdbt> ordermdbts) {
		List<NoPassPersonInfo> list = new ArrayList<NoPassPersonInfo>();
		for (Ordermdbt ordermdbt : ordermdbts) {
			NoPassPersonInfo noPassPersonInfo = new NoPassPersonInfo();
			noPassPersonInfo.setAgentBrand(ordermdbt.getAgentBrand());
			noPassPersonInfo.setApplyAmount(ordermdbt.getApplyAmount());
			noPassPersonInfo.setApplyDayPatchExpress(ordermdbt.getApplyDayPatchExpress());
			noPassPersonInfo.setApplyDayPickExpress(ordermdbt.getApplyDayPickExpress());
			noPassPersonInfo.setBranchesName(ordermdbt.getApplyEnterpriseName());
			noPassPersonInfo.setBusinessArea(ordermdbt.getApplyEnterpriseReigionName());
			noPassPersonInfo.setIdentity(ordermdbt.getApplyIdentityNo());
			noPassPersonInfo.setName(ordermdbt.getRealName());
			noPassPersonInfo.setProductLabel(ordermdbt.getProduct().getLabel());
			noPassPersonInfo.setReason(ordermdbt.getComment());
			noPassPersonInfo.setServiceName(ordermdbt.getServiceName());
			noPassPersonInfo.setTelephone(ordermdbt.getApplyMobile());
			list.add(noPassPersonInfo);
		}
		return list;
	}

	public List<NoPassPersonInfo> getResponseSjsh(List<Orderwdsjsh> orderwdsjshs) {
		List<NoPassPersonInfo> list = new ArrayList<NoPassPersonInfo>();
		for (Orderwdsjsh orderwdsjsh : orderwdsjshs) {
			NoPassPersonInfo noPassPersonInfo = new NoPassPersonInfo();
			noPassPersonInfo.setAgentBrand(orderwdsjsh.getAgentBrand());
			noPassPersonInfo.setApplyAmount(orderwdsjsh.getApplyAmount());
			noPassPersonInfo.setApplyDayPatchExpress(orderwdsjsh.getApplyDayPatchExpress());
			noPassPersonInfo.setApplyDayPickExpress(orderwdsjsh.getApplyDayPickExpress());
			noPassPersonInfo.setBranchesName(orderwdsjsh.getApplyEnterpriseName());
			noPassPersonInfo.setBusinessArea(orderwdsjsh.getApplyEnterpriseReigionName());
			noPassPersonInfo.setIdentity(orderwdsjsh.getApplyIdentityNo());
			noPassPersonInfo.setName(orderwdsjsh.getRealName());
			noPassPersonInfo.setProductLabel(orderwdsjsh.getProduct().getLabel());
			noPassPersonInfo.setReason(orderwdsjsh.getComment());
			noPassPersonInfo.setServiceName(orderwdsjsh.getServiceName());
			noPassPersonInfo.setTelephone(orderwdsjsh.getApplyMobile());
			list.add(noPassPersonInfo);
		}
		return list;
	}

	public List<NoPassPersonInfo> getResponseRzzl(List<Orderrzzl> orderrzzls) {
		List<NoPassPersonInfo> list = new ArrayList<NoPassPersonInfo>();
		for (Orderrzzl orderrzzl : orderrzzls) {
			NoPassPersonInfo noPassPersonInfo = new NoPassPersonInfo();
			noPassPersonInfo.setAgentBrand(orderrzzl.getAgentBrand());
			noPassPersonInfo.setApplyAmount(orderrzzl.getApplyAmount());
			noPassPersonInfo.setApplyDayPatchExpress(orderrzzl.getApplyDayPatchExpress());
			noPassPersonInfo.setApplyDayPickExpress(orderrzzl.getApplyDayPickExpress());
			noPassPersonInfo.setBranchesName(orderrzzl.getApplyEnterpriseName());
			noPassPersonInfo.setBusinessArea(orderrzzl.getApplyEnterpriseReigionName());
			noPassPersonInfo.setIdentity(orderrzzl.getApplyIdentityNo());
			noPassPersonInfo.setName(orderrzzl.getRealName());
			noPassPersonInfo.setProductLabel(orderrzzl.getProduct().getLabel());
			noPassPersonInfo.setReason(orderrzzl.getComment());
			noPassPersonInfo.setServiceName(orderrzzl.getServiceName());
			noPassPersonInfo.setTelephone(orderrzzl.getApplyMobile());
			list.add(noPassPersonInfo);
		}
		return list;
	}

	public List<NoPassPersonInfo> getResponseWdxyd(List<Orderwdxyd> orderwdxyds) {
		List<NoPassPersonInfo> list = new ArrayList<NoPassPersonInfo>();
		for (Orderwdxyd orderwdxyd : orderwdxyds) {
			NoPassPersonInfo noPassPersonInfo = new NoPassPersonInfo();
			noPassPersonInfo.setAgentBrand(orderwdxyd.getAgentBrand());
			noPassPersonInfo.setApplyAmount(orderwdxyd.getApplyAmount());
			noPassPersonInfo.setApplyDayPatchExpress(orderwdxyd.getApplyDayPatchExpress());
			noPassPersonInfo.setApplyDayPickExpress(orderwdxyd.getApplyDayPickExpress());
			noPassPersonInfo.setBranchesName(orderwdxyd.getApplyEnterpriseName());
			noPassPersonInfo.setBusinessArea(orderwdxyd.getApplyEnterpriseReigionName());
			noPassPersonInfo.setIdentity(orderwdxyd.getApplyIdentityNo());
			noPassPersonInfo.setName(orderwdxyd.getRealName());
			noPassPersonInfo.setProductLabel(orderwdxyd.getProduct().getLabel());
			noPassPersonInfo.setReason(orderwdxyd.getComment());
			noPassPersonInfo.setServiceName(orderwdxyd.getServiceName());
			noPassPersonInfo.setTelephone(orderwdxyd.getApplyMobile());
			list.add(noPassPersonInfo);
		}
		return list;
	}

	@RequestMapping(value = "/getcreditcarddetail")
	public ResponseBody getCreditcardInfoDetail(Integer creditcardId) {
		Creditcard creditcard = creditcardRepository.findOne(creditcardId);
		List<LoaningInfo> list = creditcardService.getLoanByCreditcard(creditcard);
		return new ResponseBody(list);
	}
	
	/**
	 * 网点随借随还和面单白条客户借款情况统计
	 */
	@GetMapping("/getProductPersonSituation")
	public ResponseBody getProductPersonSituation(){
		
		//1、随借随还借款客户情况统计
		//还款客户（结清客户）
		int sjshhkPerson = loanRepository.getOrderwdsjshSettleCreditcardSum();
		//所有客户（随借随还所有信用卡）
		int sjshallPerson = creditcardRepository.getOrderwdsjshCreditcardSum();
		//授信已借钱的客户
		int sjshyjPerson = creditcardRepository.getOrderwdsjshLendingCreditcard();
		//授信未借过钱的客户(所有客户-结清客户-在借客户)
		int sjshwjPerson = sjshallPerson - sjshhkPerson - sjshyjPerson;
		//未做客户
		int sjshwzPerson = orderwdsjshRepository.getOderwdsjshDeniedSum();
		//在借客户=授信已借钱的客户+授信未借过钱的客户
		//int sjshzjPerson = sjshyjPerson + sjshwjPerson;
		PersonSituation sjsjPerson = new PersonSituation(sjshhkPerson, sjshwzPerson, sjshyjPerson);
		
		Map<String, Object> map = new HashMap<>();
		map.put("sjsh",sjsjPerson);
		//2、面单白条借款客户请款统计
		//还款客户（结清客户）
		int mdbthkPerson = loanRepository.getOrdermdbtSettleCreditcardSum();
		//所有客户（随借随还所有信用卡）
		int mdbtallPerson = creditcardRepository.getOrdermdbtCreditcardSum();
		//授信已借钱的客户
		int mdbtyjPerson = creditcardRepository.getOrdermdbtLendingCreditcard();
		//授信未借过钱的客户(所有客户-结清客户-在借客户)
		int mdbtwjPerson = mdbtallPerson - mdbthkPerson - mdbtyjPerson;
		//未做客户
		int mdbtwzPerson = ordermdbtRepository.getOrdermdbtDeniedSum();
		//在借客户=在借客户+授信未借过钱的客户
		//int mdbtzjPerson = mdbtyjPerson + mdbtwjPerson;
		
		PersonSituation mdbtPerson = new PersonSituation(mdbthkPerson, mdbtwzPerson, mdbtyjPerson);		
		map.put("mdbt",mdbtPerson);
		
		return new ResponseBody(map);
	}
		
}
       class PersonSituation{
    	   
    	  private int hkPerson;
    	  
    	  private int wzPerson;
    	 
    	  private int zjPerson;

		public PersonSituation(int hkPerson, int wzPerson, int zjPerson) {
			super();
			this.hkPerson = hkPerson;
			this.wzPerson = wzPerson;
			this.zjPerson = zjPerson;
		}

		public int getHkPerson() {
			return hkPerson;
		}

		public void setHkPerson(int hkPerson) {
			this.hkPerson = hkPerson;
		}

		public int getWzPerson() {
			return wzPerson;
		}

		public void setWzPerson(int wzPerson) {
			this.wzPerson = wzPerson;
		}

		public int getZjPerson() {
			return zjPerson;
		}

		public void setZjPerson(int zjPerson) {
			this.zjPerson = zjPerson;
		}
		   
       }
