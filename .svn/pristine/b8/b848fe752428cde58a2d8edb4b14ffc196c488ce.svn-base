package com.liyang.util;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseBody {

	/*
	 * 返回状态码 0是正确  
	 */
	@JsonProperty("ActionStatus")
	private String ActionStatus;
	@JsonProperty("ErrorCode")
	private Integer ErrorCode;
	@JsonProperty("ErrorInfo")
	private String ErrorInfo;

	private Object data;

	@JsonProperty(value = "ActionStatus")
	public String getActionStatus() {
		return ActionStatus;
	}

	public void setActionStatus(String ActionStatus) {
		this.ActionStatus = ActionStatus;
	}

	@JsonProperty(value = "ErrorCode")
	public Integer getErrorCode() {
		return ErrorCode;
	}

	public void setErrorCode(Integer ErrorCode) {
		this.ErrorCode = ErrorCode;
	}

	@JsonProperty(value = "ErrorInfo")
	public String getErrorInfo() {
		return ErrorInfo;
	}

	public void setErrorInfo(String ErrorInfo) {
		this.ErrorInfo = ErrorInfo;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public ResponseBody(Integer ErrorCode, String ErrorInfo) {
		super();
		this.ErrorCode = ErrorCode;
		this.ErrorInfo = ErrorInfo;
	}

	public ResponseBody() {
	}

	public ResponseBody(Object data) {
		super();
		this.ErrorCode = 0;
		this.data = data;
	}

	@Override
	public String toString() {
		return "ResponseBody [ActionStatus=" + ActionStatus + ", ErrorCode=" + ErrorCode + ", ErrorInfo=" + ErrorInfo
				+ ", data=" + data + "]";
	}

}
