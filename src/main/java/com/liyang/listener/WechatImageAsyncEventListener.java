package com.liyang.listener;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.AbstractWorkflowEntity;
import com.liyang.domain.base.AbstractWorkflowFile;
import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.exception.Exception;
import com.liyang.domain.exception.ExceptionRepository;
import com.liyang.message.ImageInfo;
import com.liyang.service.AbstractAuditorService;
import com.liyang.service.AbstractWorkflowService;
import com.liyang.service.ExceptionService;
import com.liyang.service.FileUploadService;
import com.liyang.service.FileUploadService.OssFile;
import com.liyang.service.FileUploadService.OssImage;
import com.liyang.util.FailReturnObject;
import com.liyang.util.WechatImageAsyncFetchEvent;
import com.liyang.util.ReturnObject.Level;
import com.liyang.util.ReturnObjectImpl;
@Service
public class WechatImageAsyncEventListener implements ApplicationListener<WechatImageAsyncFetchEvent> {
	
	@Autowired
	ExceptionService exceptionService;
	
	@Autowired
	FileUploadService fileUploadService;
	
	@Override
	@Async
	public void onApplicationEvent(WechatImageAsyncFetchEvent arg0) {
		try {
			OssImage uploadByUrlAndFileType = fileUploadService.uploadByUrlAndFileType(arg0.getFile().getOriginalFileName(), "image");
			arg0.getFile().setFileSize(uploadByUrlAndFileType.getFileSize());
			arg0.getFile().setFileType(uploadByUrlAndFileType.getFileType());
			arg0.getFile().setNewFileName(uploadByUrlAndFileType.getNewFileName());
			
			if (uploadByUrlAndFileType.getImageInfo() != null) {
				ImageInfo[] imageInfo = uploadByUrlAndFileType.getImageInfo();
				for (ImageInfo imageInfo2 : imageInfo) {
					if (imageInfo2.getType().equals(1)) {
						arg0.getFile().setLargeImage(imageInfo2.getUrl());
					} else if (imageInfo2.getType().equals(2)) {
						arg0.getFile().setMiddleImage(imageInfo2.getUrl());
					} else if (imageInfo2.getType().equals(3)) {
						arg0.getFile().setSmallImage(imageInfo2.getUrl());
					}
				}
			}
			
			AbstractAuditorService service = arg0.getSource().getService();
			if(service instanceof AbstractWorkflowService){
				AbstractWorkflowService abstractWorkflowService = (AbstractWorkflowService)service;
				abstractWorkflowService.getFileRepository().save(arg0.getFile());
			}else{
				throw new FailReturnObject(4974, "只有工作流实体支持传文件",Level.WARNING);
			}
			
		} catch (FailReturnObject e) {
			Exception exception = new Exception();
			exception.setActionStatus("FAIL");
			exception.setErrorCode(e.getErrorCode());
			exception.setErrorInfo(arg0.getFile().getClass().getSimpleName()+"从微信转移文件出错,file_id=" + arg0.getFile().getId()+"，出错信息："+ e.getMessage());
			exceptionService.log(exception);
		}catch(java.lang.Exception ex){
			exceptionService.log(ex);
		}
		
		
	}
	

}
