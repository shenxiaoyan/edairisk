package com.liyang.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import com.liyang.message.ImageInfo;
import com.liyang.util.FailReturnObject;
import com.liyang.util.FileFormat;
import com.liyang.util.FileFormatJudge;
import com.liyang.util.SuccessReturnObject;

@Service
public class FileUploadService {

	@Value("${spring.oss.endpoint}")
	private String endpoint;

	@Value("${spring.oss.accessKeyId}")
	private String accessKeyId;

	@Value("${spring.oss.accessKeySecret}")
	private String accessKeySecret;

	@Value("${spring.oss.bucketName}")
	private String bucketName;
	
	
	
	
	public SuccessReturnObject uploadByIOStream(InputStream fileInputStream){
		Map<String, Object> fileInfoMap = fileInformation(fileInputStream);
		upload(fileInfoMap, fileInputStream);
		return new SuccessReturnObject(new OssFile("", (String) fileInfoMap.get("newFileName"),
				Long.valueOf(fileInfoMap.get("fileSize").toString()), (String) fileInfoMap.get("fileFormatString"), "file"));
	}
	// 文件信息
	private Map<String, Object> fileInformation(InputStream is) {
		Map<String, Object> fileInfoMap = new HashMap<String, Object>();
		UUID randomUUID = UUID.randomUUID();
		String newFileName = randomUUID.toString()+ ".zip" ;
		// 文件大小
		int fileSize;
		try {
			fileSize = is.available();
		} catch (IOException e) {
			throw new FailReturnObject(7641, "io错误",com.liyang.util.ReturnObject.Level.ERROR);
		}
		fileInfoMap.put("fileSize", fileSize);
		fileInfoMap.put("newFileName", newFileName);
		
		// 上传到阿里云的返回的文件地址
		StringBuilder sb2 = new StringBuilder();
		fileInfoMap.put("fileFormatString", "ZIP");
		fileInfoMap.put("realfileFormat", "ZIP");
		return fileInfoMap;
	}

	public SuccessReturnObject uploadSignPdf(InputStream inputStream)
	{
		Map<String, Object> fileInfoMap = new HashMap<String, Object>();
		UUID randomUUID = UUID.randomUUID();
		String newFileName = randomUUID.toString()+ ".pdf" ;
		// 文件大小
		int fileSize;
		try {
			fileSize = inputStream.available();
		} catch (IOException e) {
			throw new FailReturnObject(7641, "io错误",com.liyang.util.ReturnObject.Level.ERROR);
		}
		fileInfoMap.put("fileSize", fileSize);
		fileInfoMap.put("newFileName", newFileName);

		// 上传到阿里云的返回的文件地址
		StringBuilder sb2 = new StringBuilder();
		fileInfoMap.put("fileFormatString", "pdf");
		fileInfoMap.put("realfileFormat", "pdf");
		upload(fileInfoMap, inputStream);
		return new SuccessReturnObject(new OssFile("", (String) fileInfoMap.get("newFileName"),
				Long.valueOf(fileInfoMap.get("fileSize").toString()), (String) fileInfoMap.get("fileFormatString"), "file"));
	}
	
	
	
	
	

	// 文件信息
	private Map<String, Object> fileInformation(MultipartFile file) {
		Map<String, Object> fileInfoMap = new HashMap<String, Object>();

		// 文件原名
		String originalFilename = file.getOriginalFilename();
		fileInfoMap.put("originalFilename", originalFilename);

		// 文件大小
		Long fileSize = file.getSize();
		fileInfoMap.put("fileSize", fileSize);

		// 新文件名
		String suffix = "";
		if (originalFilename.lastIndexOf(".") != -1) {
			suffix = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
		}
		UUID randomUUID = UUID.randomUUID();
		
		
		fileInfoMap.put("randomUUID", randomUUID.toString());



		// 文件类型
		String fileFormatString = "";
		FileFormat realfileFormat = null;
		try {
			realfileFormat = FileFormatJudge.getFormat(file.getInputStream());
		} catch (IOException e) {
			throw new FailReturnObject(5672, "上传文件格式解析出错",com.liyang.util.ReturnObject.Level.FATAL);
		}
		if (realfileFormat != null) {
			fileFormatString = realfileFormat.toString();
		} else if (!"".equals(suffix)) {
			fileFormatString = suffix.toUpperCase();
		}
		String newFileName=null;
		if(suffix.equals("")){
			newFileName = randomUUID.toString()+ "." + fileFormatString;
		}else{
			newFileName = randomUUID.toString()+ "." +  suffix;
		}
		fileInfoMap.put("newFileName", newFileName);
		
		
		// 上传到阿里云的返回的文件地址
		StringBuilder sb2 = new StringBuilder();
		String ossFileUrl = sb2.append(bucketName).append(".").append(endpoint).append("/").append(newFileName)
				.toString();
		fileInfoMap.put("ossFileUrl", ossFileUrl);
		
		fileInfoMap.put("fileFormatString", fileFormatString);
		fileInfoMap.put("realfileFormat", realfileFormat);

		return fileInfoMap;
	}

	// 文件上传，返回文件阿里云地址
	public String upload(Map<String, Object> fileInformation, InputStream fileInputStream) {
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		StringBuilder sb3 = new StringBuilder();
		String ossFileReturnTemplate = sb3.append(bucketName).append(".").append(endpoint).append("/").toString();
		String newFileName = (String) fileInformation.get("newFileName");
		try {
			ossClient.putObject(new PutObjectRequest(bucketName, newFileName, fileInputStream));
		} catch (OSSException oe) {
			StringBuilder sb4 = new StringBuilder();
			throw new FailReturnObject(1568,
					sb4.append(oe.getErrorCode()).append(":").append(oe.getErrorMessage()).toString(),com.liyang.util.ReturnObject.Level.FATAL);
		} finally {
			ossClient.shutdown();
		}
		return ossFileReturnTemplate + newFileName;
	}
	


	public SuccessReturnObject uploadByFileTypeName(MultipartFile file, String fileType) {
		Map<String, Object> fileInfoMap = fileInformation(file);
		// 文件类型判断
		if ("image".equals(fileType)) {
			if (!FileFormatJudge.isImage((FileFormat) fileInfoMap.get("realfileFormat"))) {
				throw new FailReturnObject(4514, "未知的图片格式",com.liyang.util.ReturnObject.Level.ERROR);
			}

			try {
				upload(fileInfoMap, file.getInputStream());
			} catch (IOException e1) {
				throw new FailReturnObject(4811, "图片文件上传出错",com.liyang.util.ReturnObject.Level.FATAL);
			}
			try {
				OssImage imageContent;
				imageContent = transferImage(fileInfoMap, file.getInputStream());
				return new SuccessReturnObject(imageContent);
			} catch (IOException e) {
				throw new FailReturnObject(5981, "文件大小转换错误",com.liyang.util.ReturnObject.Level.ERROR);
			}

		} else if ("file".equals(fileType)) {
			String newFileAddress="";
			try {
				upload(fileInfoMap, file.getInputStream());
			} catch (IOException e) {
				throw new FailReturnObject(4659, "普通文件上传出错",com.liyang.util.ReturnObject.Level.ERROR);
			}
			return new SuccessReturnObject(new OssFile((String) fileInfoMap.get("originalFilename"), (String) fileInfoMap.get("newFileName"),
					(Long) fileInfoMap.get("fileSize"), (String) fileInfoMap.get("fileFormatString"), "file"));
		} else {
			throw new FailReturnObject(4213, "不支持的文件格式",com.liyang.util.ReturnObject.Level.WARNING);
		}
	}

	public OssImage transferImage(Map<String, Object> fileInformation, InputStream fileInputStream) {
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		StringBuilder sb5 = new StringBuilder();
		String ossFileReturnTemplate = sb5.append(bucketName).append(".").append(endpoint).append("/").toString();
		String randomUUIDString = (String) fileInformation.get("randomUUID");
		StringBuilder sb6 = new StringBuilder();
		String transNewFileName = sb6.append(randomUUIDString).append(".jpg").toString();
		StringBuilder sb7 = new StringBuilder();
		String transImageNameTemplate = sb7.append(randomUUIDString).append("%s").append(".jpg").toString();
		OssImage ossImage = new OssImage();
		ossImage.setFileType((String) fileInformation.get("fileFormatString"));
		ossImage.setNewFileName(((String) fileInformation.get("newFileName")).toString());
		ossImage.setOriginalFileName((String) fileInformation.get("originalFilename"));
		ossImage.setFileSize((Long) fileInformation.get("fileSize"));
		ossImage.setUploadType("image");
		try {
			if ("JPEG".equals((String) fileInformation.get("fileFormatString"))) {
				ossImage.setImageInfo(picutureTrans_198_720(ossClient, transImageNameTemplate,
						(String) fileInformation.get("newFileName"), fileInputStream));
				return ossImage;
			} else {
				GetObjectRequest request = picutureTrans(ossClient, "image/format,jpg",
						(String) fileInformation.get("newFileName"), transNewFileName);
				ossImage.setImageInfo(picutureTrans_198_720(ossClient, transImageNameTemplate, transNewFileName,
						ossClient.getObject(request).getObjectContent()));
				return ossImage;
			}
		} catch (Exception e) {
			throw new FailReturnObject(8462, "阿里云转换文件格式出错",com.liyang.util.ReturnObject.Level.ERROR);
		} finally {
			ossClient.shutdown();
		}
	}

	private GetObjectRequest picutureTrans(OSSClient ossClient, String style, String primitiveFileName,
			String transNewFileName) {

		GetObjectRequest request = new GetObjectRequest(bucketName, primitiveFileName);
		// if(style!=null){
		request.setProcess(style);
		// }
		OSSObject ossObject = ossClient.getObject(request);
		InputStream inputStream = ossObject.getObjectContent();
		ossClient.putObject(new PutObjectRequest(bucketName, transNewFileName, inputStream));
		return request;
	}

	private ImageInfo[] picutureTrans_198_720(OSSClient ossClient, String transFileNameTemplate,
			String transNewFileName, InputStream fileInputStream) {
		ImageInfo[] imageInfos = null;
		StringBuilder sb9 = new StringBuilder();
		String ossFileReturnTemplate = "";
		try {
			BufferedImage bufferedImage = ImageIO.read(fileInputStream);
			Collection<Integer> colls = new ArrayList<Integer>();
			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();
			int size = 0;
			colls.add(height);
			colls.add(width);
			Integer min = Collections.min(colls);
			imageInfos = new ImageInfo[3];
			if (min >= 198) {
				GetObjectRequest request = picutureTrans(ossClient, "image/resize,m_mfit,w_198", transNewFileName,
						String.format(transFileNameTemplate, "_198"));
				imageInfos[0] = imageInfoCreate(3, 0, 0, 0,
						ossFileReturnTemplate + String.format(transFileNameTemplate, "_198"));
				if (min >= 720) {
					request = picutureTrans(ossClient, "image/resize,m_mfit,w_720", transNewFileName,
							String.format(transFileNameTemplate, "_720"));
					imageInfos[1] = imageInfoCreate(2, 0, 0, 0,
							ossFileReturnTemplate + String.format(transFileNameTemplate, "_720"));
				}
			}
			if (imageInfos[0] == null) {
				for (int i = 1; i < 4; i++) {
					imageInfos[i - 1] = imageInfoCreate(i, height, width, size,
							ossFileReturnTemplate + transNewFileName);
				}
			} else if (imageInfos[1] == null) {
				imageInfos[1] = imageInfoCreate(2, 0, 0, size,
						ossFileReturnTemplate + String.format(transFileNameTemplate, ""));
				imageInfos[2] = imageInfoCreate(1, height, width, size, ossFileReturnTemplate + transNewFileName);
			} else {
				imageInfos[2] = imageInfoCreate(1, height, width, size, ossFileReturnTemplate + transNewFileName);
			}
			return imageInfos;
		} catch (IOException e) {
			throw new FailReturnObject(4721, "阿里云图片格式转换出错",com.liyang.util.ReturnObject.Level.ERROR);
		}
		// return imageInfos;
	}

	private ImageInfo imageInfoCreate(Integer type, Integer height, Integer width, Integer size, String url) {
		ImageInfo imageInfo = new ImageInfo();
		imageInfo.setType(type);
		imageInfo.setHeight(height);
		imageInfo.setWidth(width);
		imageInfo.setSize(size);
		imageInfo.setUrl(url);
		return imageInfo;
	}

	public static class OssImage extends OssFile {

		private ImageInfo[] imageInfo;

		public ImageInfo[] getImageInfo() {
			return imageInfo;
		}

		public void setImageInfo(ImageInfo[] imageInfo) {
			this.imageInfo = imageInfo;
		}
		public OssImage(String originalFileName, String newFileName, Long fileSize, String fileType, String uploadType) {
			super(originalFileName,newFileName,fileSize,fileType,uploadType);
		}
		public OssImage(){}
	}

	public static class OssFile {
		private String originalFileName;
		private String newFileName;
		private Long fileSize;
		private String fileType;
		private String uploadType;
		private String topcategory="-";// 顶级目录
		private String subcategory="-";// 子级目录
		private String actCode;
		public String getTopcategory() {
			return topcategory;
		}

		public void setTopcategory(String topcategory) {
			this.topcategory = topcategory;
		}

		public String getSubcategory() {
			return subcategory;
		}

		public void setSubcategory(String subcategory) {
			this.subcategory = subcategory;
		}

		public String getUploadType() {
			return uploadType;
		}

		public void setUploadType(String uploadType) {
			this.uploadType = uploadType;
		}

		public String getOriginalFileName() {
			return originalFileName;
		}

		public void setOriginalFileName(String originalFileName) {
			this.originalFileName = originalFileName;
		}

		public String getNewFileName() {
			return newFileName;
		}

		public void setNewFileName(String newFileName) {
			this.newFileName = newFileName;
		}

		public Long getFileSize() {
			return fileSize;
		}

		public void setFileSize(Long fileSize) {
			this.fileSize = fileSize;
		}

		public String getFileType() {
			return fileType;
		}

		public void setFileType(String fileType) {
			this.fileType = fileType;
		}

		public String getActCode() {
			return actCode;
		}

		public void setActCode(String actCode) {
			this.actCode = actCode;
		}

		public OssFile(String originalFileName, String newFileName, Long fileSize, String fileType, String uploadType) {
			super();
			this.originalFileName = originalFileName;
			this.newFileName = newFileName;
			this.fileSize = fileSize;
			this.fileType = fileType;
			this.uploadType = uploadType;
		}

		public OssFile() {

		}

	}

	// 重载

	public OssImage uploadByUrlAndFileType(String Url, String fileType) {
		try {
			URL url = new URL(Url);
			Map<String, Object> fileInfoMap = fileInformation(url);
			// 文件类型判断
			if ("image".equals(fileType)) {
				if (!FileFormatJudge.isImage((FileFormat) fileInfoMap.get("realfileFormat"))) {
					throw new FailReturnObject(4514, "未知的图片格式",com.liyang.util.ReturnObject.Level.ERROR);
				}
				upload(fileInfoMap, url.openStream());
				OssImage imageContent = transferImage(fileInfoMap, url.openStream());
				return imageContent;
			} else if ("file".equals(fileType)) {
				upload(fileInfoMap, url.openStream());
				return new OssImage((String) fileInfoMap.get("originalFilename"), (String) fileInfoMap.get("newFileName"),
						(Long) fileInfoMap.get("fileSize"), (String) fileInfoMap.get("fileFormatString"), "file");
			} else {
				throw new FailReturnObject(4212, "不支持的文件格式",com.liyang.util.ReturnObject.Level.ERROR);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			throw new FailReturnObject(6432, "url格式出错",com.liyang.util.ReturnObject.Level.ERROR);
		} catch (IOException e) {
			throw new FailReturnObject(8234, "从url地址取文件出错",com.liyang.util.ReturnObject.Level.ERROR);
		}
	}

	private Map<String, Object> fileInformation(URL url) {
		Map<String, Object> fileInfoMap = new HashMap<String, Object>();
		// 文件原名
		String originalFilename = url.getFile();
		fileInfoMap.put("originalFilename", originalFilename);
		// 文件大小
		Long fileSize;
		try {
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			fileSize = con.getContentLengthLong();
			fileInfoMap.put("fileSize", fileSize);
		} catch (IOException e) {
			throw new FailReturnObject(8762, "读取远程服务器出错",com.liyang.util.ReturnObject.Level.FATAL);
		}
		
		// 新文件名
		String suffix = "";
		if (originalFilename.lastIndexOf(".") != -1) {
			suffix = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
		}
		
		// 文件类型
		String fileFormatString = "";
		FileFormat realfileFormat;
		try {
			realfileFormat = FileFormatJudge.getFormat(((HttpURLConnection) url.openConnection()).getInputStream());
			if (realfileFormat != null) {
				fileFormatString = realfileFormat.toString();
			} else if (!"".equals(suffix)) {
				fileFormatString = suffix.toUpperCase();
			}
			fileInfoMap.put("fileFormatString", fileFormatString);
			fileInfoMap.put("realfileFormat", realfileFormat);
		} catch (IOException e) {
			throw new FailReturnObject(7634, "读取远程服务器出错",com.liyang.util.ReturnObject.Level.FATAL);
		}
		

	
		UUID randomUUID = UUID.randomUUID();
		String newFileName = null;
		if(suffix.equals("")){
			newFileName=randomUUID.toString() + "."+fileFormatString.toLowerCase();
		}else{
			newFileName = randomUUID.toString() + "." + suffix;
		}
		fileInfoMap.put("newFileName", newFileName);
		fileInfoMap.put("randomUUID", randomUUID.toString());

		// 上传到阿里云的返回的文件地址
		String ossFileUrl = bucketName + "." + endpoint + "/" + newFileName;
		fileInfoMap.put("ossFileUrl", ossFileUrl);



		return fileInfoMap;
	}
}
