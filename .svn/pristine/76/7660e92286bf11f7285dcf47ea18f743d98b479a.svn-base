package com.liyang.util;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class FailReturnObject extends RuntimeException implements ReturnObject {

	private String ErrorInfo;
	private Integer ErrorCode;
	private Level level = Level.INFO;
	private Integer msgTime;
	
	

	
	public FailReturnObject(Integer errorCode ,String errorInfo, Level level) {
		super(errorCode + " : "+ errorInfo);
		ErrorInfo = errorInfo;
		ErrorCode = errorCode;
		this.level = level;
	
	}

	public FailReturnObject(Integer errorCode ,Object errorInfo,Level level) {
		super(errorCode + " : "+ errorInfo.toString());
		ErrorInfo = errorInfo.toString();
		ErrorCode = errorCode;
		this.level = level;
	
	}
	
	public FailReturnObject(ExceptionResultEnum eResultEnum){
		super(eResultEnum.getCode() + " : " + eResultEnum.getMessage());
		ErrorInfo=eResultEnum.getMessage();
		ErrorCode=eResultEnum.getCode();
	}
	
	public FailReturnObject(ExceptionResultEnum eResultEnum ,Level level){
		super(eResultEnum.getCode() + " : " + eResultEnum.getMessage());
		ErrorInfo=eResultEnum.getMessage();
		ErrorCode=eResultEnum.getCode();
		this.level=level;
	}
	
	public Integer getMsgTime() {
		return msgTime;
	}

	public void setErrorInfo(String errorInfo) {
		ErrorInfo = errorInfo;
	}

	public void setErrorCode(Integer errorCode) {
		ErrorCode = errorCode;
	}

	public void setMsgTime(Integer msgTime) {
		this.msgTime = msgTime;
	}

	@Override
	public String getActionStatus() {
		// TODO Auto-generated method stub
		return "FAIL";
	}

	public String getErrorInfo() {
		return ErrorInfo;
	}

	public Integer getErrorCode() {
		return ErrorCode;
	}



	@Override
	public void setActionStatus(String s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Level getLevel() {
		// TODO Auto-generated method stub
		return level;
	}

	@Override
	public void setLevel(Level level) {
		this.level = level;
		
	}



	

}
