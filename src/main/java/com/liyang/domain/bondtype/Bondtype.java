package com.liyang.domain.bondtype;

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
@Table(name = "bondtype")
@Info(label="保证金库",placeholder="",tip="",help="",secret="")
public class Bondtype extends BaseEntity{
    private String alias;

    @Column(name = "bondtype_code")
    @Info(label="保证金的标识code",placeholder="如ICBC、ALIPAY",tip="",help="大写的拼音",secret="")
    private String bondtypeCode;

    @Lob
    @Info(label="保证金说明",placeholder="",tip="",help="",secret="")
    private String description;

    @Lob
    @Info(label="保证金备注",placeholder="",tip="",help="",secret="")
    private String comment;


    @Info(label="前台排序",placeholder="",tip="",help="",secret="")	   
    private Integer sort;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getBondtypeCode() {
        return bondtypeCode;
    }

    public void setBondtypeCode(String bondtypeCode) {
        this.bondtypeCode = bondtypeCode;
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
