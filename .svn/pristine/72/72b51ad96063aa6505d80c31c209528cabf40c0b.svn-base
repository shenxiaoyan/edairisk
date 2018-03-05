package com.liyang.domain.enterprise;

import com.liyang.domain.base.AbstractTag;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by win7 on 2017-07-25.
 */
@Entity
@Table(name = "enterprise_tag")
//person enterpise user
public class EnterpriseTag extends AbstractTag{
    @ManyToMany(targetEntity = Enterprise.class,mappedBy = "enterpriseTagSet")
    private Set<Enterprise> enterpriseSet=new HashSet<Enterprise>();
    //优先级
    public void setPersonSet(Set<Enterprise> enterpriseSet) {
        this.enterpriseSet = enterpriseSet;
    }

    public Set<Enterprise> getPersonSet() {
        return enterpriseSet;
    }
}
