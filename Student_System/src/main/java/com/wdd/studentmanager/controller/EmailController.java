package com.wdd.studentmanager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wdd.studentmanager.common.ResultData;
import com.wdd.studentmanager.domain.S_course;
import com.wdd.studentmanager.domain.S_leave;
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
import org.springframework.web.bind.annotation.RequestBody;
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
    StudentService studentService;

    @Autowired
    CourseService courseService;

    @Autowired
    SelectedCourseService selectedCourseService;

    @Autowired
    private JavaMailSender mailSender;

    @RequestMapping("/send")
    @ResponseBody
    public ResultData Email(@RequestBody S_student student) throws MessagingException {
        QueryWrapper<S_student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("qq",student.getQq());
        S_student tmp=studentService.getOne(queryWrapper);
        System.out.println(tmp);
        sendEmailNotification(tmp);//通过qq号找到的学生
        return ResultData.success("已发送邮件通知");
    }

    private void sendEmailNotification(S_student student) throws MessagingException {
        String to = student.getQq();  // 假设QQ邮箱信息保存在这个字段中
        to = to + "@qq.com";//地址

        String subject = "您的密码：";
        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append(student.getPassword());
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