package com.liyang.domain.approveloan;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.liyang.annotation.Info;

@Entity
@Table(name = "approveloanstate")
@Info(label="审批借款状态表")
public class ApproveLoanState {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="create_date")
	@Info(label="创建时间",placeholder="",tip="",help="",secret="")	
	private Date createDate;
	
	@Column(name="state_code")
	@Info(label="状态码",placeholder="",tip="",help="",secret="")
	private String stateCode;
	
	@Column(name="state_label")
	@Info(label="状态名称",placeholder="",tip="",help="",secret="")
	private String stateLabel;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getStateLabel() {
		return stateLabel;
	}

	public void setStateLabel(String stateLabel) {
		this.stateLabel = stateLabel;
	}
	

}
