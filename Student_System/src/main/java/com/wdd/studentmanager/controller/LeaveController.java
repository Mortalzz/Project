package com.wdd.studentmanager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wdd.studentmanager.common.ResultData;
import com.wdd.studentmanager.domain.S_leave;
import com.wdd.studentmanager.domain.S_student;
import com.wdd.studentmanager.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/leave")
public class LeaveController {
    @Autowired
    LeaveService leaveService;
    @RequestMapping("/request")
    @ResponseBody
    public ResultData Requestleave(HttpServletRequest request){
        HttpSession session=request.getSession(false);
        S_student student=(S_student) session.getAttribute("currentUser");
        String datestart=request.getParameter("datestart");
        System.out.println(datestart);
        String dateend=request.getParameter("dateend");
        System.out.println(dateend);
        String reason=request.getParameter("reason");
        String phone=request.getParameter("phone");
        S_leave leave=new S_leave();
        leave.setStudentid(student.getId());
        leave.setTimestart(datestart);
        leave.setTimeend(dateend);
        leave.setInfo(reason);
        leave.setPhone(phone);
        leave.setStatus(false);
        System.out.println(leave);
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
    @RequestMapping("/confirm")
    @ResponseBody
    public ResultData confirmLeave(@RequestParam Map<String, String> remarks) {
        // 查询出所有 status 为 false 的记录
        QueryWrapper<S_leave> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", false);
        List<S_leave> leaves = leaveService.list(queryWrapper);

        // 遍历查询结果并更新 status 和 remark 字段
        for (S_leave leave : leaves) {
            leave.setStatus(true);  // 将 status 设置为 true
            // 根据不同的记录设置不同的 remark 值
            String customRemark = remarks.getOrDefault(String.valueOf(leave.getId()),"无备注");
            leave.setRemark(customRemark);
        }

        // 批量更新记录
        boolean updateResult = leaveService.updateBatchById(leaves);

        // 根据更新结果返回响应
        if (updateResult) {
            return ResultData.success("批量更新成功");
        } else {
            return ResultData.success("批量更新失败");
        }
    }//测试通过 测试人：邹正强

}
