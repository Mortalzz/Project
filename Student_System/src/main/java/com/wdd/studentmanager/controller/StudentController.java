package com.wdd.studentmanager.controller;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
@ResponseBody
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private AdminService adminService;
    public String code="";
    //注册申请
    @PostMapping("/register")
    public ResultData add(@Validated S_student student, BindingResult result) {
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
    //已测试  来自：邹正强

    private S_student findBySn(String sn) {
        QueryWrapper<S_student> wrapper = new QueryWrapper<>();
        wrapper.eq("sn", sn);
        return studentService.getOne(wrapper);
    }

    //用户登录
    @PostMapping("/login")
    public ResultData login(Login login, HttpServletRequest request) {
        // 根据角色选择不同的登录处理逻辑
        if ("student".equals(login.getRole())) {
            return loginStudent(login,request);
        } else if ("admin".equals(login.getRole())) {
            return loginAdmin(login,request);
        } else {
            return ResultData.fail("无效的角色");
        }
    }

    private ResultData loginStudent(Login loginRequest,HttpServletRequest request) {
        if (!loginRequest.getCode().equals(code)) {
            return ResultData.fail("验证码不正确，请检查后重新输入");
        }
        QueryWrapper<S_student> studentWrapper = new QueryWrapper<>();
        studentWrapper.eq("sn", loginRequest.getSn());
        studentWrapper.eq("password", loginRequest.getPassword());
        S_student student = studentService.getOne(studentWrapper);

        if (student != null) {
            HttpSession session = request.getSession(); // 假设在一个控制器方法中
            session.setAttribute("currentUser",student);
            return ResultData.success(student);
        } else {
            return ResultData.fail("学生用户不存在");
        }
    }

    private ResultData loginAdmin(Login loginRequest,HttpServletRequest request) {
        if (!loginRequest.getCode().equals(code)) {
            return ResultData.fail("验证码不正确，请检查后重新输入");
        }
        QueryWrapper<S_admin> adminWrapper = new QueryWrapper<>();
        adminWrapper.eq("sn", loginRequest.getSn());
        adminWrapper.eq("password", loginRequest.getPassword());
        S_admin admin = adminService.getOne(adminWrapper);

        if (admin != null) {
            HttpSession session = request.getSession(); // 假设在一个控制器方法中
            session.setAttribute("currentUser", admin);
            return ResultData.success(admin);
        } else {
            return ResultData.fail("管理员用户不存在");
        }
    }

    //获取验证码
    @GetMapping("/code")
    public void code(HttpServletResponse response) throws IOException {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        code = lineCaptcha.getCode();
        lineCaptcha.write(response.getOutputStream());
    }



    //------------------------------------------------------------------------------------------
    //下面为显示和修改学生的信息



    @GetMapping("/get_profile")
    public ResultData getProfile(HttpServletRequest request) {
        HttpSession session=request.getSession(false);
        S_student currentStu = (S_student) session.getAttribute("currentUser");
        if (currentStu != null) {
            // 获取用户详细信息
            S_student student = studentService.getById(currentStu.getId());
            return ResultData.success(student);
        } else {
            return ResultData.fail("用户未登录");
        }
    }

    @RequestMapping("/set_profile")
    public ResultData setProfile(@RequestBody S_student student,HttpServletRequest request){
        HttpSession session=request.getSession(false);
        S_student currentStu=(S_student) session.getAttribute("currentUser");
        S_student temp=student;
        if (currentStu != null) {
            // 设置用户详细信息
            temp.setId(currentStu.getId());
            boolean result=studentService.updateById(temp);
            return ResultData.success(temp);
        } else {
            return ResultData.fail("用户未登录");
        }
    }

}
