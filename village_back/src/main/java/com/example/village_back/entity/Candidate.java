package com.example.village_back.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Candidate {
    private Integer id;
    private String name;
    private String gender;
    private Date birthday;
    private String voteType;
    private String phone;
    private String avatarUrl;
    private String experience;
    private Integer votes;
}
