package com.example.village_back.entity;

import lombok.Data;

@Data

public class User {
    private Integer id;
    private String username;
    private String password;
    private Integer identify;
//    private String phone;
}
