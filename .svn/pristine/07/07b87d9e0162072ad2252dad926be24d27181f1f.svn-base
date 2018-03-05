package com.liyang.domain.productcompanyfee;

import com.liyang.annotation.Info;
import com.liyang.domain.base.BaseEntity;
import com.liyang.domain.feetype.Feetype;
import com.liyang.domain.product.Product;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by win7 on 2017-07-26.
 */
@Entity
@Table(name = "product_companyfee")
@Info(label="费用库",placeholder="",tip="",help="",secret="")
public class Productcompanyfee extends BaseEntity{
    private String alias;
    @ManyToOne
    @JoinColumn(name="feetype_id")
    @Info(label="费用库ID",placeholder="",tip="",help="",secret="")
    private Feetype feetype;

    @Column(name = "period_number")
    @Info(label="分期数",placeholder="如ICBC、ALIPAY",tip="",help="大写的拼音",secret="")
    private Integer periodNumber;

    @Column(name = "fee_rate",precision = 19,scale = 6)
    @Info(label="按比例收费，优先",placeholder="如ICBC、ALIPAY",tip="",help="大写的拼音",secret="")
    private BigDecimal feeRate;

    @Column(name = "fee_amount",precision = 19,scale = 6)
    @Info(label="固定收费")
    private BigDecimal feeAmount;

    @Lob
    @Info(label="公司收费说明",placeholder="",tip="",help="",secret="")
    private String description;

    @Lob
    @Info(label="公司收费备注",placeholder="",tip="",help="",secret="")
    private String comment;

    @Lob
    @Info(label="feetype_code",placeholder="",tip="",help="",secret="")
    private String feetypeCode;
    
    @ManyToOne
    @JoinColumn(name="product_id")
    @Info(label="销售产品ID",placeholder="",tip="",help="",secret="")
    private Product product;

	@Info(label="前台排序",placeholder="",tip="",help="",secret="")	   
    private Integer sort;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Feetype getFeetype() {
        return feetype;
    }
    
    public String getFeetypeCode() {
		return feetypeCode;
	}

	public void setFeetypeCode(String feetypeCode) {
		this.feetypeCode = feetypeCode;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
    public void setFeetype(Feetype feetype) {
        this.feetype = feetype;
    }

    public Integer getPeriodNumber() {
        return periodNumber;
    }

    public void setPeriodNumber(Integer periodNumber) {
        this.periodNumber = periodNumber;
    }

    public BigDecimal getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(BigDecimal feeRate) {
        this.feeRate = feeRate;
    }

    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
