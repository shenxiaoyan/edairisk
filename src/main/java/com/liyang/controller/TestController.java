package com.liyang.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserRepository;
import com.liyang.service.AccountService;
import com.liyang.service.TIMService;
import com.liyang.util.TIMSignature;
/**
 * 替换TIM  user  sign
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TIMService timService;
	@Autowired
	private AccountService accountService;
	
	
	@RequestMapping(value = "/qianyi",method = RequestMethod.GET)
    private Map<String, Object> testQianYi() {
        List<User> users = userRepository.findAll();
        HashMap<String, Object> hashMap = new HashMap<>();
        for (User entity : users) {
            try {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                entity.setSig(TIMSignature.generate(entity.getId().toString()).urlSig);
                userRepository.save(entity);

                hashMap.put(entity.getId().toString(), entity.getSig());

                timService.addUser(entity.getId().toString(), entity.getUsername(), "");
                //创建默认account
                //begin 新建完user创建默认的account --Jax
                accountService.createdDefaultAccountByUser(entity);
                //end
            } catch (Exception e) {
            }
        }
        return hashMap;
    }

}
