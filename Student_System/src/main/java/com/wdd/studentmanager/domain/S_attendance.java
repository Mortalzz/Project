package com.wdd.studentmanager.domain;

import lombok.Data;

@Data
public class S_attendance {
    private int id;
    private int student_id;
    private String type;
    private String date;
}
