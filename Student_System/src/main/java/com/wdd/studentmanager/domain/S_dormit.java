package com.wdd.studentmanager.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class S_dormit{
    private int id;
    private String build;
    private String room;
    private String dormitmanager;
    private String managerphone;

    @TableField(exist = false)
    private Integer stuId;
}
