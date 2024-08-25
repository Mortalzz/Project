package com.wdd.studentmanager.domain;

import lombok.Data;

@Data
public class S_dormit{
    private int id;
    private String build;
    private String room;
    private String dormitmanager;
    private String managerphone;
    private int takeincounts;
    private int status;
}
