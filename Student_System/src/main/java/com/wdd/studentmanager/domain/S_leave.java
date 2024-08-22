package com.wdd.studentmanager.domain;

import lombok.Data;

@Data
public class S_leave {
    private int id;
    private int student_id;
    private String info;
    private boolean status;
    private String remark;
}
