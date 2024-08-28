package com.wdd.studentmanager.controller;

import com.wdd.studentmanager.common.ResultData;
import com.wdd.studentmanager.domain.S_dormit;
import com.wdd.studentmanager.domain.S_leave;
import com.wdd.studentmanager.domain.S_student;
import com.wdd.studentmanager.mapper.DormitoryMapper;
import com.wdd.studentmanager.mapper.LeaveMapper;
import com.wdd.studentmanager.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
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
    @ResponseBody
    public ResultData getProvince() {
        List<S_student> students = studentMapper.getAllStudents(); // 假设有一个方法来获取所有学生
        String[] provinces = {
                "北京", "天津", "河北", "山西", "内蒙古",
                "辽宁", "吉林", "黑龙江", "上海", "江苏",
                "浙江", "安徽", "福建", "江西", "山东",
                "河南", "湖北", "湖南", "广东", "广西",
                "海南", "重庆", "四川", "贵州", "云南",
                "西藏", "陕西", "甘肃", "青海", "宁夏",
                "新疆","香港","澳门","台湾"
        };
        // 初始化Map，所有值为0
        Map<String, Long> provinceMap = new HashMap<>();
        Arrays.stream(provinces).forEach(province -> provinceMap.put(province, 0L));
        for(S_student province:students){
            String temp=province.getAddress();
            provinceMap.put(temp,provinceMap.get(temp)+1);
        }
        return ResultData.success(provinceMap);
    }

    @RequestMapping("/sex")
    @ResponseBody
    public ResultData getSex() {
        List<S_student> students = studentMapper.getAllStudents();
        Map<String,Long> tmp=students.stream()
                .collect(Collectors.groupingBy(S_student::getSex, Collectors.counting()));
        return ResultData.success(tmp);
    }
//宿舍每栋人数
    @RequestMapping("/dormit")
    @ResponseBody
    //List<Map<String, Object>>
    public ResultData getDormit() {
        // 获取所有宿舍数据
        List<S_dormit> dormits = dormitoryMapper.getAlldormit();
        String[] provinces = {
                "梅园","兰园","竹园","松园"
        };
        Map<String, Long> provinceMap = new HashMap<>();
        Arrays.stream(provinces).forEach(province -> provinceMap.put(province, 0L));
        for(S_dormit dormit:dormits){
            String temp=dormit.getBuild();
            provinceMap.put(temp, (long) dormit.getTakeincounts());
        }
        return ResultData.success(provinceMap);
    }
//请假记录
    @RequestMapping("/leave")
    //List<Map<String, Object>>
    @ResponseBody
    public ResultData getLeave() {
        List<S_leave> leaves = leaveMapper.getAllleaves();
        List<Map<String, Object>> result = new ArrayList<>();
        for (S_leave leave : leaves) {
            // 获取学生名字
            String studentName = studentMapper.getNameById(leave.getStudentid());

            // 创建一个Map来存储每个请假记录
            Map<String, Object> leaveData = new HashMap<>();
            leaveData.put("timestart", leave.getTimestart());
            leaveData.put("timeend", leave.getTimeend());
            leaveData.put("name", studentName);
            result.add(leaveData);
        }
        return ResultData.success(result);
    }
//各专业人数
    @RequestMapping("/major")
    @ResponseBody
    public ResultData getMajor(){
        List<S_student> students = studentMapper.getAllStudents();
        Map<String,Long> tmp=students.stream()
                .collect(Collectors.groupingBy(S_student::getClazzid, Collectors.counting()));
        long temp=0;
        for (String key : tmp.keySet()) {
            if(!key.equals("软件工程专业")&&!key.equals("数学专业")&&!key.equals("计算机专业")&&!key.equals("机械专业")&&!key.equals("土木专业"))
                temp+=1;
        }
        tmp.put("其他专业",temp);
        return ResultData.success(tmp);
    }
//学生的数量
    @RequestMapping("/allstu")
    @ResponseBody
    public ResultData getall(){
        List<S_student> students=studentMapper.getAllStudents();
        int sum=students.size();
        return ResultData.success(sum);
    }

}
