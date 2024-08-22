package com.wdd.studentmanager.domain;

import lombok.Data;

@Data
public class Login {
    private String sn;
    private String code;
    private String password;
    private String role;

}
