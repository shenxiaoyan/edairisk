package com.liyang.controller;

import com.liyang.domain.department.DepartmentRepository;
import com.liyang.domain.ordercdd.Ordercdd;
import com.liyang.domain.ordercdd.OrdercddState;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserRepository;
import com.liyang.service.OrdercddService;
import com.liyang.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping(value = "ordercdd")
public class OrdercddController {
    @Autowired
    OrdercddService ordercddService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    @GetMapping(value = "getNotCloneCountByServiceUser")//每个客户经理未结束的CDD订单
    @ResponseBody
    public Map getNotCloneByServiceUser(HttpServletRequest request) {
        Map<String, Object> rsp = new HashMap<>();
        rsp.put("errorCode", 0);
        rsp.put("msg", "成功");

        List<User> findByDepartmentIn = userRepository.findByDepartmentIn(CommonUtil.ownAndChildrenDepartments(departmentRepository.findOne(CommonUtil.getCurrentUserDepartment().getId())));
        List<ServiceUser> serviceUsers = new ArrayList<>();

        List<OrdercddState> stateCodes = ordercddService.getNoCloneCode();
        for (User user : findByDepartmentIn) {
            ServiceUser suser = new ServiceUser();
            suser.setId(user.getId());
            suser.setName(user.getNickname());
            suser.setNotClone(ordercddService.getNotCloneByServiceUser(user.getId(),stateCodes).size());
            serviceUsers.add(suser);

        }
        rsp.put("data", serviceUsers);
        return rsp;
    }

    private class ServiceUser {
        private Integer id;
        private String name;
        private Integer notClone;//没有完成的数量

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getNotClone() {
            return notClone;
        }

        void setNotClone(Integer notClone) {
            this.notClone = notClone;
        }
    }

}
