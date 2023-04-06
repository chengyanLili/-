package com.example.village_back.mapper;

import com.example.village_back.entity.Family;
import com.example.village_back.entity.FloatPopulation;
import com.example.village_back.entity.Population;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FloatMapper {
    @Select("select * from floatPopulation")
    List<FloatPopulation> findAll();

    @Insert("INSERT INTO `floatPopulation`(name,gender,phone,birthday,when,where,why)\n" +
            "values(#{name},#{gender},#{phone},#{birthday},#{when},#{where},#{why})")

    int insert(FloatPopulation floatPopulation);

    int update(FloatPopulation floatPopulation);

    @Delete("delete from `floatPopulation` where id = #{id}")
    int delete(@Param("id") Integer id);

    //    批量删除
    Integer deleteBatch(@Param("ids") List<Long> ids);

    List<FloatPopulation> selectPage(Integer pageNum, Integer pageSize, String name, String phone, String idCard);

    long selectTotal();
}
