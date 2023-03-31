package com.example.village_back.mapper;

import com.example.village_back.entity.Population;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper

public interface PopulationMapper {

    @Select("select * from population")
    List<Population> findAll();

    @Insert("INSERT INTO `population`(name,phone,gender,age,idCard,householder,post,policy,education,inGroup,nation)\n" +
            "values(#{name},#{gender},#{phone},#{age},#{idCard},#{householder},#{post},#{policy},#{education},#{inGroup},#{nation})")
    int insert(Population population);

    int update(Population population);

    @Delete("delete from `population` where id = #{id}")
    int deleteById(@Param("id") Integer id);

    List<Population> selectPage(Integer pageNum, Integer pageSize,String name,String phone,String idCard);


    long selectTotal();
}
