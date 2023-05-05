package com.example.village_back.mapper;

import com.example.village_back.entity.FloatPopulation;
import com.example.village_back.entity.Task;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TaskMapper {

    @Insert("INSERT INTO `task`(title,fileUrl,content,createTime,finishTime,progress,publishName,commentNum,noticeType)\n" +
            "values(#{title},#{fileUrl},#{content},#{createTime},#{finishTime},#{progress},#{publishName},#{commentNum},#{noticeType})")

    int insert(Task task);

    int update(Task task);

    @Delete("delete from `task` where id = #{id}")
    int delete(@Param("id") Integer id);

    //    批量删除
    Integer deleteBatch(@Param("ids") List<Long> ids);

    List<Task> selectPage(Integer pageNum, Integer pageSize, String title,String noticeType);

    long selectTotal();
}
