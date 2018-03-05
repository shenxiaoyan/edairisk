package com.liyang.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.liyang.domain.company.Company;
import com.liyang.domain.company.CompanyRepository;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserRepository;
import com.liyang.dto.CompanyInfo;
import com.liyang.dto.CompanyListInfo;
import com.liyang.dto.UserInfo;
import com.liyang.service.CompanyService;
import com.liyang.service.UserService;
import com.liyang.util.FailReturnObject;
import com.liyang.util.PageInfo;
import com.liyang.util.ResponseBody;
import com.liyang.util.ReturnObject.Level;

@RestController
@RequestMapping("/company")
public class CompanyController {
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private CompanyService companyService;

	@RequestMapping("/getcompanyinformation")
	public ResponseBody getCompanyInformation(Integer userid) {
		User user = userRepository.findByUserId(userid);
		String companyRoleName = user.getCompanyRole();
		Map<String, Object> map = new HashMap<String, Object>();
		if (companyRoleName != null && "manager".equals(companyRoleName)) {
			Company company = companyRepository.findByCreateBy(userid);
			CompanyInfo companyInfo = new CompanyInfo();
			companyInfo.setId(company.getId());
			companyInfo.setCompanyProvince(company.getCompanyProvince());
			companyInfo.setCompanyCity(company.getCompanyCity());
			companyInfo.setCompanyDistrict(company.getCompanyDistrict());
			companyInfo.setCompanyAddress(company.getCompanyAddress());
			companyInfo.setCompanyName(company.getCompanyName());
			companyInfo.setCompanyNumber(company.getCompanyNumber());
			companyInfo.setCompanyPersonName(company.getCompanyPersonName());
			companyInfo.setState(company.getState().getStateCode());
			companyInfo.setLabel(company.getState().getLabel());
			List<User> users = userRepository.findByCompany(company.getId());
			List<UserInfo> infos = new ArrayList<UserInfo>();
			for (User user2 : users) {
				if (!"manager".equals(user2.getCompanyRole())) {
					UserInfo userInfo = new UserInfo();
					userInfo.setRealname(user2.getRealname());
					userInfo.setUsername(user2.getUsername());
					infos.add(userInfo);
				}
			}
			map.put("employee", infos);
			map.put("company", companyInfo);
			return new ResponseBody(map);
		}
		if (companyRoleName != null && "employee".equals(companyRoleName)) {
			Company company = user.getCompany();
			CompanyInfo companyInfo = new CompanyInfo();
			companyInfo.setId(company.getId());
			companyInfo.setCompanyProvince(company.getCompanyProvince());
			companyInfo.setCompanyCity(company.getCompanyCity());
			companyInfo.setCompanyDistrict(company.getCompanyDistrict());
			companyInfo.setCompanyAddress(company.getCompanyAddress());
			companyInfo.setCompanyName(company.getCompanyName());
			companyInfo.setCompanyNumber(company.getCompanyNumber());
			companyInfo.setCompanyPersonName(company.getCompanyPersonName());
			companyInfo.setState(company.getState().getStateCode());
			companyInfo.setLabel(company.getState().getLabel());
			List<User> users = userRepository.findByCompany(company.getId());
			List<UserInfo> infos = new ArrayList<UserInfo>();
			for (User user2 : users) {
				if (!"manager".equals(user2.getCompanyRole())) {
					UserInfo userInfo = new UserInfo();
					userInfo.setRealname(user2.getRealname());
					userInfo.setUsername(user2.getUsername());
					infos.add(userInfo);
				}
			}
			map.put("employee", infos);
			map.put("company", companyInfo);
			return new ResponseBody(map);
		}
		map.put("employee", null);
		map.put("company", null);
		return new ResponseBody(map);
	}

	@RequestMapping(value = "/addemployee")
	public ResponseBody addEmployee(String username, String realname, String smscode, Integer userid) {
		userService.verifySmsCode(username, smscode);
		Company company = companyRepository.findByCreateBy(userid);
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new FailReturnObject(2567, "该手机号没有注册账号", Level.WARNING);
		}
		if (user.getCompany() != null) {
			throw new FailReturnObject(2349, "该用户已经有所属公司", Level.WARNING);
		}
		user.setRealname(realname);
		user.setCompanyRole("employee");
		user.setCompany(company);
		userRepository.save(user);
		int peopleAmount = company.getPeopleAmount();
		peopleAmount++;
		company.setPeopleAmount(peopleAmount);
		companyRepository.save(company);
		return new ResponseBody("ok");
	}

	@RequestMapping("/removeemployee")
	public ResponseBody removeEmployee(String username) {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new FailReturnObject(2567, "该手机号没有注册", Level.WARNING);
		}
		Company company = user.getCompany();
		user.setCompanyRole(null);
		user.setCompany(null);
		userRepository.save(user);
		int peopleAmount = company.getPeopleAmount();
		peopleAmount--;
		company.setPeopleAmount(peopleAmount);
		companyRepository.save(company);
		return new ResponseBody("ok");
	}

	@RequestMapping("/addcompany")
	public ResponseBody addCompany(Company company, Integer userid) {
		company = companyService.doCreate(company, userid);
		CompanyInfo companyInfo = new CompanyInfo();
		companyInfo.setId(company.getId());
		return new ResponseBody(companyInfo);
	}

	@RequestMapping("/changecompanyinfo")
	public ResponseBody changeCompanyInfo(Company company) {
		companyService.changeCompanyInfo(company);
		return new ResponseBody("ok");
	}
	
	@GetMapping("/list")
	public ResponseBody getCompanyList(String stateCode,String companyName,String companyNumber,
                                       String companyPersonName,Pageable pageable){
	
		Page<Company> companyPage = companyService.getCompanyList(stateCode, companyName, companyNumber, companyPersonName,pageable);
		Integer  number = companyPage.getNumber();
		Integer  size = companyPage.getSize();
		long  totalElements = companyPage.getTotalElements();
		Integer  totalPages = companyPage.getTotalPages();
		
		List<CompanyListInfo> CompanyListInfoList = new ArrayList<>();
		for (Company cp : companyPage) {
			
			CompanyListInfo companyListInfo = new CompanyListInfo();
			BeanUtils.copyProperties(cp, companyListInfo);
			companyListInfo.setState(cp.getState().getStateCode());
			companyListInfo.setLabel(cp.getState().getLabel());
			CompanyListInfoList.add(companyListInfo);	
			
		}
		//自定义的page对象
		PageInfo pageInfo = new PageInfo(number, size, totalElements, totalPages, CompanyListInfoList);
		//返回的最外层对象
		ResponseBody  responseBody = new ResponseBody(pageInfo);
		return responseBody;
	}
}
