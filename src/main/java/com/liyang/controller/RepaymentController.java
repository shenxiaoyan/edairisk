package com.liyang.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.liyang.domain.creditrepayment.Creditrepayment;
import com.liyang.domain.user.User;
import com.liyang.dto.RepaymentInformation;
import com.liyang.service.CreditrepaymentService;
import com.liyang.util.CommonUtil;
import com.liyang.util.PageInfo;
import com.liyang.util.ResponseBody;

@RestController
@RequestMapping("/repayment")
public class RepaymentController {

	@Autowired
	private CreditrepaymentService creditrepaymentService;

	@RequestMapping(value = "/searchrepaymentrecord", method = RequestMethod.GET)
	public ResponseBody searchRepaymentRecord(String realname, String telephone, Pageable pageable, Integer productid,
			String stateCode,String orderNo) {
		Page<Creditrepayment> page = creditrepaymentService.getRepaymentRecordBySearch(realname, telephone, pageable,
				productid, stateCode,orderNo);
		List<RepaymentInformation> list = new ArrayList<RepaymentInformation>();
		for (Creditrepayment creditrepayment : page) {
			RepaymentInformation information = new RepaymentInformation();
			information.setInformation(creditrepayment.getInformation());
			information.setInterest(creditrepayment.getInterest());
			information.setIsOverdue(creditrepayment.getIsOverdue());
			information.setPrincipal(creditrepayment.getPrincipal());
			information.setPunishinterest(creditrepayment.getPunishinterest());
			information.setLabel(creditrepayment.getCreditcard().getProduct().getLabel());
			information.setName(creditrepayment.getCreditcard().getPerson().getName());
			information.setId(creditrepayment.getId());
			information.setRealDate(creditrepayment.getDebtorActualRepaymentDate());
			information.setRepaymentName(creditrepayment.getDebtorName());
			information.setOrderNo(creditrepayment.getOrderNo());
			information.setPhoneString(creditrepayment.getCreditcard().getCreditcardIdentity());
			information.setRemainAmount(creditrepayment.getCreditcard().getCreditBalance());
			information.setPlanDate(creditrepayment.getDebtorRepaymentDate());
			information.setRealPayAmount(creditrepayment.getPayAmount());
			information.setStateLabel(creditrepayment.getState().getLabel());
			information.setFees(creditrepayment.getFees());
			list.add(information);
		}
		PageInfo pageInfo = new PageInfo(page.getNumber(), page.getSize(), page.getTotalElements(),
				page.getTotalPages(), list);
		ResponseBody responseBody = new ResponseBody(pageInfo);
		return responseBody;
	}
}
