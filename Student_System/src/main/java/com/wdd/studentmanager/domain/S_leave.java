package com.wdd.studentmanager.domain;

import lombok.Data;

@Data
public class S_leave {
    private int id;
    private int studentid;
    private String info;
    private boolean status;
    private int remark;
    private String timestart;
    private String timeend;
    private String phone;
}
