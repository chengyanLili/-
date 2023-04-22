package com.example.village_back.mapper;

import com.example.village_back.entity.Task;
import com.example.village_back.entity.Voting;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VotingMapper {
    @Insert("INSERT INTO `voting`(voteType,startTime,endTime,duty,year,notice)\n" +
            "values(#{voteType},#{startTime},#{endTime},#{duty},#{year},#{notice})")

    int insert(Voting voting);

    int update(Voting voting);

    @Delete("delete from `voting` where id = #{id}")
    int delete(@Param("id") Integer id);

    //    批量删除
    Integer deleteBatch(@Param("ids") List<Long> ids);

    List<Voting> selectPage(Integer pageNum, Integer pageSize, String year, String voteType);

    long selectTotal();
}
