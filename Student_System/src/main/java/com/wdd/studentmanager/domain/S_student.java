package com.wdd.studentmanager.domain;

import lombok.Data;

@Data
public class S_student {
    private Integer id;
    private String sn;
    private String username;
    private String password;
    private int clazz_id;
    private String sex;
    private String mobile;
    private String qq;
    private String photo;
    private String address  ;
    private Integer dormit_id;
}
