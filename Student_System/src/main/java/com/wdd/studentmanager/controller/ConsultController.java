package com.wdd.studentmanager.controller;

import com.wdd.studentmanager.domain.S_student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ConsultController {
    @RequestMapping("/consult")
    @ResponseBody
    public String consult(HttpServletRequest request,String Infomation) {
        HttpSession session = request.getSession();
        S_student stu = (S_student) session.getAttribute("currentUser");

        if (stu != null) {
            String senderEmail = stu.getQq() + "@qq.com";
            String recipientEmail = "33429313157@qq.com"; // 替换为实际的收件人邮箱
            String subject = "在线咨询"; // 替换为邮件主题
            String body = Infomation; // 替换为邮件内容

            // 构建 mailto 链接
            String mailtoLink = "mailto:" + recipientEmail
                    + "?subject=" + encodeURIComponent(subject)
                    + "&body=" + encodeURIComponent(body);

            // 返回包含 mailto 链接的 HTML 页面
            return "<html><body><a href=\"" + mailtoLink + "\">Click here to send an email to " + recipientEmail + "</a></body></html>";
        } else {
            return "<html><body><p>Error: User not found</p></body></html>";
        }
    }

    // URL 编码方法
    private String encodeURIComponent(String value) {
        try {
            return java.net.URLEncoder.encode(value, "UTF-8");
        } catch (Exception e) {
            return "";
        }
    }
}

