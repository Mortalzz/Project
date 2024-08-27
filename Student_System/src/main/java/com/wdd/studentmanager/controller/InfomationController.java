package com.wdd.studentmanager.controller;

import com.wdd.studentmanager.domain.S_student;
import com.wdd.studentmanager.mapper.StudentMapper;
import com.wdd.studentmanager.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/Bigdata")

public class InfomationController {
    @Autowired
    StudentMapper studentMapper;

    @RequestMapping("/province")
    public Map<String,Long> getProvince(){
        List<S_student> students = studentMapper.getAllStudents(); // 假设有一个方法来获取所有学生
        return students.stream()
                .collect(Collectors.groupingBy(S_student::getProvince, Collectors.counting()));
    }
}
