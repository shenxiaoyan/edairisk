package com.liyang.util;

public enum ExceptionResultEnum {

	unknow_error(-1, "unknow_error"), 
	sussess(0, "ok"),
	error_1(1315,"状态不能为空"),
	error_2(1313,"json解析对象拷贝错误1"),
	error_3(1314,"json解析对象拷贝错误2"),
	error_4(199, "前一个申请还在审核中！"),
	error_5(499, "没有创建人"),
	error_6(4561, "身份证号已经存在"),
	;

 
	private Integer code;
	private String message;

	private ExceptionResultEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
