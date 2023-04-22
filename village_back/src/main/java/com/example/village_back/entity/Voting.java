package com.example.village_back.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class Voting {
    private Integer id;
    private String voteType;
    private String duty;
    private String year;
    private String notice;
    private Date startTime;
    private Date endTime;
}
