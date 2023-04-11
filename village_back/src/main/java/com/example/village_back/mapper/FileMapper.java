package com.example.village_back.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.village_back.entity.Difficulty;
import com.example.village_back.entity.Files;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface FileMapper extends BaseMapper<Files> {
    int update(Difficulty difficulty);

    @Delete("delete from `difficulty` where id = #{id}")
    int delete(@Param("id") Integer id);

    //    批量删除
    Integer deleteBatch(@Param("ids") List<Long> ids);

    List<Difficulty> selectPage(Integer pageNum, Integer pageSize, String name, String phone);

    long selectTotal();

}
