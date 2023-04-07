package com.example.village_back.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@TableName(value = "floatPopulation")
@ToString
public class FloatPopulation {
    @TableId(value = "id")
    private Integer id;
    @Alias("姓名")
    private String name;
    @Alias("性别")
    private String gender;
    @Alias("电话号码")
    private String phone;
    @Alias("身份证号")
    private String idCard;
    @Alias("出生日期")
    private Date birthday;
    @Alias("何时来此")
    private Date whenCome;
    @Alias("暂住何户")
    private String witchFamily;
    @Alias("为何来此")
    private String whyCome;
}
