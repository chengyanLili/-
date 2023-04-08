package com.example.village_back.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("file")
public class Files {
    @TableId(value = "id")
    private Integer id;
    private String name;
    private String type;
    private Long size;
    private String url;
    private String md5;
}
