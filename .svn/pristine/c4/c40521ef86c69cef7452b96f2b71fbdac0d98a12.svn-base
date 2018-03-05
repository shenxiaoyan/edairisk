package com.liyang.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.apache.pdfbox.contentstream.operator.state.SetRenderingIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jpa.query.expression.QueryExpSpecificationBuilder;
import com.jpa.query.expression.generic.GenericQueryExpSpecification;
import com.liyang.Enum.OrderTypeEnum;
import com.liyang.aliyunsms.AliyunSmsService;
import com.liyang.domain.creditcard.Creditcard;
import com.liyang.domain.creditcard.CreditcardRepository;
import com.liyang.domain.datetransform.DateTransform;
import com.liyang.domain.ordermdbt.Ordermdbt;
import com.liyang.domain.ordermdbt.OrdermdbtRepository;
import com.liyang.domain.orderwdsjsh.Orderwdsjsh;
import com.liyang.domain.orderwdsjsh.OrderwdsjshRepository;
import com.liyang.domain.quota.Quota;
import com.liyang.domain.quota.QuotaRepository;
import com.liyang.util.FailReturnObject;
import com.liyang.util.ProductUtil;
import com.liyang.util.ReturnObject;


@Service
public class QuotaService {
	
	@Autowired
	OrdermdbtRepository ordermdbtRepository;
	@Autowired
	OrderwdsjshRepository orderwdsjshRepository;
	@Autowired
	QuotaRepository quotaRepository;
	@Autowired
	CreditcardRepository creditcardRepository;
	@Autowired
	CreditcardRepository CreditcardRepository;
	@Autowired
	private XinGePushService xinGePushService;
    @Autowired
    WlqzWechatPubService wechatPubService;
    @Value("${spring.wlqz.wechat.OPEN_ACC_APPLY}")
    private String OPEN_ACC_APPLY;
	@Autowired
	AliyunSmsService aliyunSmsService;
    @Autowired
    DateTransform dateTransform;
	
	/**
	 * 调额通过
	 */
	@Transactional
	public void quotaApplicationAdotp(Integer quotaid){
		
		Quota quota = quotaRepository.findOne(quotaid);
		Creditcard creditcard = quota.getCreditcard();
		if(!"ENABLED".equals(creditcard.getState().getStateCode())){ 
			throw new FailReturnObject(1319, "此卡为无效状态", ReturnObject.Level.ERROR);
		}  
		//更新信用卡里的剩余额度
		BigDecimal creditBalance = quota.getQuotaAmount().subtract(creditcard.getCreditGrant());
		creditcard.setCreditBalance(creditcard.getCreditBalance().add(creditBalance));
		//更新信用卡里的总额度		
		creditcard.setCreditGrant(quota.getQuotaAmount());
		creditcardRepository.save(creditcard);    					
		//修改调额表状态为有效
		quota.setStateCode("ENABLED");
		quotaRepository.save(quota);
		String content = xinGePushService.getQuotaApplicationPassMessage(quota);
		xinGePushService.pushAppMessage(quota.getApplicationUser(), quota.getProduct(), "提额进度通知", content, 2);
		wechatPubService.pushAddQuotPassMessage(quota.getApplicationUser(),quota.getQuotaAmount());

	}
	
    //驿贷未通过
	@Transactional
    public void quotaApplicationNotAdotp(Integer quotaid,String auditRemark) {    	
		Quota quota = quotaRepository.findOne(quotaid);
		quota.setAuditRemark(auditRemark);
		quota.setStateCode("DENIED");
		quotaRepository.save(quota);
        String content = xinGePushService.getQuotaApplicationNotPassMessage(quota.getAuditRemark());
        xinGePushService.pushAppMessage(quota.getApplicationUser(), quota.getProduct(), "审核进度通知", content,2);       
    }
	
	
	/**
	 * 提交调额申请单
	 */
	@Transactional
	public void saveQuoteApplication(Integer creditcardId,String quotaAmount){
		
		Creditcard creditcard = CreditcardRepository.findOne(creditcardId);
		if(!"ENABLED".equals(creditcard.getState().getStateCode())){ 
			throw new FailReturnObject(1320, "此卡为无效状态", ReturnObject.Level.ERROR);
		}		
		BigDecimal amount = new BigDecimal(quotaAmount);
		BigDecimal grant = creditcard.getCreditGrant();
		if(amount.compareTo(grant) < 1){
			throw new FailReturnObject(1321, "调额不能小于当前额度", ReturnObject.Level.ERROR);
		}
		//identity是手机号
		String identity = creditcard.getCreditcardIdentity();	
		Integer productid = creditcard.getProduct().getId();
		Quota quota = new Quota();
		//网点随借谁还提额
		/*if(productid == 1){
			Orderwdsjsh sjsh = orderwdsjshRepository.getByApplyIdentityNoAndProductid(identity,productid);
			if(sjsh == null){
				throw new FailReturnObject(1322, "网点随借随还为空！", ReturnObject.Level.ERROR);
			}
			//单号
			String quotaNumber = ProductUtil.modify("网点随借随还", OrderTypeEnum.QUOTA);
			quota.setQuotaNumber(quotaNumber);
			quota.setCreditcard(creditcard);
			quota.setProduct(sjsh.getProduct());
			quota.setProductRecordId(sjsh.getId());
			quota.setProductLabel(sjsh.getProduct().getLabel());
			quota.setApplicationUser(sjsh.getUser());
			quota.setApplicationTime(new Date());
			quota.setQuotaAmount(amount);
			quota.setCurrentAmount(creditcard.getCreditGrant());
			quota.setStateCode("CREATED");	
			quota.setApplyEnterpriseName(sjsh.getApplyEnterpriseName());
			quota.setApplyDayPatchExpress(sjsh.getApplyDayPatchExpress());
			quota.setApplyDayPickExpress(sjsh.getApplyDayPickExpress());
			quota.setPerson(sjsh.getPerson());
			quota.setApplyMobile(sjsh.getApplyMobile());
			quotaRepository.save(quota);
		}*/
		//面单白条 
		if(productid == 4){
			Ordermdbt ord = ordermdbtRepository.getByApplyIdentityNoAndProductid(identity,productid);
			if(ord == null){
				throw new FailReturnObject(1323, "面单白条为空！", ReturnObject.Level.ERROR);
			}
			//单号
			String quotaNumber = ProductUtil.modify("面单白条", OrderTypeEnum.QUOTA);
			quota.setQuotaNumber(quotaNumber);
			quota.setCreditcard(creditcard);
			quota.setProduct(ord.getProduct());
			quota.setProductRecordId(ord.getId());
			quota.setProductLabel(ord.getProduct().getLabel());
			quota.setApplicationUser(ord.getUser());
			quota.setApplicationTime(new Date());
			quota.setQuotaAmount(amount);
			quota.setCurrentAmount(creditcard.getCreditGrant());
			quota.setStateCode("CREATED");	
			quota.setApplyEnterpriseName(ord.getApplyEnterpriseName());
			quota.setApplyDayPatchExpress(ord.getApplyDayPatchExpress());
			quota.setApplyDayPickExpress(ord.getApplyDayPickExpress());
			quota.setPerson(ord.getPerson());
			quota.setApplyMobile(ord.getApplyMobile());
			quotaRepository.save(quota);
			//通知业务员
			if(ord.getServiceUser() != null){
				if(ord.getServiceUser().getUsername() == null || !Pattern.matches("^1[\\d]{10}$", ord.getServiceUser().getUsername())){
					throw new FailReturnObject(1328, "请输入正确的手机号", ReturnObject.Level.INFO);
				}
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("orderNo", ord.getOrderNo());
				param.put("orderType", "提额");
				param.put("label", "面单白条");
				aliyunSmsService.sendMes(ord.getServiceUser().getUsername(), param, "SMS_119085953");
			}
		}else{
			throw new FailReturnObject(1324, "目前只开通了面单白条提额！", ReturnObject.Level.ERROR);
		}
	}
	
		
    //根据条件动态查询Quota并分页
    public Page<Quota> getQuotaList(String stateCode,String personName,Quota quota,DateTransform dateTransform,Pageable pageable){ 
    	
	    GenericQueryExpSpecification<Quota> queryExpression = new GenericQueryExpSpecification<Quota>();
	    queryExpression.add(QueryExpSpecificationBuilder.eq("stateCode", stateCode, true));
	    queryExpression.add(QueryExpSpecificationBuilder.eq("person.name", personName, true));
	    queryExpression.add(QueryExpSpecificationBuilder.eq("applyEnterpriseName", quota.getApplyEnterpriseName(), true));
		queryExpression.add(QueryExpSpecificationBuilder.eq("applyMobile", quota.getApplyMobile(), true));
		queryExpression.add(QueryExpSpecificationBuilder.eq("quotaNumber", quota.getQuotaNumber(), true));
    	queryExpression.add(QueryExpSpecificationBuilder.gt("applicationTime", dateTransform.getCreateDateTime(), true));
    	queryExpression.add(QueryExpSpecificationBuilder.lt("applicationTime", dateTransform.getEndDateTime(), true));
		return quotaRepository.findAll(queryExpression,pageable);	
    }

    //查询某一客户某一自然月是否已经申请过提额
    public int queryQuotaRecord(String creditcardId){
    	int result = 0;
    	Integer id = Integer.valueOf(creditcardId);
    	List<Quota> list = quotaRepository.findByCreditcardAndApplycationTime(id);
    	if(list.size() > 0){
    		result = 1;
    		return result;
    	}
    	return result;
    }
    
}
