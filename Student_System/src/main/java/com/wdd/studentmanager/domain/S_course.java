package com.wdd.studentmanager.domain;

import lombok.Data;

@Data
public class S_course {
    private int id;
    private String name;
    private int teacherid;
    private String coursedate;
    private String info;
}
