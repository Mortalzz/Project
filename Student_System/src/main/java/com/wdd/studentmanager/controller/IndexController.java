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
}
