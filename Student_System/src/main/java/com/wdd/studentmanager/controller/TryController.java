package com.wdd.studentmanager.controller;

import com.wdd.studentmanager.domain.S_student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@ResponseBody
@RequestMapping("/try")
public class TryController {
    @RequestMapping("/test")
    public S_student test(HttpServletRequest request){
        HttpSession session=request.getSession(false);
        return (S_student) session.getAttribute("currentUser");
    }
}
