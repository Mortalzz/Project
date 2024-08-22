package com.wdd.studentmanager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wdd.studentmanager.common.BindingResultUtil;
import com.wdd.studentmanager.common.ResultData;
import com.wdd.studentmanager.domain.Login;
import com.wdd.studentmanager.domain.S_student;
import com.wdd.studentmanager.mapper.StudentMapper;
import com.wdd.studentmanager.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.UUID;

@Controller
//@ResponseBody
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

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
        if (!login.getCode().equals(code)) {
            return ResultData.fail("验证码不正确，请检查后重新输入");
        }
        // 判断学生是否被激活
        QueryWrapper<S_student> wrapper = new QueryWrapper<>();
        wrapper.eq("sn", login.getSn());
        wrapper.eq("password", login.getPassword());
        S_student stu = studentService.getOne(wrapper);
        // 验证登录用户是学生还是管理员
        if (stu == null) {
            return ResultData.fail("用户不存在，请检查后重新输入");
        } else {
            if (stu.getStatus() == 0) {
                return ResultData.fail("用户未激活，请联系管理员激活");
            } else {
                HashMap<String, String> map = new HashMap<>();
                map.put("sn", login.getSn());
                String token = JWTUtils.getToken(map);
                stu.setToken(token);
                return ResultData.success(stu);
            }
        }
    }








}
