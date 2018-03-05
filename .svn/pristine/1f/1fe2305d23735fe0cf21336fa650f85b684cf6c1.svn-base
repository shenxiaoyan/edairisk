package com.liyang.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReturnObjectImpl implements ReturnObject {

	@JsonProperty("ActionStatus")
	private String actionStatus;
	@JsonProperty("ErrorInfo")
	private String errorInfo;
	@JsonProperty("ErrorCode")
	private Integer errorCode;
	@JsonProperty("MsgTime")
	private Integer msgTime;
	private Level level=Level.INFO;
	
	

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



	public Integer getMsgTime() {
		return msgTime;
	}



	public void setMsgTime(Integer msgTime) {
		this.msgTime = msgTime;
	}



	@Override
	public String toString() {
//		return "ReturnObjectImpl [actionStatus=" + actionStatus + ", errorInfo=" + errorInfo + ", errorCode="
//				+ errorCode + ", msgTime=" + msgTime + "]";
		StringBuilder sb = new StringBuilder();
		return sb.append("ReturnObjectImpl").append("[actionStatus=") 
		.append(actionStatus).append(",").append("errorInfo=") .append(errorInfo)
		.append(",").append("errorCode=") .append(errorCode) 
		.append(",").append("msgTime=") .append(msgTime).append("]").toString();
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
