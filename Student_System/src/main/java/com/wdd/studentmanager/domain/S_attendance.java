package com.wdd.studentmanager.domain;

import lombok.Data;

@Data
public class S_attendance {
    private int id;
    private int studentid;
    private String type;
    private String date;
}
