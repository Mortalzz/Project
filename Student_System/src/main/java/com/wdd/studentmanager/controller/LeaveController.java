package com.wdd.studentmanager.controller;

import com.wdd.studentmanager.common.ResultData;
import com.wdd.studentmanager.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@ResponseBody
@RequestMapping("/leave")
public class LeaveController {
    @Autowired
    LeaveService leaveService;

    @RequestMapping("/request")
    public ResultData Requestleave(HttpServletRequest request){

    }

}
