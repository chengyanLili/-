package com.example.village_back.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
public class LandChange {
    @TableId(value = "id")
    private Integer id;
    private String notaryName;
    private String notaryPhone;
    private Date changeDay;
    private String changePlace;
    private String transferName;
    private String transferIntoName;
    private Integer changeNum;
    private String transferPhone;
    private String transferIntoPhone;
}
