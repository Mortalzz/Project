package com.wdd.studentmanager.domain;

import lombok.Data;

@Data
public class S_course {
    private int id;
    private String name;//课程名称
    private String teachername;
    private String coursedate;
    private String courseplace;
    private String info;
}
