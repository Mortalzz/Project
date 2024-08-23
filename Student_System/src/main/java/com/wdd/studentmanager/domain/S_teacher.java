package com.wdd.studentmanager.domain;

import lombok.Data;

@Data
public class S_teacher {
    private int id;
    private String sn;
    private String username;
    private String password;
    private int clazzid;
    private String mobile;
    private String qq;
    private String sex;
    private String photo;
}
