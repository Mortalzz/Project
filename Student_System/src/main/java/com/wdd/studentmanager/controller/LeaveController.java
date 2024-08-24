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
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
@RequestMapping("/leave")
public class LeaveController {
    @Autowired
    LeaveService leaveService;

    @RequestMapping("/request")
    public ResultData Requestleave(HttpServletRequest request,String Info){
        HttpSession session=request.getSession();
        S_student currentStu = (S_student) session.getAttribute("currentUser");

        int stu_id=currentStu.getId();
        S_leave user = new S_leave();
        user.setStudentid(stu_id);
        user.setInfo(Info);
        user.setStatus(false);
        boolean save_leave=leaveService.save(user);
        if(save_leave==true){
            return ResultData.success("申请成功");
        }
        else{
            return ResultData.fail("申请请假失败");
        }
    }

    //查看请假状态
    @RequestMapping("/get")
    private ResultData Getleave(HttpServletRequest request){
        HttpSession session=request.getSession();
        S_student stu=(S_student) session.getAttribute("currentUser");

        int stu_id=stu.getId();
        QueryWrapper<S_leave> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("studentid", stu_id);
        List<S_leave> users = leaveService.list(queryWrapper);
        if(users==null){
            return ResultData.fail("没有请假信息");
        }
        else{
            return ResultData.success(users);
        }

    }

    //管理员回复
    @RequestMapping("/confirm")
    public ResultData confirmLeave(@RequestParam Map<String, String> remarks) {
        // 查询出所有 status 为 false 的记录
        QueryWrapper<S_leave> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", false);
        List<S_leave> leaves = leaveService.list(queryWrapper);

        // 遍历查询结果并更新 status 和 remark 字段
        for (S_leave leave : leaves) {
            leave.setStatus(true);  // 将 status 设置为 true
            // 根据不同的记录设置不同的 remark 值
            String customRemark = remarks.getOrDefault(String.valueOf(leave.getId()), "Info");
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
    }

}
