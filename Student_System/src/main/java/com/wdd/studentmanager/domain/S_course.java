package com.wdd.studentmanager.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

@Data
public class S_course {
    private int id;
    private String name;//课程名称
    private String teachername;
    private String coursedate;
    private String courseplace;
    private String info;
    @TableField(exist = false)
    private int stuId;
}
