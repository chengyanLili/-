package com.example.village_back.mapper;

import com.example.village_back.entity.Cadre;
import com.example.village_back.entity.FloatPopulation;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CadreMapper {

    @Insert("INSERT INTO `cadre`(name,gender,birthday,duty,phone,post,cadresDesc,idCard)\n" +
            "values(#{name},#{gender},#{birthday},#{duty},#{phone},#{post},#{cadresDesc},#{idCard})")

    int insert(Cadre cadre);

    int update(Cadre cadre);

    @Delete("delete from `cadre` where id = #{id}")
    int delete(@Param("id") Integer id);

    //    批量删除
    Integer deleteBatch(@Param("ids") List<Long> ids);

    List<Cadre> selectPage(Integer pageNum, Integer pageSize, String name, String phone, String idCard);

    long selectTotal();
}
