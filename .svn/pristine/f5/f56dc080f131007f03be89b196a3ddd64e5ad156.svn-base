package com.liyang.domain.loanstorefee;

import com.liyang.annotation.Info;
import com.liyang.domain.base.BaseEntity;
import com.liyang.domain.productstorefee.Productstorefee;

import javax.persistence.*;
import java.math.BigDecimal;
@Entity
@Table(name = "loan_storefee")
@Info(label = "单笔贷款门店收费表",tip = "单笔贷款门店收费表")
public class LoanStorefee  extends BaseEntity{
    @Column(name = "amount",precision = 19,scale = 6)
    private BigDecimal amount;
    @Column(name = "description")
    private String description;
    @Column(name = "comment")
    private String comment;
    @JoinColumn(name = "product_storefee_id")
    @ManyToOne
    private Productstorefee productstorefee;

    public Productstorefee getProductstorefee() {
        return productstorefee;
    }

    public void setProductstorefee(Productstorefee productstorefee) {
        this.productstorefee = productstorefee;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
}
