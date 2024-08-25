package com.wdd.studentmanager.controller;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wdd.studentmanager.common.BindingResultUtil;
import com.wdd.studentmanager.common.ResultData;
import com.wdd.studentmanager.domain.Login;
import com.wdd.studentmanager.domain.S_admin;
import com.wdd.studentmanager.domain.S_student;
import com.wdd.studentmanager.domain.UserContext;
import com.wdd.studentmanager.service.AdminService;
import com.wdd.studentmanager.service.StudentService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;


@Controller
@CrossOrigin
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private AdminService adminService;
    public String code="";
    //注册申请
    @PostMapping("/register")
    @ResponseBody
    public ResultData add(HttpServletRequest request) throws JSONException {
        StringBuilder jsonStringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonStringBuilder.append(line);
            }
        } catch (IOException e) {
            return ResultData.fail("读取请求数据失败");
        }

        // 解析 JSON 数据
        String jsonString = jsonStringBuilder.toString();
        JSONObject jsonObject = new JSONObject(jsonString);

        String username = jsonObject.getString("username");
        String sn = jsonObject.getString("sn");
        String password = jsonObject.getString("password");
        String sex = jsonObject.getString("sex");
        int clazzid = jsonObject.getInt("clazzid");
        String qq = jsonObject.getString("qq");

        S_student student=new S_student();
        student.setPassword(password);
        student.setSn(sn);
        student.setUsername(username);
        student.setQq(qq);
        student.setClazzid(clazzid);
        student.setSex(sex);
        System.out.println(student);
        if (findBySn(sn) != null) {
            return ResultData.fail("学生已经存在，请勿重复添加");
        } else {
            boolean save = studentService.save(student);
            if (save) {
                System.out.println("保存成功");
                return ResultData.success(save);
            } else {
                return ResultData.fail("保存失败，请联系网站管理员");
            }
        }
    }//注册测试通过 测试人：邹正强

    private S_student findBySn(String sn) {
        QueryWrapper<S_student> wrapper = new QueryWrapper<>();
        wrapper.eq("sn", sn);
        return studentService.getOne(wrapper);
    }
//----------------------------------------------------
//测试用的
/*    //需要更改
    @RequestMapping("/index")
    public String index(){
        return "system/index";
    }

    @RequestMapping("/index/login")
    public String index_login(){
        return  "login&regist";
    }
*/
//------------------------------------------------
    //用户登录
@PostMapping("/login")
@ResponseBody
public ResultData login(HttpServletRequest request) throws JSONException {
    StringBuilder jsonStringBuilder = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8))) {
        String line;
        while ((line = reader.readLine()) != null) {
            jsonStringBuilder.append(line);
        }
    } catch (IOException e) {
        e.printStackTrace();
        return ResultData.fail("读取请求数据失败: " + e.getMessage());
    }

    String jsonString = jsonStringBuilder.toString();
    System.out.println("接收到的 JSON 数据: " + jsonString); // 日志输出接收到的数据

    JSONObject jsonObject;
    try {
        jsonObject = new JSONObject(jsonString);
    } catch (JSONException e) {
        e.printStackTrace();
        return ResultData.fail("JSON 解析失败: " + e.getMessage());
    }

    String role = jsonObject.getString("role");
    if ("student".equals(role)) {
        return loginStudent(jsonObject,request);
    } else if ("admin".equals(role)) {
        return loginAdmin(jsonObject);
    } else {
        return ResultData.fail("无效的角色");
    }
}


    private ResultData loginStudent(JSONObject jsonObject,HttpServletRequest request) throws JSONException {


        QueryWrapper<S_student> studentWrapper = new QueryWrapper<>();
        studentWrapper.eq("sn", jsonObject.getString("sn"));
        studentWrapper.eq("password", jsonObject.getString("password"));
        S_student student = studentService.getOne(studentWrapper);
        if (student != null) {
            System.out.println(request);
            HttpSession session = request.getSession();
            session.setAttribute("currentUser",student);
            System.out.println(session);
            //UserContext.setUser(jsonObject);//使用UserContext 类存储用户信息
            return ResultData.success(student);
        } else {
            return ResultData.fail("学生用户不存在");
        }
    }

    private ResultData loginAdmin(JSONObject jsonObject) throws JSONException {
        QueryWrapper<S_admin> adminWrapper = new QueryWrapper<>();
        adminWrapper.eq("sn", jsonObject.getString("sn"));
        adminWrapper.eq("password", jsonObject.getString("password"));
        S_admin admin = adminService.getOne(adminWrapper);

        if (admin != null) {
            //HttpSession session = request.getSession();
            //session.setAttribute("currentUser", admin);
            UserContext.setUser(jsonObject);
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
    public ResultData getProfile(HttpServletRequest request){
    //JSONObject currentStu=UserContext.getUser();
        System.out.println(request);
        HttpSession session = request.getSession(false);
        /*if (session == null) {
            System.out.println("会话不存在");
            return ResultData.fail("会话不存在");
        }*/
        S_student currentStu = (S_student) session.getAttribute("currentUser");
        System.out.println(currentStu);
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
    public ResultData setProfile(@RequestBody S_student student /*,HttpServletRequest request*/){
        //HttpSession session=request.getSession(false);
        //S_student currentStu=(S_student) session.getAttribute("currentUser");

        //S_student temp=student;

        if (student != null) {
            // 设置用户详细信息

            //temp.setId(student.getId());
            boolean result=studentService.updateById(student);
            return ResultData.success(student);
        } else {
            return ResultData.fail("用户未登录");
        }
    }

}
