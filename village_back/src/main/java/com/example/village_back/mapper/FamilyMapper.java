package com.example.village_back.mapper;

import com.example.village_back.entity.Family;
import com.example.village_back.entity.Population;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public interface FamilyMapper {

    @Select("select * from family")
    List<Family> findAll();

    @Insert("INSERT INTO `family`(accountNo,householdName,householdPhone,householdIdCard,birthday,inGroup,memberName,memberIdCard,relation)\n" +
            "values(#{accountNo},#{householdName},#{householdPhone},#{householdIdCard},#{birthday},#{inGroup},#{memberName},#{memberIdCard},#{relation})")
    int insert(Family family);

    int update(Family family);

    @Delete("delete from `family` where familyId = #{familyId}")
    int delete(@Param("familyId") Integer familyId);

    //    批量删除
    Integer deleteBatch(@Param("familyIds") List<Long> familyIds);

    List<Family> selectPage(Integer pageNum, Integer pageSize,String householdName,String name);
    long selectTotal();

    @Select("select * from family where accountNo = #{accountNo}")
    List<Family> familyDetail(@Param("accountNo") Integer accountNo);
}
