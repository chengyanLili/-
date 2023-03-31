package com.example.village_back.dao;

import com.example.village_back.controller.DTO.PopulationQueryDTO;
import com.example.village_back.entity.Population;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface PopulationDao {
    static int update(Population population) {
        return 0;
    }

    int insert(Population population) ;

    List<Population> findAll();

     List<Population> findPage(PopulationQueryDTO populationQueryDTO);

     long count(PopulationQueryDTO populationQueryDTO);
}
