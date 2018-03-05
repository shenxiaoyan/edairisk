package com.liyang.domain.datetransform;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;


/**
 * @author 白羊
 * 转换前端传入的日期
 */
@Component
public class DateTransform {
	
	//创建开始时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") 
	private Date createDateTime;
	
	//创建结束时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") 
	private Date endDateTime;

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public Date getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}
	
}
