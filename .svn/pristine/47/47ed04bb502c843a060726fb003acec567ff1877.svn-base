package com.liyang.controller;

import com.liyang.domain.reference.Reference;
import com.liyang.service.DevService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller()
@RequestMapping("/devUtils")
public class DevController {
    @Autowired
    DevService devService;
    @GetMapping("/")
    public String index()
    {
        return "dev/index";
    }
    @PostMapping("/deleteDataByForeignKey")
    @ResponseBody
    public Object deleteDataByForeignKey(HttpServletRequest request)
    {
        String tableName=request.getParameter("tableName");
        String id=request.getParameter("id");
        devService.deleteDataByForeignKey(tableName,id,request.getParameter("password"));
        Map<String ,Object> rsp=new HashMap<>();
        rsp.put("ErrorCode",0);
        rsp.put("ErrorInfo","成功!");

        return rsp;
    }

}
