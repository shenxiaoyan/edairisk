package com.liyang.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.liyang.Enum.OrderTypeEnum;

/**
 * 根据传入的字段生成订单号，并添加相关时间信息
 * 
 * @author nielijun 2017/12/19
 *
 */
public class ProductUtil {
	
     private static String modifyString() {
    	Date date = new Date();
		String format = new SimpleDateFormat("yyyyMMddHHmmss").format(date);
		String substring = format.substring(2);
		Random random = new Random ();
		int nextInt = random.nextInt(89);
		int i = nextInt + 10;
		return substring+i;
		
	}
	//获取具体产品名称特定编号
     private  static  String productNameString(String productName){
     	if ("网点随借随还".equals(productName)){
     		return "SJ";
		}else if("网点信用贷".equals(productName)){
			 return "XD";}
		else if("融资租赁".equals(productName)){
			return "RZ";}
		else if("面单白条".equals(productName)){
			return "BT";}
		return null;
}
	//获取具体产品类型特定编号
	private  static  String orderTypeString(Enum<OrderTypeEnum> orderType){
		if (orderType.equals(OrderTypeEnum.LOAN)){
			return "L";
		}else if(orderType.equals(OrderTypeEnum.ORDER)){
			return "O";}
		else if(orderType.equals(OrderTypeEnum.REPAYMENT)){
			return "R";}
		else if(orderType.equals(OrderTypeEnum.QUOTA)){
			return "T";}
		else if(orderType.equals(OrderTypeEnum.ABNORMALLOAN)){
			return "Y";}
     return null;
	}
	// 根据产品名称,产品类型生成单号
	public static String modify(String productName,Enum<OrderTypeEnum> orderType) {
		return orderTypeString(orderType)+productNameString(productName)+modifyString();
	}
//	public static void main(String[] args) {
//		String productName="面单白条";
//		Enum<OrderTypeEnum> orderType=OrderTypeEnum.REPAYMENT;
//		String modify = ProductUtil.modify(productName, orderType);
//		System.out.println(modify);
//	}
}