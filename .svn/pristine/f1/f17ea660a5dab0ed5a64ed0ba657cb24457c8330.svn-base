package com.liyang.domain.riskmodel;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.boot.jaxb.hbm.spi.PluralAttributeInfoPrimitiveArrayAdapter;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.AbstractAuditorLog;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.department.Department;
import com.liyang.domain.menu.Menu;
import com.liyang.service.AbstractAuditorService;
import com.liyang.service.MenuService;
import com.liyang.util.CommonUtil;
import com.liyang.util.TreeNode;
import com.liyang.util.TreeNodeImpl;

@Entity
@Table(name = "riskmodel")
public class Riskmodel extends AbstractAuditorEntity<RiskmodelState, RiskmodelAct,RiskmodelLog>{


	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = 1L;


	
	@Transient
	private static LogRepository logRepository;
	
	@Transient
	private static AbstractAuditorService service;
	@Lob
	private String description;	
	@Lob
	private String comment;
	@Column(name="fail_boundary")
	private String failBoundary;
	private Integer level1;
	private String boundary2;
	private Integer level3;
	private String boundary4;
	private Integer level5;
	private String boundary6;
	private Integer level7;
	private String boundary8;
	private Integer level9;
	@Column(name="success_boundary")
	private String successBoundary;
	@Override
	@JsonIgnore
	@Transient
	public RiskmodelLog getLogInstance() {
		// TODO Auto-generated method stub
		return new RiskmodelLog();
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getFailBoundary() {
		return failBoundary;
	}

	public void setFailBoundary(String failBoundary) {
		this.failBoundary = failBoundary;
	}

	public Integer getLevel1() {
		return level1;
	}

	public void setLevel1(Integer level1) {
		this.level1 = level1;
	}

	public String getBoundary2() {
		return boundary2;
	}

	public void setBoundary2(String boundary2) {
		this.boundary2 = boundary2;
	}

	public Integer getLevel3() {
		return level3;
	}

	public void setLevel3(Integer level3) {
		this.level3 = level3;
	}

	public String getBoundary4() {
		return boundary4;
	}

	public void setBoundary4(String boundary4) {
		this.boundary4 = boundary4;
	}

	public Integer getLevel5() {
		return level5;
	}

	public void setLevel5(Integer level5) {
		this.level5 = level5;
	}

	public String getBoundary6() {
		return boundary6;
	}

	public void setBoundary6(String boundary6) {
		this.boundary6 = boundary6;
	}

	public Integer getLevel7() {
		return level7;
	}

	public void setLevel7(Integer level7) {
		this.level7 = level7;
	}

	public String getBoundary8() {
		return boundary8;
	}

	public void setBoundary8(String boundary8) {
		this.boundary8 = boundary8;
	}

	public Integer getLevel9() {
		return level9;
	}

	public void setLevel9(Integer level9) {
		this.level9 = level9;
	}

	public String getSuccessBoundary() {
		return successBoundary;
	}

	public void setSuccessBoundary(String successBoundary) {
		this.successBoundary = successBoundary;
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
		Riskmodel.logRepository = logRepository;
		
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
		Riskmodel.service = service;
		
	}



	

}