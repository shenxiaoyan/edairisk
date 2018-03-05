package com.liyang.service;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.liyang.domain.base.AbstractAuditorAct.ActGroup;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.jpa.query.expression.QueryExpSpecificationBuilder;
import com.jpa.query.expression.generic.GenericQueryExpSpecification;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.company.Company;
import com.liyang.domain.company.CompanyAct;
import com.liyang.domain.company.CompanyActRepository;
import com.liyang.domain.company.CompanyLog;
import com.liyang.domain.company.CompanyLogRepository;
import com.liyang.domain.company.CompanyRepository;
import com.liyang.domain.company.CompanyState;
import com.liyang.domain.company.CompanyStateRepository;
import com.liyang.domain.department.Department;
import com.liyang.domain.quota.Quota;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserRepository;
import com.liyang.domain.user.userProjection;
import com.liyang.util.FailReturnObject;
import com.liyang.util.ReturnObject.Level;
import com.timevale.tgtext.text.pdf.PdfStructTreeController.returnType;
import com.timevale.tgtext.text.pdf.parser.u;

@Service
@Order(10000)
public class CompanyService extends AbstractAuditorService<Company, CompanyState, CompanyAct, CompanyLog> {

	@Autowired
	CompanyActRepository companyActRepository;

	@Autowired
	CompanyStateRepository companyStateRepository;

	@Autowired
	CompanyLogRepository companyLogRepository;

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired

	@Override
	@PostConstruct
	public void sqlInit() {

		long findAll = companyActRepository.count();
		if (findAll == 0) {
			CompanyAct save1 = companyActRepository.save(new CompanyAct("创建", "create", 10, ActGroup.UPDATE));
			CompanyAct save2 = companyActRepository.save(new CompanyAct("读取", "read", 20, ActGroup.READ));
			CompanyAct save3 = companyActRepository.save(new CompanyAct("更新", "update", 30, ActGroup.UPDATE));
			CompanyAct save4 = companyActRepository.save(new CompanyAct("删除", "delete", 40, ActGroup.UPDATE));
			CompanyAct save5 = companyActRepository.save(new CompanyAct("自己的列表", "listOwn", 50, ActGroup.READ));
			CompanyAct save6 = companyActRepository
					.save(new CompanyAct("部门的列表", "listOwnDepartment", 60, ActGroup.READ));
			CompanyAct save7 = companyActRepository
					.save(new CompanyAct("部门和下属部门的列表", "listOwnDepartmentAndChildren", 70, ActGroup.READ));
			CompanyAct save8 = companyActRepository.save(new CompanyAct("通知其他人", "noticeOther", 80, ActGroup.NOTICE));
			CompanyAct save9 = companyActRepository
					.save(new CompanyAct("通知给操作者", "noticeActUser", 90, ActGroup.NOTICE));
			CompanyAct save10 = companyActRepository
					.save(new CompanyAct("通知给展示人", "noticeShowUser", 100, ActGroup.NOTICE));

			companyStateRepository.save(new CompanyState("已创建", 0, "CREATED"));
			CompanyState companyState = new CompanyState("有效", 100, "ENABLED");
			companyState.setActs(Arrays.asList(save1, save2, save3, save4, save5, save6, save7).stream()
					.collect(Collectors.toSet()));
			companyStateRepository.save(companyState);
			companyStateRepository.save(new CompanyState("无效", 200, "DISABLED"));
			companyStateRepository.save(new CompanyState("已删除", 300, "DELETED"));

		}

	}

	@Override
	public LogRepository<CompanyLog> getLogRepository() {
		// TODO Auto-generated method stub
		return companyLogRepository;
	}

	@Override
	public AuditorEntityRepository<Company> getAuditorEntityRepository() {
		// TODO Auto-generated method stub
		return companyRepository;
	}

	@Override
	@PostConstruct
	public void injectLogRepository() {
		new Company().setLogRepository(companyLogRepository);

	}

	@Override
	public CompanyLog getLogInstance() {
		// TODO Auto-generated method stub
		return new CompanyLog();
	}

	@Override
	public ActRepository<CompanyAct> getActRepository() {
		// TODO Auto-generated method stub
		return companyActRepository;
	}

	@Override
	@PostConstruct
	public void injectEntityService() {
		new Company().setService(this);

	}

	/**
	 * 前端用户创建公司
	 * 
	 * @param company
	 */
	public Company doCreate(Company company, Integer userid) {

		Company findByNameCompany = companyRepository.findByCompanyName(company.getCompanyName());
		if (null != findByNameCompany) {
			throw new FailReturnObject(6423, "公司名称" + company.getCompanyName() + "已存在,请重新填写公司名称", Level.WARNING);
		}
		Company findByNumber = companyRepository.findByCompanyNumber(company.getCompanyNumber());
		if (null != findByNumber) {
			throw new FailReturnObject(6423, "工商号码" + company.getCompanyNumber() + "已存在，请重新填写工商号码", Level.WARNING);
		}
		company.setState(companyStateRepository.findByStateCode("CREATED"));
		//拼接地址全称
		StringBuffer addressFullName = new StringBuffer(company.getCompanyProvince());
		addressFullName.append(company.getCompanyCity());
		addressFullName.append(company.getCompanyDistrict());
		addressFullName.append(company.getCompanyAddress());
		company.setAddressFullName(addressFullName.toString());
		User user = userRepository.findByUserId(userid);
		if (user.getCompany() != null) {
			throw new FailReturnObject(9654, "您已经有所属公司", Level.WARNING);
		}
		company = companyRepository.save(company);
		user.setCompany(company);
		user.setCompanyRole("manager");
		userRepository.save(user);
		return company;
	}

	public Company changeCompanyInfo(Company company) {
		Company findByNameCompany = companyRepository.findByCompanyName(company.getCompanyName());
		if (null != findByNameCompany && !findByNameCompany.getId().equals(company.getId())) {
			throw new FailReturnObject(6423, "公司名称" + company.getCompanyName() + "已存在,请重新填写公司名称", Level.WARNING);
		}
		Company findByNumber = companyRepository.findByCompanyNumber(company.getCompanyNumber());
		if (null != findByNumber && !findByNumber.getId().equals(company.getId())) {
			throw new FailReturnObject(6423, "工商号码" + company.getCompanyNumber() + "已存在，请重新填写工商号码", Level.WARNING);
		}
		Company realCompany = companyRepository.findOne(company.getId());
		realCompany.setCompanyName(company.getCompanyName());
		realCompany.setCompanyNumber(company.getCompanyNumber());
		realCompany.setCompanyPersonName(company.getCompanyPersonName());
		realCompany.setCompanyProvince(company.getCompanyProvince());
		realCompany.setCompanyCity(company.getCompanyCity());
		realCompany.setCompanyDistrict(company.getCompanyDistrict());
		realCompany.setCompanyAddress(company.getCompanyAddress());
		//拼接地址全称
		StringBuffer addressFullName = new StringBuffer(company.getCompanyProvince());
		addressFullName.append(company.getCompanyCity());
		addressFullName.append(company.getCompanyDistrict());
		addressFullName.append(company.getCompanyAddress());
		realCompany.setAddressFullName(addressFullName.toString());
		return companyRepository.save(realCompany);
	}
	
    //根据条件动态查询并分页
    public Page<Company> getCompanyList(String stateCode,String companyName,String companyNumber,
    		                        String companyPersonName,Pageable pageable){ 
    	
	    GenericQueryExpSpecification<Company> queryExpression = new GenericQueryExpSpecification<Company>();
	    queryExpression.add(QueryExpSpecificationBuilder.eq("state.stateCode", stateCode, true));
	    queryExpression.add(QueryExpSpecificationBuilder.eq("companyName", companyName, true));
	    queryExpression.add(QueryExpSpecificationBuilder.eq("companyNumber", companyNumber, true));
		queryExpression.add(QueryExpSpecificationBuilder.eq("companyPersonName", companyPersonName, true));
		
		return companyRepository.findAll(queryExpression,pageable);	
    }

}
