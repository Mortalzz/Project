package com.wdd.studentmanager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wdd.studentmanager.common.ResultData;
import com.wdd.studentmanager.domain.S_course;
import com.wdd.studentmanager.domain.S_selected_course;
import com.wdd.studentmanager.domain.S_student;
import com.wdd.studentmanager.service.CourseService;
import com.wdd.studentmanager.service.SelectedCourseService;
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
    SelectedCourseService selectedCourseService;

    @Autowired
    private JavaMailSender mailSender;

    @RequestMapping("/send")
    @ResponseBody
    public ResultData Email(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        S_student currentStu = (S_student) session.getAttribute("currentUser");

        boolean dormitYesorNot = false; // 没有注册宿舍
        boolean courseYesorNot = false; // 没有选课
        boolean normalYesorNot = false; // 基本信息没填完

        if (currentStu.getId() != null) {
            dormitYesorNot = true;
        }

        QueryWrapper<S_selected_course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("studentid", currentStu.getId());
        List<S_selected_course> user = selectedCourseService.list(queryWrapper);  // 查看是否选课
        if (user != null && !user.isEmpty()) {
            courseYesorNot = true;
        }

        if (currentStu.getMobile() != null && currentStu.getAddress() != null && currentStu.getPhoto() != null) {
            normalYesorNot = true;
        }

        if (!dormitYesorNot || !courseYesorNot || !normalYesorNot) {
            // 发送邮件通知
            try {
                sendEmailNotification(currentStu, dormitYesorNot, courseYesorNot, normalYesorNot);
            } catch (MessagingException e) {
                e.printStackTrace();
                return ResultData.fail("发送邮件通知失败。");
            }
        }

        return ResultData.success("如果需要，已发送邮件通知。");
    }

    private void sendEmailNotification(S_student student, boolean dormitYesorNot, boolean courseYesorNot, boolean normalYesorNot) throws MessagingException {
        String to = student.getQq();  // 假设QQ邮箱信息保存在这个字段中
        to = to + "@qq.com";

        String subject = "提醒：请完成您的注册信息";
        StringBuilder contentBuilder = new StringBuilder();

        if (!dormitYesorNot) {
            contentBuilder.append("您需要注册宿舍。\n");
        }
        if (!courseYesorNot) {
            contentBuilder.append("您需要完成选课。\n");
        }
        if (!normalYesorNot) {
            contentBuilder.append("您需要完善您的基本信息。\n");
        }

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