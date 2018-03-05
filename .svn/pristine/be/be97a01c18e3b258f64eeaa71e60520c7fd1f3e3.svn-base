package com.liyang.domain.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Null;

import com.liyang.domain.person.Person;
import com.liyang.util.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.annotation.Info.FLAG;
import com.liyang.annotation.Infos;
import com.liyang.domain.base.AbstractWorkflowEntity;
import com.liyang.domain.base.AbstractWorkflowLog;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.company.Company;
import com.liyang.domain.department.Department;
import com.liyang.domain.role.Role;
import com.liyang.service.AbstractAuditorService;
import com.liyang.service.AbstractWorkflowService;

@Entity
@Table(name = "user")
@Info(label="用户")
public class User extends AbstractWorkflowEntity<UserWorkflow, UserState, UserAct, UserLog, UserFile> implements UserDetails{

	@Transient
	private static LogRepository logRepository;
	
	@Transient
	private static AbstractWorkflowService service;
	
	@Column(unique=true)
	private String username;
	private String password;
	@Transient
	private String passwordAgain;
	@Transient
	private String newPassword;
	private Integer money;
	
	@Info(label="昵称",placeholder="微信来的昵称",tip="昵称",help="",secret="")
	private String nickname;
	
	@Info(label="真实姓名",placeholder="",tip="",help="",secret="")
	private String realname;
	
	@Info(label="性别",placeholder="",tip="",help="",secret="")
	private Integer sex;
	
	@Info(label="省份",placeholder="",tip="",help="",secret="")
	private String province;
	
	@Info(label="城市",placeholder="",tip="",help="",secret="")
	private String city;

	@Info(label="国家",placeholder="",tip="",help="",secret="")
	private String country;

	@Info(label="头像URL",placeholder="",tip="",help="",secret="")
	private String headimgurl;

	@Column(unique=true)
	@Info(label="OpenID",placeholder="",tip="",help="",secret="")
	private String openid;

	@Column(length = 50)
	@Info(label="微信unoinid",placeholder="",tip="多微信公从号唯一标识",help="",secret="")
	private String unionid;

	@Column(length = 500)
	@Info(label="TIM登录标识",placeholder="",tip="TIM登录标识",help="",secret="")
	private String sig;
	
	@Column(name = "sms_noticer")
	@Info(label="是否为后台短信接收人员",placeholder="",tip="",help="",secret="")
	private Integer smsNoticer;
	
	@ManyToOne
	@JoinColumn(name = "company_id")
	@Info(label="所属公司",placeholder="",tip="",help="",secret="")
	private Company company;
	
	@Column(name = "company_role_name")
	@Info(label="公司角色",placeholder="",tip="",help="",secret="")
	private String  companyRole;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "department_id")
	@Info(label="所属部门",placeholder="",tip="所属部门",help="",secret="")
	private Department department;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "role_id")
	@Info(label="所属角色",placeholder="",tip="所属角色",help="",secret="")
	private Role role;

	@JsonIgnore
	@Transient
	@Info(label="子部门",placeholder="",tip="子部门",help="",secret="")
	public List<Department> childrenDepartments;
	

	
	@Info(label="纬度",placeholder="",tip="",help="",secret="")	
	private Double latitude;
		
	@Info(label="经度",placeholder="",tip="",help="",secret="")
	private Double longitude;

//	@Column(name = "service_province")
//	@Info(label = "服务的省",placeholder = "",tip = "服务的省",secret = "")
//	private String serviceProvince;
//	@Column(name = "service_city")
//	@Info(label = "服务的市",placeholder = "",tip = "服务的市",secret = "")
//	private String serviceCity;
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@ManyToOne
	private Person person;

	@OneToOne(mappedBy = "user")
	@Info(label = "E签账户",placeholder = "",tip = "E签账户",help = "",secret = "")
	private UserESign userESign;

	public UserESign getUserESign() {
		return userESign;
	}

	public void setUserESign(UserESign userESign) {
		this.userESign = userESign;
	}
	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	
	
	public Integer getSmsNoticer() {
		return smsNoticer;
	}

	public void setSmsNoticer(Integer smsNoticer) {
		this.smsNoticer = smsNoticer;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	
	public String getCompanyRole() {
		return companyRole;
	}

	public void setCompanyRole(String companyRole) {
		this.companyRole = companyRole;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	

	public String getNickname() {
		return nickname;
	}


	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.liyang.domain.UserProjection#getRole()
	 */
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see com.liyang.domain.UserProjection#getSig()
	 */
	public String getSig() {
		return sig;
	}

	public void setSig(String sig) {
		this.sig = sig;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.liyang.domain.UserProjection#getSex()
	 */
	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.liyang.domain.UserProjection#getProvince()
	 */
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.liyang.domain.UserProjection#getCity()
	 */
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.liyang.domain.UserProjection#getCountry()
	 */
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.liyang.domain.UserProjection#getHeadimgurl()
	 */
	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.liyang.domain.UserProjection#getOpenid()
	 */
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.liyang.domain.UserProjection#getUnionid()
	 */
	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

//	public String getServiceProvince() {
//		return serviceProvince;
//	}
//
//	public void setServiceProvince(String serviceProvince) {
//		this.serviceProvince = serviceProvince;
//	}
//
//	public String getServiceCity() {
//		return serviceCity;
//	}
//
//	public void setServiceCity(String serviceCity) {
//		this.serviceCity = serviceCity;
//	}

	/*
         * (non-Javadoc)
         *
         * @see com.liyang.domain.UserProjection#getDepartment()
         */
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@Override
	@JsonIgnore
	@Transient
	public UserLog getLogInstance() {
		// TODO Auto-generated method stub
		return new UserLog();
	}

	@Override
	@JsonIgnore
	@Transient
	public LogRepository getLogRepository() {
		// TODO Auto-generated method stub
		return logRepository;
	}

	@Override
	public void setLogRepository(LogRepository logRepository) {
		User.logRepository = logRepository;
	}

	@Override
	@JsonIgnore
	@Transient
	public AbstractAuditorService getService() {
		// TODO Auto-generated method stub
		return service;
	}

	@Override
	public void setService(AbstractAuditorService service) {
		User.service = (AbstractWorkflowService) service;
		
	}


	public void setChildrenDepartments(List<Department> ownAndChildrenDepartments) {
		this.childrenDepartments = ownAndChildrenDepartments;
		
	}
	
	/**
	 * 用户是否处于禁用状态
	 * @return
	 */
	public boolean isUserStateDisabled(){
//		return "DISABLED".equals(getState().getStateCode());
		return  (getState()!=null)&&("DISABLED".equals(getState().getStateCode()));
	}

	/**
	 * 用户是否处于已删除状态
	 * @return
	 */
	public boolean isUserStateDeleted(){
//		return "DELETED".equals(getState().getStateCode());
		return getState()!=null&&"DELETED".equals(getState().getStateCode());
	}
	
	/**
	 * 用户是否处于审核中状态
	 * @return
	 */
	public boolean isUserStateApplied(){
//		return "APPLIED".equals(getState().getStateCode());
		return getState()!=null&&"APPLIED".equals(getState().getStateCode());
	}
	public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
		if(password==null||password.length()<6){
			throw new FailReturnObject(1557,"密码长度不能小于6位数", ReturnObject.Level.INFO);
		}
		this.password=MD5Util.encode(password);
//        this.password = password;
    }


	@Override
	@JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<>();
        Role role = this.getRole();
        auths.add(new SimpleGrantedAuthority(role.getRoleCode().toString()));
        return auths;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public String getPasswordAgain() {
		return passwordAgain;
	}

	public void setPasswordAgain(String passwordAgain) {
		this.passwordAgain = passwordAgain;
	}

	@Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isEnabled() {
        return true;
    }

}