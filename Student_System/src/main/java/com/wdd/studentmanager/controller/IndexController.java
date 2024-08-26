package com.wdd.studentmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class IndexController {

    @RequestMapping("/login")
    public String index_login(){
        return  "login&regist";
    }

    @RequestMapping("/show")
    public String show(){
        return "view";
    }

    @RequestMapping("/index")
    public String index(){
        return "/system/index";
    }

    @RequestMapping("/view")
    public String view(){
        return "view";
    }

    @RequestMapping("/email")
    public String email(){
        return "/system/email";
    }

    @RequestMapping("/stu_list")
    public String list(){
        return "/student/studentList";
    }

    @RequestMapping("stu_get_all")
    public String stu_all(){
        return "/student/adminStudentList";
    }

    @RequestMapping("/course_list")
    public String list_course(){
        return "/course/courseList";
    }

    @RequestMapping("set_stu_profile")
    public String set_profile(){
        return "/student/studentEdit";
    }

    @RequestMapping("change_password")
    public String change_password(){
        return "/system/personnalView";
    }

    @RequestMapping("/dormit_list")
    public String dormit_list(){
        return "/dormit/dormitList";
    }

    @RequestMapping("/select_course")
    public String select_course(){
        return "/course/selectCourseList";
    }

    @RequestMapping("/req_leave")
    public String req_leave(){
        return "/system/leavestudent1";
    }

    @RequestMapping("/lea_req_slt")
    public String leave_request_select(){
        return "/system/leavestudent2";
    }

    @RequestMapping("/admin_leave")
    public String admin_leave(){
        return "/system/leaveadmin";
    }

    @RequestMapping("/admin_dormitlist")
    public String admin_dormitlist(){
        return "/dormit/adminDormitList";
    }
}
