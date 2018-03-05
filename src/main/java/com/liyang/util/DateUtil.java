package com.liyang.util;

import static org.mockito.Matchers.anyString;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class DateUtil {

	/**
	 * 根据字符串得到日期
	 * 
	 * @param str
	 * @param format
	 * @return
	 */
	public static Date getDateByStr(String str, String format) {
		Date date = null;
		SimpleDateFormat s = new SimpleDateFormat(format);
		try {
			date = s.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;

	}

	/**
	 * 根据日期得到字符串
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getStrByDate(Date date, String format) {
		String str = null;
		SimpleDateFormat s = new SimpleDateFormat(format);
		str = s.format(date);
		return str;
	}

	
	

}
