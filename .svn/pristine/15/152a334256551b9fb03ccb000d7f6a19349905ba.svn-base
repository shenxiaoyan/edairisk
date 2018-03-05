package com.liyang.controller;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.liyang.Exception.AesException;
import com.liyang.util.SHA1;


@Controller
public class WechatSignature {
	@RequestMapping(value="/beidou/wechatSign",method=RequestMethod.GET)
	@ResponseBody
	public String wechatSign(@RequestParam("timestamp") String timestamp,@RequestParam("nonce") String nonce,@RequestParam("signature") String signature,@RequestParam("echostr") String echostr) throws AesException{
		String token = "wuliuqianzhuang123";
		String sha1String=SHA1.getSHA1(token,timestamp,nonce);
		if(sha1String.equals(signature)){
			return echostr;
		}else{
			return "fail";
		}
	}
}
