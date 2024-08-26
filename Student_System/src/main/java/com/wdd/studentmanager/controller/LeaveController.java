package com.wdd.studentmanager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wdd.studentmanager.common.ResultData;
import com.wdd.studentmanager.domain.Admin_confirm;
import com.wdd.studentmanager.domain.S_leave;
import com.wdd.studentmanager.domain.S_student;
import com.wdd.studentmanager.service.LeaveService;
import com.wdd.studentmanager.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/leave")
public class LeaveController {
    @Autowired
    LeaveService leaveService;

    @Autowired
    StudentService studentService;
    @RequestMapping("/request")
    @ResponseBody
    public ResultData Requestleave(HttpServletRequest request){
        HttpSession session=request.getSession(false);
        S_student student=(S_student) session.getAttribute("currentUser");
        String datestart=request.getParameter("datestart");
        String dateend=request.getParameter("dateend");
        String reason=request.getParameter("reason");
        String phone=request.getParameter("phone");
        S_leave leave=new S_leave();
        leave.setStudentid(student.getId());
        leave.setTimestart(datestart);
        leave.setTimeend(dateend);
        leave.setInfo(reason);
        leave.setPhone(phone);
        leave.setStatus(false);
        leave.setRemark(0);
        boolean save_leave=leaveService.save(leave);
        if(save_leave==true){
            return ResultData.success("申请成功");
        }
        else{
            return ResultData.fail("申请请假失败");
        }
    }//测试通过 测试人：邹正强

    //查看请假状态
    @RequestMapping("/get")
    @ResponseBody
    private ResultData Getleave(HttpServletRequest request){
        HttpSession session=request.getSession();
        S_student stu=(S_student) session.getAttribute("currentUser");

        int stu_id=stu.getId();
        QueryWrapper<S_leave> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("studentid", stu_id);
        List<S_leave> users = leaveService.list(queryWrapper);
        if(users==null){
            return ResultData.fail("没有请假信息");
        }
        else{
            return ResultData.success_leave(users);
        }

    }//测试通过 测试人：邹正强

    //管理员回复
    @RequestMapping("/get_allconfirm")
    @ResponseBody
    public ResultData confirmLeave(HttpServletRequest request) {
        QueryWrapper<S_leave> s_leaveQueryWrapper=new QueryWrapper<>();
        s_leaveQueryWrapper.eq("status",0);
        List<S_leave> s_leaves=leaveService.list(s_leaveQueryWrapper);
        List<Admin_confirm> admin_confirms=new ArrayList<>();
        for(S_leave temp:s_leaves){
            QueryWrapper<S_student> s_studentQueryWrapper=new QueryWrapper<>();
            s_studentQueryWrapper.eq("id",temp.getStudentid());
            S_student s_student=studentService.getOne(s_studentQueryWrapper);
            Admin_confirm t=new Admin_confirm();
            t.setSn(s_student.getSn());
            t.setUsername(s_student.getUsername());
            t.setTimestart(temp.getTimestart());
            t.setTimeend(temp.getTimeend());
            t.setInfo(temp.getInfo());
            t.setRemark(temp.getRemark());
            admin_confirms.add(t);
        }
        if(admin_confirms!=null){
            return ResultData.success_admin_confirm(admin_confirms);
        }
        else {
            return ResultData.fail("暂无请假信息");
        }
    }//测试通过 测试人：邹正强


    @RequestMapping("/confirm")
    @ResponseBody
    public ResultData confirm(HttpServletRequest request){
        String sn=request.getParameter("sn");
        String remark=request.getParameter("remark");
        System.out.println(remark);
        int flag=Integer.valueOf(remark);
        QueryWrapper<S_student> s_studentQueryWrapper=new QueryWrapper<>();
        s_studentQueryWrapper.eq("sn",sn);
        S_student student=studentService.getOne(s_studentQueryWrapper);
        int id=student.getId();
        UpdateWrapper<S_leave> s_leaveUpdateWrapper=new UpdateWrapper<>();
        s_leaveUpdateWrapper.eq("studentid",id).set("remark",flag).set("status",1);
        boolean result=leaveService.update(s_leaveUpdateWrapper);
        if(result){
            return ResultData.success("状态更新成功！");
        }
        else {
            return ResultData.fail("状态更新失败");
        }
    }
}
