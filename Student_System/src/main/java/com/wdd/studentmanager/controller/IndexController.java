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

    @RequestMapping("/welcome")
    public String index_welcome(){
        return "/system/welcome";
    }

    @RequestMapping("/show")
    public String show(){
        return "index";
    }
}
