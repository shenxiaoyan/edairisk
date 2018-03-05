package com.liyang.domain.riskmodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.liyang.domain.base.BaseEntity;

//
@Table
@Entity
public class RiskmodelItem extends BaseEntity{
	private String alias;
	private String description;
	@Column(name="tablce_code")
	private String tableCode;
	@Column(name="table_name")
	private String tableName;
	@Column(name="field_code")
	private String fieldCode;
	@Column(name="field_name")
	private String fieldName;
	@Column(name="fail_boundary")
	private Integer failBoundary;
	private Integer score1;
	private Integer boundary2;
	private Integer score3;
	private Integer boundary4;
	private Integer score5;
	private Integer boundary6;
	private Integer score7;
	private Integer boundary8;
	private Integer score9;
	@Column(name="success_boundary")
	private Integer successBoundary;
	private Integer weight;
	@ManyToOne
	@JoinColumn(name="riskmodel_id")
	private Riskmodel riskmodel;
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTableCode() {
		return tableCode;
	}
	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getFieldCode() {
		return fieldCode;
	}
	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public Integer getFailBoundary() {
		return failBoundary;
	}
	public void setFailBoundary(Integer failBoundary) {
		this.failBoundary = failBoundary;
	}
	public Integer getScore1() {
		return score1;
	}
	public void setScore1(Integer score1) {
		this.score1 = score1;
	}
	public Integer getBoundary2() {
		return boundary2;
	}
	public void setBoundary2(Integer boundary2) {
		this.boundary2 = boundary2;
	}
	public Integer getScore3() {
		return score3;
	}
	public void setScore3(Integer score3) {
		this.score3 = score3;
	}
	public Integer getBoundary4() {
		return boundary4;
	}
	public void setBoundary4(Integer boundary4) {
		this.boundary4 = boundary4;
	}
	public Integer getScore5() {
		return score5;
	}
	public void setScore5(Integer score5) {
		this.score5 = score5;
	}
	public Integer getBoundary6() {
		return boundary6;
	}
	public void setBoundary6(Integer boundary6) {
		this.boundary6 = boundary6;
	}
	public Integer getScore7() {
		return score7;
	}
	public void setScore7(Integer score7) {
		this.score7 = score7;
	}
	public Integer getBoundary8() {
		return boundary8;
	}
	public void setBoundary8(Integer boundary8) {
		this.boundary8 = boundary8;
	}
	public Integer getScore9() {
		return score9;
	}
	public void setScore9(Integer score9) {
		this.score9 = score9;
	}
	public Integer getSuccessBoundary() {
		return successBoundary;
	}
	public void setSuccessBoundary(Integer successBoundary) {
		this.successBoundary = successBoundary;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public Riskmodel getRiskmodel() {
		return riskmodel;
	}
	public void setRiskmodel(Riskmodel riskmodel) {
		this.riskmodel = riskmodel;
	}
	
}
