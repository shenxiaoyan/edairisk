package com.liyang.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liyang.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.liyang.domain.user.User;
import com.liyang.util.CommonUtil;

@Controller
public class IndexController {
	@Autowired
	UserRepository userRepository;
	@RequestMapping("/")
	public String index(Model model){
		User principal = CommonUtil.getPrincipal();
		model.addAttribute("id", principal.getId());

		ObjectMapper mapper=new ObjectMapper();

		try {
			model.addAttribute("userInfoSt", mapper.writeValueAsString(userRepository.findUserById(principal.getId())));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "index_cdn";
	}

	
}
