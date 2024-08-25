package com.wdd.studentmanager.controller;

import cn.hutool.captcha.LineCaptcha;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wdd.studentmanager.common.ResultData;
import com.wdd.studentmanager.domain.S_admin;
import com.wdd.studentmanager.domain.S_clazz;
import com.wdd.studentmanager.domain.S_student;
import com.wdd.studentmanager.service.AdminService;
import com.wdd.studentmanager.service.ClazzService;
import com.wdd.studentmanager.service.CourseService;
import com.wdd.studentmanager.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ClazzService clazzService;
    @Autowired
    private AdminService adminService;
    public String code="";
    //注册申请
    @PostMapping("/register")
    @ResponseBody
    public ResultData add(HttpServletRequest request) {
        String password=request.getParameter("password");
        String qq=request.getParameter("qq");
        String sex=request.getParameter("sex");
        String sn=request.getParameter("sn");
        String username=request.getParameter("username");
        String clazz=request.getParameter("clazz");
        S_student student=new S_student();
        student.setPassword(password);
        student.setSn(sn);
        student.setUsername(username);
        student.setClazzid(Integer.valueOf(clazz));
        student.setQq(qq);
        student.setSex(sex);
        if (findBySn(sn) != null) {
            return ResultData.fail("学生已经存在，请勿重复添加");
        } else {
            QueryWrapper<S_clazz> clazzQueryWrapper=new QueryWrapper<>();
            clazzQueryWrapper.eq("id",student.getClazzid());
            S_clazz clazz1=clazzService.getOne(clazzQueryWrapper);
            if(clazz1!=null)
            {
                boolean save = studentService.save(student);
                if (save) {
                    return ResultData.success(save);
                } else {
                 return ResultData.fail("保存失败，请联系网站管理员");
                }
            }
            else
            {
                return ResultData.fail("注册失败！该专业不存在！");
            }
        }
    }//注册测试通过 测试人：邹正强

    private S_student findBySn(String sn) {
        QueryWrapper<S_student> wrapper = new QueryWrapper<>();
        wrapper.eq("sn", sn);
        return studentService.getOne(wrapper);
    }
    //----------------------------------------------------


    //------------------------------------------------
    //用户登录
    @PostMapping("/login")
    @ResponseBody
    public ResultData login(HttpServletRequest request){
        // 根据角色选择不同的登录处理逻辑
        String temp=request.getParameter("role");
        if ("student".equals( request.getParameter("role"))) {
            return loginStudent(request);
        } else if ("admin".equals(request.getParameter("role"))) {
            return loginAdmin(request);
        } else {
            return ResultData.fail("无效的角色");
        }
    }//登录测试通过 测试人：邹正强

    private ResultData loginStudent(HttpServletRequest request){
        if (!request.getParameter("captcha").equals(code)) {
            return ResultData.fail("验证码不正确，请检查后重新输入");
        }
        QueryWrapper<S_student> studentWrapper = new QueryWrapper<>();
        studentWrapper.eq("sn", request.getParameter("sn"));
        studentWrapper.eq("password", request.getParameter("password"));
        S_student student = studentService.getOne(studentWrapper);
        if (student != null) {
            HttpSession session = request.getSession(); // 假设在一个控制器方法中
            session.setAttribute("currentUser",student);
            return ResultData.success(student);
        } else {
            return ResultData.fail("学生用户不存在");
        }
    }

    private ResultData loginAdmin(HttpServletRequest request) {
        if (!request.getParameter("captcha").equals(code)) {
            return ResultData.fail("验证码不正确，请检查后重新输入");
        }
        QueryWrapper<S_admin> adminWrapper = new QueryWrapper<>();
        adminWrapper.eq("sn", request.getParameter("sn"));
        adminWrapper.eq("password", request.getParameter("password"));
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
    @GetMapping("/captcha")
    @ResponseBody
    public void getCaptcha(HttpServletResponse response) throws IOException {
        LineCaptcha lineCaptcha = new LineCaptcha(200, 100, 4, 150);
        // 将验证码的文本存储到会话中
        code=lineCaptcha.getCode();
        // 输出图片到前端
        response.setContentType("image/png");
        lineCaptcha.write(response.getOutputStream());
    }//验证码测试通过 测试人：邹正强

    //------------------------------------------------------------------------------------------
    //下面为显示和修改学生的信息

    @GetMapping("/get_profile")
    @ResponseBody
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


    @RequestMapping("/get_all")
    @ResponseBody
    public ResultData getall_Profile(HttpServletRequest request){
        List<S_student> s_students=studentService.list();
        return ResultData.success(s_students);
    }
    @RequestMapping("/set_profile")
    @ResponseBody
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
