package com.example.village_back.mapper;

import com.example.village_back.entity.Cadre;
import com.example.village_back.entity.Difficulty;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DifficultMapper {
    @Insert("INSERT INTO `difficulty`(name,gender,birthday,difficultReason,phone,diseases,disabledCard)\n" +
            "values(#{name},#{gender},#{birthday},#{difficultReason},#{phone},#{diseases},#{disabledCard})")

    int insert(Difficulty difficulty);

    int update(Difficulty difficulty);

    @Delete("delete from `difficulty` where id = #{id}")
    int delete(@Param("id") Integer id);

    //    批量删除
    Integer deleteBatch(@Param("ids") List<Long> ids);

    List<Difficulty> selectPage(Integer pageNum, Integer pageSize, String name, String phone);

    long selectTotal();
}
