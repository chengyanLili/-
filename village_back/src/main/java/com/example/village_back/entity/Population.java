package com.example.village_back.entity;
import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@TableName(value = "population")
@ToString
public class Population {

    @TableId(value = "id")
    private Integer id;
    @Alias("姓名")
    private String name;
    @Alias("性别")
    private String gender;
    @Alias("电话号码")
    private String phone;
    @Alias("出生日期")
    private Date birthday;
    @Alias("民族")
    private String nation;
    @Alias("身份证号")
    private String idCard;
    @Alias("是否是户主")
    private Integer householder;
    @Alias("职位")
    private String post;
    @Alias("政治面貌")
    private String policy;
    @Alias("学历")
    private String education;
    @Alias("所在小组")
    private String inGroup;
}
