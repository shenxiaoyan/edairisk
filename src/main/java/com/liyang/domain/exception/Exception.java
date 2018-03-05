package com.liyang.domain.exception;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.liyang.annotation.Info;
import com.liyang.domain.base.BaseEntity;
import com.liyang.util.ReturnObject;
@Entity
@Table(name = "exception")
public class Exception extends BaseEntity implements ReturnObject {
	
	@Column(name="action_status")
	private String actionStatus;
	@Lob
	@Column(name="error_info")
	private String errorInfo;
	@Column(name="error_code")
	private Integer errorCode;
	
	@Enumerated(EnumType.STRING)
	@Column(name="level")
	@Info(label="异常等级",placeholder="",tip="",help="",secret="")		
	private Level level;
	
	public String getActionStatus() {
		return actionStatus;
	}
	public void setActionStatus(String actionStatus) {
		this.actionStatus = actionStatus;
	}
	public String getErrorInfo() {
		return errorInfo;
	}
	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	public Integer getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	@Override
	@Transient
	public Integer getMsgTime() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setMsgTime(Integer i) {
		// TODO Auto-generated method stub
		
	}
	public Level getLevel() {
		return level;
	}
	public void setLevel(Level level) {
		this.level = level;
	}


}
