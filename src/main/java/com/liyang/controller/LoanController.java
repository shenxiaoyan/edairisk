package com.liyang.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.coyote.http11.filters.VoidInputFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.liyang.domain.loan.Loan;
import com.liyang.domain.user.User;
import com.liyang.dto.LoanInformation;
import com.liyang.service.CreditrepaymentService;
import com.liyang.service.LoanService;
import com.liyang.util.CommonUtil;
import com.liyang.util.PageInfo;
import com.liyang.util.ResponseBody;

@RestController
@RequestMapping("/loan")
public class LoanController {

	@Autowired
	private LoanService loanService;
	@Autowired
	private CreditrepaymentService creditrepaymentService;

	@RequestMapping(value = "/searchloanrecord", method = RequestMethod.GET)
	public ResponseBody searchLoanRecord(String realname, String telephone, String stateCode, Pageable pageable,
			Integer productid, String orderNo, Integer overdue) {
		Page<Loan> page = loanService.getLoanRecordBySearch(realname, telephone, productid, pageable, stateCode,
				orderNo, overdue);
		List<LoanInformation> list2 = new ArrayList<LoanInformation>();
		for (Loan loan2 : page) {
			LoanInformation information = new LoanInformation();
			information.setInformation(loan2.getInformation());
			information.setLabel(loan2.getCreditcard().getProduct().getLabel());
			information.setCreditGrant(loan2.getCreditcard().getCreditGrant());
			information.setId(loan2.getId());
			information.setApplyDate(loan2.getApplyDate());
			information.setUseDate(loan2.getUseDate());
			information.setUserName(loan2.getDebtorName());
			information.setPersonName(loan2.getCreditcard().getPerson().getName());
			information.setLoanSn(loan2.getOrderNo());
			information.setPhone(loan2.getCreditcard().getCreditcardIdentity());
			information.setLoanAmount(loan2.getPrincipal());
			information.setTotalInterest(loan2.getTotalInterest());
			information.setRemainAmount(loan2.getRemainAmount());
			information.setStateLabel(loan2.getState().getLabel());
			if (loan2.getCreditcard().getCompany() != null) {
				information.setCompanyName(loan2.getCreditcard().getCompany().getCompanyName());
			}
			information.setPurchasingUnit(loan2.getPurchasingUnit());
			information.setCreditBalance(loan2.getCreditcard().getCreditBalance());
			information.setOverdue(loan2.getOverdue());
			information.setAccountStatus(loan2.getDebtorPerson().getAccountStatus());
			list2.add(information);
		}
		PageInfo pageInfo = new PageInfo(page.getNumber(), page.getSize(), page.getTotalElements(),
				page.getTotalPages(), list2);
		ResponseBody responseBody = new ResponseBody(pageInfo);
		return responseBody;
	}
	
	/**
	 * 近30天，每隔3天的借还款总额统计
	 * @return
	 */
	@GetMapping("/getDayMoneySumSituation")
	public Map<String, Object> getDayMoneySumSituation(){
		
		Map<String, Object> map = new HashMap<>();
		//借款
		List<Long> loanlist = loanService.getDaySumLoanAmountSituation();
		//还款
		List<String> creditrepaymentlist = creditrepaymentService.getDaySumCreditrepaymentAmountSituation();
		map.put("loan", loanlist);
		map.put("creditrepayment", creditrepaymentlist);
		return map;
		
	}

}
