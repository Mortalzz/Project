package com.wdd.studentmanager.domain;

import lombok.Data;

@Data
public class Login {
    private String id_card;
    private String code;
    private String password;

}
