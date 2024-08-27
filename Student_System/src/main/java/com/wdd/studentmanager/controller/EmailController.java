package com.wdd.studentmanager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wdd.studentmanager.common.ResultData;
import com.wdd.studentmanager.domain.S_course;
import com.wdd.studentmanager.domain.S_selected_course;
import com.wdd.studentmanager.domain.S_student;
import com.wdd.studentmanager.service.CourseService;
import com.wdd.studentmanager.service.SelectedCourseService;
import com.wdd.studentmanager.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;  // 使用正确的包
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/email")
public class EmailController {

    @Autowired
    CourseService courseService;

    @Autowired
    StudentService studentService;

    @Autowired
    SelectedCourseService selectedCourseService;

    @Autowired
    private JavaMailSender mailSender;

    @RequestMapping("sendall")
    @ResponseBody
    public ResultData sendall(HttpServletRequest request) throws MessagingException {
        String info=request.getParameter("info");
        System.out.println(info);
        List<S_student> list=studentService.list();
        for(S_student item:list){
            try {
                sendEmailNotification(item.getQq(),info);
            } catch (MessagingException e) {
                e.printStackTrace();
                return ResultData.fail("发送邮件通知失败。");
            }
        }
        return ResultData.success("批量发送成功！");
    }


    @RequestMapping("/info_lost")
    @ResponseBody
    public ResultData info_lost(HttpServletRequest request){
        String qq=request.getParameter("qq");
        System.out.println(qq);
        try {
            sendEmailNotification(qq,"亲爱的重庆大学同学，你的身份信息不完善，请尽快完善，感谢配合");
        } catch (MessagingException e) {
            e.printStackTrace();
            return ResultData.fail("发送邮件通知失败。");
        }
        return ResultData.success("信息提醒成功！");
    }

    @RequestMapping("/course_lost")
    @ResponseBody
    public ResultData course_lost(HttpServletRequest request){
        String qq=request.getParameter("qq");
        try {
            sendEmailNotification(qq,"亲爱的重庆大学同学，你尚未选课，请尽快完成选课，感谢配合");
        } catch (MessagingException e) {
            e.printStackTrace();
            return ResultData.fail("发送邮件通知失败。");
        }
        return ResultData.success("信息提醒成功！");
    }


    private void sendEmailNotification(String to,String info) throws MessagingException {
        // 假设QQ邮箱信息保存在to这个字段中
        to = to + "@qq.com";
        String subject = "提醒：请完成您官网的相关信息";//主题
        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append(info);//信息
        String content = contentBuilder.toString();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("3342931517@qq.com");  // 替换为您的QQ邮箱
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, false);  // false表示内容是纯文本格式
        mailSender.send(message);
    }


}