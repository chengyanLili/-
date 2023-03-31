package com.example.village_back.controller.DTO;
//用来放前端传递过来的参数:接口参数比较多的情况下使用

import lombok.Data;

@Data
public class PopulationQueryDTO {
    private Integer currentPage;
    private Integer pageNum;
    private Integer pageSize;
    private String name;
    private String phone;
    private String idCard;

}
