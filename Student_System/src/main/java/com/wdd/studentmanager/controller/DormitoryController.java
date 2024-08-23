package com.wdd.studentmanager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wdd.studentmanager.common.ResultData;
import com.wdd.studentmanager.domain.S_dormit;
import com.wdd.studentmanager.domain.S_student;
import com.wdd.studentmanager.service.DormitoryService;
import com.wdd.studentmanager.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
@Controller
@ResponseBody
@RequestMapping("/dormitory")
public class DormitoryController {

    @Autowired
    private StudentService studentService; // 服务层，用于处理学生操作

    @Autowired
    private DormitoryService dormitoryService; // 服务层，用于处理宿舍操作

    @RequestMapping("/regi_dormit")
    @ResponseBody
    public ResultData regDormit(HttpServletRequest request, @RequestParam("dormitoryId") Integer dormitoryId) {
        HttpSession session=request.getSession(false);
        S_student currentStu = (S_student) session.getAttribute("currentUser");

        // 校验参数是否有效
        if ( currentStu.getId()== null || dormitoryId == null) {
            return ResultData.fail( "Student ID and Dormitory ID must be provided.");
        }

        if(currentStu.getDormit_id()!=null){
            return ResultData.fail("已经注册宿舍");
        }
        // 更新学生的宿舍信息
        currentStu.setDormit_id(dormitoryId);
        return ResultData.success("Dormitory assigned successfully.");
    }

    @RequestMapping("/get_dormitInfo")
    public ResultData getDormit(HttpServletRequest request){
        HttpSession session=request.getSession(false);
        S_student currentStu = (S_student) session.getAttribute("currentUser");
        Integer tmp_dormitId=currentStu.getDormit_id();//tmp_dormit为获取学生的宿舍id
        if(tmp_dormitId==null){
            return ResultData.fail("该学生暂未申请宿舍");
        }

        if(findByID(tmp_dormitId)!=null){
            S_dormit tmp=findByID(tmp_dormitId);
            return ResultData.success(tmp);
        }

        return ResultData.fail("查找失败");
    }

    private S_dormit findByID(Integer id) {
        QueryWrapper<S_dormit> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        return dormitoryService.getOne(wrapper);
    }


}

