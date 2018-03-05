package com.liyang.controller;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.liyang.domain.company.Company;
import com.liyang.domain.company.CompanyRepository;
import com.liyang.domain.creditcard.Creditcard;
import com.liyang.domain.creditcard.CreditcardRepository;
import com.liyang.domain.person.Person;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserRepository;
import com.liyang.service.UserInfoService;
import com.liyang.util.ResponseBody;

@RestController
public class UserInfoController {

	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CreditcardRepository creditcardRepository;
	@Autowired
	private CompanyRepository companyRepository;

	/**
	 * 根据userid查询该用户下产品使用信息 后续添加新产品再添加 新产品使用信息
	 * 
	 * @param userid
	 * @return
	 */
	@RequestMapping(value = "/getuserproductinfo", method = RequestMethod.GET)
	public ResponseBody getProductInfoByUser(Integer userid) {

		Map<String, Object> sjshMap = userInfoService.getSjshAmount(userid);
		Information sjsh = dealMap(sjshMap);
		Map<String, Object> rzzlMap = userInfoService.getRzzlAmount(userid);
		Information rzzl = dealMap(rzzlMap);
		Map<String, Object> wdxydMap = userInfoService.getWdxydAmount(userid);
		Information wdxyd = dealMap(wdxydMap);
		Map<String, Object> mdbtMap = userInfoService.getMdbtAmount(userid);
		Information mdbt = dealMap(mdbtMap);
		
		
		ReturnInfomation infomation = new ReturnInfomation(sjsh, wdxyd, rzzl,mdbt);
		
		User user = userRepository.findByUserId(userid);
		Company company = user.getCompany();
		if(null != company){
			Creditcard creditcard = creditcardRepository.findByCompany(company.getId());
			if(creditcard != null){
				Information companyMDBT = new Information();
				companyMDBT.setApply(true);
				companyMDBT.setCardId(creditcard.getId());
				companyMDBT.setReamin(creditcard.getCreditBalance());
				companyMDBT.setTotal(creditcard.getCreditGrant());
				companyMDBT.setState("ENABLED");
				infomation.setCompanyMDBT(companyMDBT);
			}
		}
		
		ResponseBody responseBody = new ResponseBody(infomation);
		return responseBody;
	}

	/**
	 * 返回前端对象的封装 后续增加新产品再添加information属性
	 * 
	 * @author Administrator
	 *
	 */
	class ReturnInfomation {
		
		private Information sjsh;
		private Information wdxyd;
		private Information rzzl;
		private Information mdbt;
		private Information companyMDBT;

		public ReturnInfomation(Information sjsh, Information wdxyd, Information rzzl, Information mdbt) {
			super();
			this.sjsh = sjsh;
			this.wdxyd = wdxyd;
			this.rzzl = rzzl;
			this.mdbt = mdbt;
		}		
		
		public Information getMdbt() {
			return mdbt;
		}


		public void setMdbt(Information mdbt) {
			this.mdbt = mdbt;
		}

		
		

		public Information getCompanyMDBT() {
			return companyMDBT;
		}

		public void setCompanyMDBT(Information companyMDBT) {
			this.companyMDBT = companyMDBT;
		}

		public Information getSjsh() {
			return sjsh;
		}

		public void setSjsh(Information sjsh) {
			this.sjsh = sjsh;
		}

		public Information getWdxyd() {
			return wdxyd;
		}

		public void setWdxyd(Information wdxyd) {
			this.wdxyd = wdxyd;
		}

		public Information getRzzl() {
			return rzzl;
		}

		public void setRzzl(Information rzzl) {
			this.rzzl = rzzl;
		}

	}
	

	/**
	 * 具体产品封装对象 如sjsh
	 * 
	 * @author Administrator
	 *
	 */
	class Information {

		private String state;
		private BigDecimal reamin;
		private BigDecimal total;
		private boolean apply;
		private Integer cardId;

		public String getState() {
			return state;
		}

		public BigDecimal getTotal() {
			return total;
		}

		public void setTotal(BigDecimal total) {
			this.total = total;
		}

		public void setState(String state) {
			this.state = state;
		}

		public BigDecimal getReamin() {
			return reamin;
		}

		public void setReamin(BigDecimal reamin) {
			this.reamin = reamin;
		}

		public boolean isApply() {
			return apply;
		}

		public void setApply(boolean apply) {
			this.apply = apply;
		}

		public Integer getCardId() {
			return cardId;
		}

		public void setCardId(Integer cardId) {
			this.cardId = cardId;
		}

	}

	/**
	 * 将某个产品返回的map进行处理，返回产品封装对象infomation
	 * 
	 * @param map
	 * @return
	 */
	public Information dealMap(Map<String, Object> map) {
		Information information = new Information();
		if ("ok".equals(map.get("have"))) {
			information.setApply(true);
			information.setState(map.get("state").toString());
			if ("ENABLED".equals(information.getState())) {
				information.setReamin((BigDecimal) map.get("remain"));
				information.setTotal((BigDecimal) map.get("total"));
				information.setCardId((Integer) map.get("id"));
			}
		}
		return information;
	}

}
