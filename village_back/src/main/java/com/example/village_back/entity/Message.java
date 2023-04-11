package com.example.village_back.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class Message {
    @TableId(value = "id")
    private Integer id;
    private String theme;
    private String sendName;
    private String content;
    private Date sendTime;
    private Date replyTime;
    private String handleName;
    private String reply;
    private String msgStatus;
}
