package com.liyang.domain.productstorebond;


import com.liyang.annotation.Info;
import com.liyang.domain.base.BaseEntity;
import com.liyang.domain.bondtype.Bondtype;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity(name = "product_storebond")
@Info(label = "门店收取保证金配置表", tip = "门店收取保证金配置表")
public class ProductStorebond extends BaseEntity {
    @Column(name = "bond_rate", scale = 4)
    @Info(label = "按比例收费，优先，前端要注意判断")
    private double bondRate;
    @Column(name = "bond_amount", precision = 19, scale = 4)
    @Info(label = "固定收费")
    private BigDecimal bondAmount;
    @Column(name = "description")
    private String description;
    @Column(name = "comment")
    private String comment;
    @JoinColumn(name = "bondtype_id")
    @ManyToOne
    private Bondtype bondtype;

    public Bondtype getBondtype() {
        return bondtype;
    }

    public void setBondtype(Bondtype bondtype) {
        this.bondtype = bondtype;
    }

    public double getBondRate() {
        return bondRate;
    }

    public void setBondRate(double bondRate) {
        this.bondRate = bondRate;
    }

    public BigDecimal getBondAmount() {
        return bondAmount;
    }

    public void setBondAmount(BigDecimal bondAmount) {
        this.bondAmount = bondAmount;
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
