package com.liyang.domain.application;

import org.springframework.web.bind.annotation.CrossOrigin;

import com.liyang.annotation.Info;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by win7 on 2017-07-25.
 */
@Entity
@Table(name = "application")
@Cacheable
@Info(label="应用",placeholder="",tip="",help="",secret="")	
public class Application implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
	@Info(label="应用的code",placeholder="XIAOJINPINGTAI",tip="通常是公司级别的应用接入，各种开放平台的APPKEY差不多",help="",secret="")	
    @Column(name = "app_code")
    private String appCode;
	
    @Column(name = "app_secret")
	@Info(label="应用的secret",placeholder="32位以上的字母数字组合",tip="与各种开放平台的APPSECRET差不多",help="",secret="")	   
    private String appSecret;

    @Info(label="应用的中文名",placeholder="小金平台、贝兜",tip="中文名称",help="",secret="等以后有需要了，应该有大中小图标")	   
    private String label;
    
    @Info(label="应用说明",placeholder="",tip="",help="",secret="")	   
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
//        return "Application{" +
//                "id=" + id +
//                ", appCode='" + appCode + '\'' +
//                ", appSecret='" + appSecret + '\'' +
//                ", label='" + label + '\'' +
//                ", description='" + description + '\'' +
//                '}';
    	StringBuilder sb = new StringBuilder();
    	sb.append("Application{")
        .append("id=") .append(id)
        .append(",).append(appCode='") .append(appCode) .append('\'')
        .append(",).append(appSecret='") .append(appSecret) .append('\'')
        .append(",).append(label='") .append(label) .append('\'')
        .append(",).append(description='") .append(description) .append('\'')
        .append('}');
    	return sb.toString();
    }
}
