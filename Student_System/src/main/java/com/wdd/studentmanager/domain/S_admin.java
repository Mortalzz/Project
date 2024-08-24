package com.wdd.studentmanager.domain;

import lombok.Data;

@Data
public class S_admin {
    private int id;
    private String sn;
    private String password;
    private int status;
}
