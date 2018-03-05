package com.liyang.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.liyang.domain.exception.Exception;
import com.liyang.domain.exception.ExceptionRepository;
import com.liyang.util.FailReturnObject;
import com.liyang.util.ReturnObject;
import com.liyang.util.ReturnObjectImpl;
import com.liyang.util.ReturnObject.Level;

@Service
public class ExceptionService {

	@Autowired
	ExceptionRepository exceptionRepository;
	
	@Value("${spring.profiles.active}")
	private String env;
	
	public void log(ReturnObject returnObject){
		Exception exception = new Exception();
		exception.setActionStatus(returnObject.getActionStatus());
		exception.setErrorCode(returnObject.getErrorCode());
		exception.setErrorInfo(returnObject.getErrorInfo());
		exception.setLevel(returnObject.getLevel());
		
		if(env.equals("dev")){
			exceptionRepository.save(exception);
		}else{
			if(returnObject.getLevel().equals(ReturnObject.Level.ERROR) || returnObject.getLevel().equals(ReturnObject.Level.FATAL)){
				exceptionRepository.save(exception);
			}
		}
	}
	public void log(java.lang.Exception ex){
		Exception exception = new Exception();
		
		exception.setActionStatus("FAIL");
		exception.setErrorCode(500);
		exception.setErrorInfo(ex.getClass().toString() + "\n" + ex.getMessage() + "\n" + ex.getCause() + "\n"
				+ Arrays.toString(ex.getStackTrace()));
		exception.setLevel(Level.FATAL);
		exceptionRepository.save(exception);
	}
	
	public void log(RuntimeException ex){
		Exception exception = new Exception();
		exception.setActionStatus("FAIL");
		exception.setErrorCode(500);
		exception.setErrorInfo(ex.getClass().toString() + "\n" + ex.getMessage() + "\n" + ex.getCause() + "\n"
				+ Arrays.toString(ex.getStackTrace()));
		exception.setLevel(Level.ERROR);
		exceptionRepository.save(exception);
	}
}
