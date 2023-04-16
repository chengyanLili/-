package com.example.village_back.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Date;

@Data
public class Users {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String gender;
    private Integer identify;
    private String avatarUrl;
    private String address;
    private String phone;
    private Date birthday;
    private String nickName;
}
