package com.example.village_back.controller.dao;

import lombok.Data;

/**
 * 接收前端登录请求的注释
 */
@Data
public class UserDTO {
    private String username;
    private String password;
    private Integer identify;
    private String avatar;

}
