package com.wdd.studentmanager.domain;

import lombok.Data;

@Data
public class S_course {
    private int id;
    private String name;
    private int teacher_id;
    private String course_date;
    private String info;
}
