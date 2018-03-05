package com.liyang.domain.appmessage;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.liyang.annotation.Info;
import com.liyang.domain.base.BaseEntity;
import com.liyang.domain.product.Product;
import com.liyang.domain.user.User;

@Entity
@Table(name = "app_message")
@Info(label="APP推送消息",placeholder="",tip="",help="",secret="")	
public class AppMessage extends BaseEntity{
	
	@Info(label="消息标题",placeholder="",tip="",help="",secret="")	
	private String title;
	
	@Info(label="消息内容",placeholder="",tip="",help="",secret="")	
	private String content;
	
	@Column(name = "push_time")
	@Info(label="消息推送时间",placeholder="",tip="",help="",secret="")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Date  pushTime;
	
	@ManyToOne
	@JoinColumn(name = "to_user_id")
	@Info(label="目标推送用户",placeholder="",tip="",help="",secret="")	
	private User  toUser;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	@Info(label="属于某个产品",placeholder="",tip="",help="",secret="")	
	private Product product;
	
	
	@Info(label="消息是否已读",placeholder="",tip="",help="",secret="")
	private boolean  reading = false;
	
	@Info(label="跳转页面判断",placeholder="",tip="",help="",secret="")
	@Column(name = "to_page")
	private Integer toPage;

	public AppMessage() {
		super();
	}

	


	public AppMessage(String title, String content, Date pushTime, User toUser, Product product, Integer toPage) {
		super();
		this.title = title;
		this.content = content;
		this.pushTime = pushTime;
		this.toUser = toUser;
		this.product = product;
		this.toPage = toPage;
	}




	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getToUser() {
		return toUser;
	}

	public void setToUser(User toUser) {
		this.toUser = toUser;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Date getPushTime() {
		return pushTime;
	}

	public void setPushTime(Date pushTime) {
		this.pushTime = pushTime;
	}




	public boolean isReading() {
		return reading;
	}




	public void setReading(boolean reading) {
		this.reading = reading;
	}




	public Integer getToPage() {
		return toPage;
	}




	public void setToPage(Integer toPage) {
		this.toPage = toPage;
	}




	
	
	
	
	

}
