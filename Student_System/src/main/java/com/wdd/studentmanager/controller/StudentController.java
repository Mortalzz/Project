package com.wdd.studentmanager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wdd.studentmanager.common.BindingResultUtil;
import com.wdd.studentmanager.common.ResultData;
import com.wdd.studentmanager.domain.Login;
import com.wdd.studentmanager.domain.S_admin;
import com.wdd.studentmanager.domain.S_student;
import com.wdd.studentmanager.service.AdminService;
import com.wdd.studentmanager.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.HashMap;
import java.util.UUID;

@Controller
//@ResponseBody
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;
    @Autowired
    private AdminService adminService;
    public String code="";
    //注册申请
    @PostMapping("/register")
    public ResultData add(@Validated @RequestBody S_student student, BindingResult result) {
        BindingResultUtil.validate(result);
        if (findBySn(student.getSn()) != null) {
            return ResultData.fail("学生已经存在，请勿重复添加");
        } else {
            boolean save = studentService.save(student);
            if (save) {
                return ResultData.success(save);
            } else {
                return ResultData.fail("保存失败，请联系网站管理员");
            }
        }
    }

    private S_student findBySn(String sn) {
        QueryWrapper<S_student> wrapper = new QueryWrapper<>();
        wrapper.eq("sn", sn);
        return studentService.getOne(wrapper);
    }

    //用户登录
    @PostMapping("/login")
    public ResultData login(@RequestBody Login login) {
        // 根据角色选择不同的登录处理逻辑
        if ("student".equals(login.getRole())) {
            return loginStudent(login);
        } else if ("admin".equals(login.getRole())) {
            return loginAdmin(login);
        } else {
            return ResultData.fail("无效的角色");
        }
    }

    private ResultData loginStudent(Login loginRequest) {
        QueryWrapper<S_student> studentWrapper = new QueryWrapper<>();
        studentWrapper.eq("sn", loginRequest.getSn());
        studentWrapper.eq("password", loginRequest.getPassword());
        S_student student = studentService.getOne(studentWrapper);

        if (student != null) {
            return ResultData.success(student);
        } else {
            return ResultData.fail("学生用户不存在");
        }
    }

    private ResultData loginAdmin(Login loginRequest) {
        QueryWrapper<S_admin> adminWrapper = new QueryWrapper<>();
        adminWrapper.eq("sn", loginRequest.getSn());
        adminWrapper.eq("password", loginRequest.getPassword());
        S_admin admin = adminService.getOne(adminWrapper);

        if (admin != null) {
            return ResultData.success(admin);
        } else {
            return ResultData.fail("管理员用户不存在");
        }
    }









}
