package com.wdd.studentmanager.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class S_student {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String sn;
    private String username;
    private String password;
    private int clazzid;
    private String sex;
    private String mobile;
    private String qq;
    private String photo;
    private String address;
    private Integer dormitid;

    public String getProvince(){
        return address;
    }
}
