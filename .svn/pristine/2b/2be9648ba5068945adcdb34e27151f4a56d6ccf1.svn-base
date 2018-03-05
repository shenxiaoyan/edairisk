package com.liyang.domain.bank;

import com.liyang.annotation.Info;
import com.liyang.annotation.Info.FLAG;
import com.liyang.domain.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * Created by win7 on 2017-07-26.
 */
@Entity
@Table(name = "bank")
@Info(label="银行",placeholder="",tip="工农商建等银行或虚拟的网络银行（小金、支付宝）",help="",secret="")	   
public class Bank extends BaseEntity{
    private String alias;
    @Column(name = "bank_image") //银行logo
    @Info(label="银行logo图",placeholder="",tip="",help="高度128、宽度小于300的图片",secret="")	   
    private String bankImage;
    
    @Column(name = "bank_icon") //银行方形小图标
    @Info(label="银行icon图",placeholder="",tip="",help="128*128的图标文件",secret="")	   
    private String bankIcon;

    @Column(name = "bank_code")
    @Info(label="银行的标识code",placeholder="如ICBC、ALIPAY",tip="",help="大写的拼音",secret="")	   
    private String bankCode;
    
    @Column(name = "is_online")
	@Info(flag=FLAG.True,label="是",placeholder="",tip="",help="",secret="")
	@Info(flag=FLAG.False,label="否",placeholder="",tip="",help="",secret="")
	@Info(flag=FLAG.self,label="就否为网络银行",placeholder="",tip="",help="",secret="工农商建等传统银行为否，支付宝/财富通等为是")	
	private Boolean isOnline=false;
    

    @Lob
    @Info(label="银行的说明",placeholder="",tip="",help="",secret="")	   
    private String description;
    
    
    @Info(label="前台排序",placeholder="",tip="",help="",secret="")	   
    private Integer sort;

    
    public Boolean getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Boolean isOnline) {
		this.isOnline = isOnline;
	}

	public String getBankIcon() {
		return bankIcon;
	}

	public void setBankIcon(String bankIcon) {
		this.bankIcon = bankIcon;
	}

	public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getBankImage() {
        return bankImage;
    }

    public void setBankImage(String bankImage) {
        this.bankImage = bankImage;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
