package com.wdd.studentmanager.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wdd.studentmanager.common.ResultData;
import com.wdd.studentmanager.domain.S_course;
import com.wdd.studentmanager.domain.S_dormit;
import com.wdd.studentmanager.domain.S_student;
import com.wdd.studentmanager.service.DormitoryService;
import com.wdd.studentmanager.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
@Controller
@RequestMapping("/dormitory")
public class DormitoryController {

    @Autowired
    private StudentService studentService; // 服务层，用于处理学生操作
    @Autowired
    private DormitoryService dormitoryService; // 服务层，用于处理宿舍操作

    //申请宿舍
    @RequestMapping("/req_dormit")
    @ResponseBody
    public ResultData reqDormit(@RequestBody S_dormit dormit) {
        System.out.println(dormit);

        QueryWrapper<S_dormit> courseQuery = new QueryWrapper<>();
        courseQuery.eq("build",dormit.getBuild());
        courseQuery.eq("room",dormit.getRoom());
        S_dormit stuDormit = dormitoryService.getOne(courseQuery);//找出匹配的宿舍
        System.out.println(stuDormit);
        if(stuDormit==null){
            return ResultData.fail("宿舍不存在");
        }

        Integer stu_id=dormit.getStuId();
        System.out.println(stu_id);

        // 校验参数是否有效
        if ( stu_id== null ) {
            return ResultData.fail( "Student ID must be provided.");
        }
        //判断是否注册宿舍
        QueryWrapper<S_student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("id",stu_id);
        S_student currentStu = studentService.getOne(studentQueryWrapper);

        if(currentStu.getDormitid()!=null){
            return ResultData.fail("已经注册宿舍");
        }

        int dormitId=stuDormit.getId();//stuDormit为通过楼栋和房间匹配的
        // 更新学生的宿舍信息
        currentStu.setDormitid(dormitId);//currentStu是账号用户
        UpdateWrapper<S_student> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", currentStu.getId()).set("dormitid", currentStu.getDormitid());
        boolean result = studentService.update(updateWrapper); // 调用 update 方法
        if (result) {
            return ResultData.success("成功注册");
        } else {
            return ResultData.fail("Error!");
        }
    }//修改测试通过 测试者：邹正强

    @RequestMapping("/get_dormitInfo")
    @ResponseBody
    public ResultData getDormit(HttpServletRequest request){
        HttpSession session=request.getSession(false);
        S_student currentStu = (S_student) session.getAttribute("currentUser");
        Integer tmp_dormitId=currentStu.getDormitid();//tmp_dormit为获取学生的宿舍id
        System.out.println(tmp_dormitId);
        if(tmp_dormitId==null){
            return ResultData.fail("该学生暂未申请宿舍");
        }
        if(tmp_dormitId!=null){
            S_dormit tmp=dormitoryService.getById(tmp_dormitId);
            return ResultData.success(tmp);
        }
        else {
            return ResultData.fail("查找失败");
        }
    }//获取测试通过，测试人：邹正强


}