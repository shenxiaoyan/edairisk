package com.liyang.domain.base;

import com.liyang.domain.user.User;

import java.util.Date;

 public interface BaseProjection {
     Integer getId();
     User getCreatedBy();
     User getLastModifiedBy();
     Date getCreatedAt();
     Date getLastModifiedAt();

}
