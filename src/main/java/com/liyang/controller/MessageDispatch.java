package com.liyang.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/message")
public class MessageDispatch {

	private String admin;
	 

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	

	@RequestMapping(value = "/dispatch", method = {RequestMethod.POST,RequestMethod.PUT,RequestMethod.PATCH,RequestMethod.GET})
	public String act(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		return (sb.append("forward:").append(request.getRequestURI())).toString();
		//return "forward:" + request.getRequestURI();
	}



}
