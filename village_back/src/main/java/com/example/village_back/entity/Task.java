package com.example.village_back.entity;
import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@TableName(value = "task")
@ToString
public class Task {
    @TableId(value = "id")
    private Integer id;
    @Alias("发布者姓名")
    private String publishName;

    private String progress;
    @Alias("主题")
    private String title;
    private String content;

    private String createTime;

    private Date finishTime;
}
