package com.wdd.studentmanager.domain;

import lombok.Data;

@Data
public class Login {
    private String sn;
    private String captcha;
    private String password;
    private String role;
}
