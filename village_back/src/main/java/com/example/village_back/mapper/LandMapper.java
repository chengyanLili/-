package com.example.village_back.mapper;

import com.example.village_back.entity.LandChange;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LandMapper {

    @Insert("INSERT INTO `landchange`(notaryName,notaryPhone,changeDay,changePlace,transferName,transferIntoName,changeNum,transferPhone,transferIntoPhone)\n" +
            "values(#{notaryName},#{notaryPhone},#{changeDay},#{changePlace},#{transferName},#{transferIntoName},#{changeNum},#{transferPhone},#{transferIntoPhone})")

    int insert(LandChange landChange);

    int update(LandChange landChange);

    @Delete("delete from `landchange` where id = #{id}")
    int delete(@Param("id") Integer id);

    //    批量删除
    Integer deleteBatch(@Param("ids") List<Long> ids);

    List<LandChange> selectPage(Integer pageNum, Integer pageSize, String transferName, String transferPhone);

    long selectTotal();
}
