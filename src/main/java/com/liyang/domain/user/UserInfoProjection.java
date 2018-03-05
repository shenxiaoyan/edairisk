package com.liyang.domain.user;

import com.liyang.domain.department.Department;
import com.liyang.domain.person.Person;
import com.liyang.domain.role.Role;

public interface UserInfoProjection {
    Integer getId();
    String getNickname();
    String getHeadimgurl();
    String getUsername();
    String getSig();
    Integer getSex();
    String getCountry();
    String getProvince();
    String getCity();
    UserState getState();
    UserWorkflow getWorkflow();
    Department getDepartment();
    Role getRole();
    Person getPerson();
    String getCompanyRole();
    
    interface UserState{
        Integer getId();
        String getLabel();
    }
    interface UserWorkflow{
        Integer getId();
    }
    interface Department{
        Integer getId();
        String getLabel();
    }
    interface Role{
        Integer getId();
        String getLabel();
    }
    interface Person{
        Integer getId();
        String getLabel();
    }
}
