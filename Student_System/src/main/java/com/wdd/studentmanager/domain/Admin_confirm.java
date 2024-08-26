package com.wdd.studentmanager.domain;

import lombok.Data;

@Data
public class Admin_confirm {
    private String sn;
    private String username;
    private String timestart;
    private String timeend;
    private String info;
    private int remark;
}
