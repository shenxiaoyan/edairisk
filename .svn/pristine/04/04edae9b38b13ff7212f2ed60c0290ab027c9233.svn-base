package com.liyang.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.liyang.domain.loan.Loanoverdue;
import com.liyang.dto.OverdueInfo;
import com.liyang.service.LoanService;
import com.liyang.util.PageInfo;
import com.liyang.util.ResponseBody;

@RestController
@RequestMapping(value = "/overdue")
public class OverdueController {

	@Autowired
	private LoanService loanService;

	@RequestMapping(value = "/searchoverdue", method = RequestMethod.GET)
	public ResponseBody searchOverdue(String name, String phone, Integer status, String loanSn, Pageable pageable) {
		Page<Loanoverdue> page = loanService.getOverdueBySearch(name, phone, status, loanSn, pageable);
		List<OverdueInfo> list = new ArrayList<OverdueInfo>();
		for (Loanoverdue loanoverdue : page) {
			OverdueInfo overdueInfo = new OverdueInfo();
			overdueInfo.setCreateAt(loanoverdue.getCreatedAt());
			overdueInfo.setHistoryAmount(loanoverdue.getHistoryAmount());
			overdueInfo.setOverdueAmount(loanoverdue.getOverdueAmount());
			overdueInfo.setLoanId(loanoverdue.getLoan().getId());
			overdueInfo.setPenalSum(loanoverdue.getPenalSum());
			overdueInfo.setPhone(loanoverdue.getLoan().getDebtorPerson().getTelephone());
			overdueInfo.setPrincipal(loanoverdue.getLoan().getPrincipal());
			overdueInfo.setStatus(loanoverdue.getStatus());
			overdueInfo.setFinishAt(loanoverdue.getFinishAt());
			overdueInfo.setOverdueDays(loanoverdue.getOverdueDays());
			overdueInfo.setName(loanoverdue.getLoan().getDebtorPerson().getName());
			overdueInfo.setOrderNo(loanoverdue.getLoan().getOrderNo());
			overdueInfo.setOverdueId(loanoverdue.getId());
			list.add(overdueInfo);
		}
		PageInfo pageInfo = new PageInfo(page.getNumber(), page.getSize(), page.getTotalElements(),
				page.getTotalPages(), list);
		return new ResponseBody(pageInfo);
	}

}
