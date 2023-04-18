package com.example.village_back.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class Cadre {
    @TableId(value = "id")
    private Integer id;
    private String name;
    private String gender;
    private Date birthday;
    private String phone;
    private String duty;
    private String post;
    private String idCard;
    private String cadresDesc;
    private String avatarUrl;
}
