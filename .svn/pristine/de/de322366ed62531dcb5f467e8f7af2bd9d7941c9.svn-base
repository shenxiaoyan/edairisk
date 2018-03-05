package com.liyang.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liyang.domain.appmessage.AppMessage;
import com.liyang.domain.appmessage.AppMessageRepository;
import com.liyang.domain.creditrepayment.Creditrepayment;
import com.liyang.domain.loan.Loan;
import com.liyang.domain.ordermdbt.Ordermdbt;
import com.liyang.domain.orderwdsjsh.Orderwdsjsh;
import com.liyang.domain.product.Product;
import com.liyang.domain.quota.Quota;
import com.liyang.domain.user.User;
import com.liyang.util.DateUtil;
import com.liyang.util.FailReturnObject;
import com.liyang.util.XingeUtil;
import com.liyang.util.ReturnObject.Level;
import com.tencent.xinge.ClickAction;
import com.tencent.xinge.Message;
import com.tencent.xinge.MessageIOS;
import com.tencent.xinge.XingeApp;
import com.timevale.tgtext.text.pdf.PdfStructTreeController.returnType;

@Service
public class XinGePushService {

	@Value("${spring.xinge.android.ACCESS_ID}")
	private long android_accessId;
	@Value("${spring.xinge.android.SECRET_KEY}")
	private String android_secretKey;
	@Value("${spring.xinge.ios.ACCESS_ID}")
	private long ios_accessId;
	@Value("${spring.xinge.ios.SECRET_KEY}")
	private String ios_secretKey;

	@Autowired
	private AppMessageRepository appMessageRepository;

	public void pushAndroid(AppMessage appMessage) {
		XingeApp xinge = new XingeApp(android_accessId, android_secretKey);
		Message message = new Message();
		ClickAction clickAction = new ClickAction();
		clickAction.setActivity("com.sdot.yidai.ui.MainActivity");
		message.setType(Message.TYPE_NOTIFICATION);
		message.setTitle(appMessage.getTitle());
		message.setContent(appMessage.getContent());
		message.setAction(clickAction);

		JSONObject jsonObject = XingeUtil.demoPushSingleAccount(xinge, appMessage.getToUser().getUsername(), message);
		Integer code = jsonObject.getInt("ret_code");
		System.out.println(code);
		if (code.equals(0)) {
			System.out.println("安卓推送成功" + jsonObject);
		} else {
			System.out.println("安卓推送失败,失败原因:" + jsonObject + "::::等待20秒重新推送");
			try {
				Thread.sleep(2000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public void pushIOS(AppMessage appMessage) {
		XingeApp xinge = new XingeApp(ios_accessId, ios_secretKey);
		MessageIOS messageIOS = new MessageIOS();
		messageIOS.setType(MessageIOS.TYPE_APNS_NOTIFICATION);
		messageIOS.setAlert(appMessage.getTitle());
		messageIOS.setSound("beep.wav");
		messageIOS.setBadge(1);
		JSONObject jsonObject = XingeUtil.demoPushSingleAccountIOS(xinge, appMessage.getToUser().getUsername(),
				messageIOS);
		System.out.println(jsonObject.toString());
		Integer code = jsonObject.getInt("ret_code");
		System.out.println(code);
		if (code.equals(0)) {
			System.out.println("ios推送成功" + jsonObject);
		} else {
			System.out.println("ios推送失败,失败原因:" + jsonObject + "::::重新推送，等待20秒重新推送");
			try {
				Thread.sleep(2000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * app推送通知
	 * 
	 * @param user
	 * @param product
	 * @param title
	 * @param content
	 */
	public void pushAppMessage(User user, Product product, String title, String content, int topage) {
		AppMessage appMessage = new AppMessage(title, content, new Date(), user, product, topage);
		appMessage.setLabel(product.getLabel());
		appMessageRepository.save(appMessage);
		pushAndroid(appMessage);
		pushIOS(appMessage);
	}

	// 驿贷审核通过、获取content
	public String getYiDaiPassContent(String productName) {
		String content = "尊敬的用户您好，您申请的产品 ：" + productName + "已初步通过审核";
		return content;
	}

	// 驿贷审核失败、获取content
	public String getYiDaiFailedContent(String productName) {

		String content = "尊敬的用户您好，您申请的产品 ：" + productName + "未通过审核，请重新提交有关资料";
		return content;
	}

	// 背篼还款成功、获取content
	public String getRepaymentSuccessMessage(String message) {
		String payAmount = getValueFromJson(message, "payAmount").toString();
		String remainAmount = getValueFromJson(message, "remainAmount").toString();
		String content = "尊敬的用户您好，您本次还款操作成功   \n本次还款金额：" + payAmount + "元    \n剩余应还金额：" + remainAmount + "元";
		return content;
	}

	// 背篼还款失败、获取content
	public String getRepaymentFailedMessage(String message) {
		String content = "尊敬的用户您好，您本次还款操作失败  \n请联系客服人员处理";
		return content;
	}

	// 背篼授信失败、获取content
	public String getShouXinFailedMessage(String message) {
		String content = "尊敬的用户您好，您申请的产品：随借随还   授信失败，请重新提交资料审核";
		return content;
	}

	// 背篼授信成功、 获取content
	public String getShouXinSuccessMessage(String message) {
		String amount = getValueFromJson(message, "amount").toString();
		String content = "尊敬的用户您好，您申请的产品：随借随还   授信成功   \n授信额度：" + amount + "元";
		return content;
	}

	// mdbt通过授信、获取content
	public String getMdbtPassMessage(Ordermdbt ordermdbt) {
		String content = "尊敬的用户您好，您申请的产品：网点面单白条   授信成功   \n授信额度：" + ordermdbt.getApplyAmount() + "元";
		return content;
	}

	// MDBT借款通过，获取content
	public String getMdbtLoanPassMessage(Loan loan) {
		String content = "尊敬的用户您好，您申请的借款" + loan.getPrincipal().setScale(2) + "元已放款";
		return content;
	}

	// MDBT还款成功
	public String getMDBTRepaymentPassMessage(Creditrepayment creditrepayment) {
		String content = "尊敬的用户您好，您于" + DateUtil.getStrByDate(creditrepayment.getCreatedAt(), "yyyy-MM-dd HH:mm:ss")
				+ "发起的还款操作成功  \n还款金额：" + creditrepayment.getPayAmount().setScale(2) + "元";
		return content;
	}

	// MDBT还款失败
	public String getMDBTRepaymentFailMessage(Creditrepayment creditrepayment) {
		String content = "尊敬的用户您好，您于" + DateUtil.getStrByDate(creditrepayment.getCreatedAt(), "yyyy-MM-dd HH:mm:ss")
				+ "发起的还款操作失败  \n请核对账单后重新提交还款申请";
		return content;
	}

	// 提额审核通过
	public String getQuotaApplicationPassMessage(Quota quota) {
		String content = "尊敬的用户您好，您的提额申请 已通过 \n提额后的总额度为：" + quota.getQuotaAmount().setScale(2) + "元";
		return content;
	}

	// 提额审核未通过
	public String getQuotaApplicationNotPassMessage(String auditRemark) {
		String content = "尊敬的用户您好，您的提额申请  未通过  \n未通过原因：" + auditRemark;
		return content;
	}

	// 传入json字符串，获取相关key的value
	public Object getValueFromJson(String originalData, String key) {
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> map = null;
		try {
			map = objectMapper.readValue(originalData, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new FailReturnObject(6659, e.getMessage(), Level.FATAL);
		}
		return map.get(key);
	}

}
