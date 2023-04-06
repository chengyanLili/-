package com.example.village_back.entity;
import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@TableName(value = "population")
@ToString
public class Family {

    @TableId(value = "familyId")
    private Integer familyId;
    private String relation;
    private String memberIdCard;
    private String phone;
    private String memberName;
    @Alias("户主姓名")
    private String householdName;
    @Alias("家庭人口数")
    private Integer familyNum;
    @Alias("户号")
    private Integer accountNo;
    @Alias("户主电话号码")
    private String householdPhone;
    @Alias("所在小组")
    private String inGroup;
    @Alias("户主身份号")
    private String householdIdCard;
    private Date birthday;
}

