package com.example.village_back.entity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;

@Data
public class Population {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String gender;
    private String phone;
    private Integer age;
    private String nation;
    private String idCard;
    private Integer householder;
    private String post;
    private String policy;
    private String education;
    private String inGroup;
}
