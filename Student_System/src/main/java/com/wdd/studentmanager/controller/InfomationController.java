package com.wdd.studentmanager.controller;

import com.wdd.studentmanager.domain.S_dormit;
import com.wdd.studentmanager.domain.S_leave;
import com.wdd.studentmanager.domain.S_student;
import com.wdd.studentmanager.mapper.DormitoryMapper;
import com.wdd.studentmanager.mapper.LeaveMapper;
import com.wdd.studentmanager.mapper.StudentMapper;
import com.wdd.studentmanager.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/Bigdata")

public class InfomationController {
    @Autowired
    StudentMapper studentMapper;

    @Autowired
    DormitoryMapper dormitoryMapper;

    @Autowired
    LeaveMapper leaveMapper;

    @RequestMapping("/province")
    public Map<String, Long> getProvince() {
        List<S_student> students = studentMapper.getAllStudents(); // 假设有一个方法来获取所有学生
        return students.stream()
                .collect(Collectors.groupingBy(S_student::getAddress, Collectors.counting()));
    }

    @RequestMapping("/sex")
    public Map<String, Long> getSex() {
        List<S_student> students = studentMapper.getAllStudents();
        return students.stream()
                .collect(Collectors.groupingBy(S_student::getSex, Collectors.counting()));
    }

    @RequestMapping("/dormit")
    public List<Map<String, Object>> getDormit() {
        // 获取所有宿舍数据
        List<S_dormit> dormits = dormitoryMapper.getAlldormit();

        // 创建一个新的列表以存储结果
        List<Map<String, Object>> result = new ArrayList<>();

        // 遍历所有宿舍数据，将需要的字段提取到 Map 中
        for (S_dormit dormit : dormits) {
            Map<String, Object> map = new HashMap<>();
            map.put("build", dormit.getBuild());
            map.put("takeincounts", dormit.getTakeincounts());
            result.add(map);
        }

        // 返回包含所需字段的列表
        return result;
    }

    @RequestMapping("/leave")
    public List<Map<String, Object>> getLeave() {
        List<S_leave> leaves = leaveMapper.getAllleaves();
        List<Map<String, Object>> result = new ArrayList<>();

        for (S_leave leave : leaves) {
            // 获取学生名字
            String studentName = studentMapper.getNameById(leave.getStudentid());

            // 创建一个Map来存储每个请假记录
            Map<String, Object> leaveData = new HashMap<>();
            leaveData.put("timestart", leave.getTimestart());
            leaveData.put("timeend", leave.getTimeend());
            leaveData.put("phone", leave.getPhone());
            leaveData.put("name", studentName);
            result.add(leaveData);
        }
        return result;
    }

    @RequestMapping("/major")
    public Map<String,Long> getMajor(){
        List<S_student> students=studentMapper.getAllStudents();
        return students.stream()
                .collect(Collectors.groupingBy(S_student::getClazzid, Collectors.counting()));
    }

}
