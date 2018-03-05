package com.liyang.message;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImageContent implements IContent{
	
	
private String uuid;
private Integer ImageFormat;
private ImageInfo[] imageInfo;

@JsonProperty("UUID")
public String getUuid() {
	return uuid;
}
@JsonProperty("UUID")
public void setUuid(String uuid) {
	this.uuid = uuid;
}
@JsonProperty("ImageFormat")
public Integer getImageFormat() {
	return ImageFormat;
}
@JsonProperty("ImageFormat")
public void setImageFormat(Integer imageFormat) {
	ImageFormat = imageFormat;
}
@JsonProperty("ImageInfoArray")
public ImageInfo[] getImageInfo() {
	return imageInfo;
}
@JsonProperty("ImageInfoArray")
public void setImageInfo(ImageInfo[] imageInfo) {
	this.imageInfo = imageInfo;
}

}
