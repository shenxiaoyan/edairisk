package com.liyang.domain.user;

import com.liyang.domain.base.BaseEntity;
import org.springframework.test.annotation.Commit;

import javax.persistence.*;

/**
 * user的e签账户
 */
@Entity
@Table(name = "user_esigns")
public class UserESign extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "accountid")
    private String accountId;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

}
