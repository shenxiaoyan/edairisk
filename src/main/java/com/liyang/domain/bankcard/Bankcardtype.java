package com.liyang.domain.bankcard;

import com.liyang.domain.base.BaseEntity;
import com.liyang.domain.role.Role;

import javax.persistence.*;
import java.util.Set;

public class Bankcardtype extends BaseEntity {
    /**
     *
     */
    @Transient
    private static final long serialVersionUID = 1L;




    @Enumerated(EnumType.STRING)
    @Column(name="bankcardtype_code")
    private BankcardtypeCode bankcardtypeCode;




    @OneToMany(mappedBy = "departmenttype")
    private Set<Bankcard> bankcards;


    @OneToMany(mappedBy = "departmenttype")
    private Set<Role> roles;





    public BankcardtypeCode getBankcardtypeCode() {
        return bankcardtypeCode;
    }


    public void setBankcardtypeCode(BankcardtypeCode bankcardtypeCode) {
        this.bankcardtypeCode = bankcardtypeCode;
    }


    public Set<Bankcard> getBankcards() {
        return bankcards;
    }


    public void setBankcards(Set<Bankcard> bankcards) {
        this.bankcards = bankcards;
    }


    public Set<Role> getRoles() {
        return roles;
    }


    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public static enum BankcardtypeCode{
        STORE,
        GROUP,
        WULIU_COMPANY,
        CREDITOR,
        DEBTOR,
        LAW,
        DISTRIBUTOR
    }
}
