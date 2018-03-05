package com.liyang.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.liyang.domain.creditcard.Creditcard;
import com.liyang.domain.creditcard.CreditcardRepository;
import com.liyang.domain.ordermdbt.Ordermdbt;
import com.liyang.domain.ordermdbt.OrdermdbtRepository;
import com.liyang.domain.orderrzzl.Orderrzzl;
import com.liyang.domain.orderrzzl.OrderrzzlRepository;
import com.liyang.domain.orderwdsjsh.Orderwdsjsh;
import com.liyang.domain.orderwdsjsh.OrderwdsjshRepository;
import com.liyang.domain.orderwdxyd.Orderwdxyd;
import com.liyang.domain.orderwdxyd.OrderwdxydRepository;
import com.liyang.domain.person.PersonRepository;
import com.liyang.domain.user.UserRepository;

@Service
public class UserInfoService {

	@Autowired
	private OrderwdsjshRepository orderwdsjshRepository;

	@Autowired
	private OrderrzzlRepository orderrzzlRepository;

	@Autowired
	private OrderwdxydRepository orderwdxydRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private CreditcardRepository creditcardRepository;
	
	@Autowired
	private OrdermdbtRepository ordermdbtRepository;
	
	/**
	 * 根据userid得到sjsh产品使用信息
	 * @param userid
	 * @return
	 */
	public Map<String, Object> getSjshAmount(Integer userid) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Orderwdsjsh> orderwdsjshes = orderwdsjshRepository.findByCreateBy(userid);
		if (orderwdsjshes == null || orderwdsjshes.size() == 0) {
			map.put("have", "none");  //未使用为none
		} else {
			Orderwdsjsh orderwdsjsh = orderwdsjshes.get(0);
			map.put("have", "ok");   //使用为ok
			map.put("state", orderwdsjsh.getState().getStateCode());
			if ("ENABLED".equals(orderwdsjsh.getState().getStateCode())) {
				String applyMobile = orderwdsjsh.getApplyMobile();
				Integer productid = orderwdsjsh.getProduct().getId();
				Creditcard creditcard = creditcardRepository.findByTelephoneAndProduct(applyMobile, productid);
				map.put("total", creditcard.getCreditGrant());
				map.put("remain", creditcard.getCreditBalance());
				map.put("id", creditcard.getId());
			}
		}
		return map;
	}
	
	/**
	 * 根据userid查询rzzl产品使用信息     多次申请时需要做出相应的修改
	 * @param userid
	 * @return
	 */
	public Map<String, Object> getRzzlAmount(Integer userid) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Orderrzzl> orderrzzls = orderrzzlRepository.findByCreateBy(userid);
		if (orderrzzls == null || orderrzzls.size() == 0) {
			map.put("have", "none");
		} else {
			Orderrzzl orderrzzl = orderrzzls.get(0);
			map.put("have", "ok");
			map.put("state", orderrzzl.getState().getStateCode());
			if ("ENABLED".equals(orderrzzl.getState().getStateCode())) {
				String applyMobile = orderrzzl.getApplyMobile();
				Creditcard creditcard = creditcardRepository.findByCreditcardIdentity(applyMobile);
				map.put("total", creditcard.getCreditGrant());
				map.put("remain", creditcard.getCreditBalance());
				map.put("id", creditcard.getId());
			}
		}
		return map;
	}
	
	/**
	 * 根据userid查询wdxyd产品使用信息
	 * @param userid
	 * @return
	 */
	public Map<String, Object> getWdxydAmount(Integer userid) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Orderwdxyd> orderwdxyds = orderwdxydRepository.findByCreateBy(userid);
		if (orderwdxyds == null || orderwdxyds.size() == 0) {
			map.put("have", "none");
		} else {
			Orderwdxyd orderwdxyd = orderwdxyds.get(0);
			map.put("have", "ok");
			map.put("state", orderwdxyd.getState().getStateCode());
			if ("ENABLED".equals(orderwdxyd.getState().getStateCode())) {
				String applyMobile = orderwdxyd.getApplyMobile();
				Creditcard creditcard = creditcardRepository.findByCreditcardIdentity(applyMobile);
				map.put("total", creditcard.getCreditGrant());
				map.put("remain", creditcard.getCreditBalance());
				map.put("id", creditcard.getId());
			}
		}
		return map;
	}
	
	
	public Map<String, Object> getMdbtAmount(Integer userid){
		Map<String, Object> map = new HashMap<String,Object>();
		List<Ordermdbt> ordermdbts = ordermdbtRepository.findByCreateBy(userid);
		if(ordermdbts == null || ordermdbts.size() == 0){
			map.put("have", "none");
		}else{
			Ordermdbt ordermdbt = ordermdbts.get(0);
			map.put("have", "ok");
			map.put("state", ordermdbt.getState().getStateCode());
			if("ENABLED".equals(ordermdbt.getState().getStateCode())){
				Creditcard creditcard = creditcardRepository.findByCompany(ordermdbt.getCompany().getId());
				map.put("total", creditcard.getCreditGrant());
				map.put("remain", creditcard.getCreditBalance());
				map.put("id", creditcard.getId());
			}
		}
		return map;
	}
	
	
}
