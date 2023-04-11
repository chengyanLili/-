package com.example.village_back.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Comment {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("评论内容")
    private String content;

    private Integer userId;

    private String time;

    @ApiModelProperty("父评论")
    private Integer pid;

    @ApiModelProperty("最上级评论")
    private Integer originId;

    private Integer noticeId;
}
