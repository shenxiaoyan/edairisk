package com.liyang.domain.loancompanyfee;

import com.liyang.annotation.Info;
import com.liyang.domain.base.BaseEntity;
import com.liyang.domain.productcompanyfee.Productcompanyfee;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "loan_companyfee")
@Info(label = "单笔贷款公司收费表",tip = "单笔贷款公司收费表")
public class LoanComapnyfee extends BaseEntity {
    @Column(name = "amount",precision = 19,scale = 6)
    private BigDecimal amount;
    @Column(name = "description")
    private String description;
    @Column(name = "comment")
    private String comment;
    @JoinColumn(name = "product_companyfee_id")
    @ManyToOne
    private Productcompanyfee productcompanyfee;

    public Productcompanyfee getProductcompanyfee() {
        return productcompanyfee;
    }

    public void setProductcompanyfee(Productcompanyfee productcompanyfee) {
        this.productcompanyfee = productcompanyfee;
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
