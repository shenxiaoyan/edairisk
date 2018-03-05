
package com.liyang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.liyang.service.FileUploadService;
import com.liyang.util.SuccessReturnObject;

@Controller
public class FileUploadController {

	@Autowired
	private FileUploadService fileUploadService;

	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	@ResponseBody
	public SuccessReturnObject handleUploadProcess(@RequestParam("file") MultipartFile file, @RequestParam("fileType") String fileType) {
		SuccessReturnObject succeessObject=fileUploadService.uploadByFileTypeName(file,fileType);
		return succeessObject;
	}
}
