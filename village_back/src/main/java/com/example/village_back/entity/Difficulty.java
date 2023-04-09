package com.example.village_back.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class Difficulty {
    @TableId(value = "id")
    private Integer id;
    private String name;
    private String gender;
    private String phone;
    private Date birthday;
    private String difficultReason;
    private String diseases;
    private String disabledCard;
}
