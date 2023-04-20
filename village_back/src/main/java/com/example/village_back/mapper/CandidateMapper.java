package com.example.village_back.mapper;

import com.example.village_back.entity.Candidate;
import com.example.village_back.entity.Voting;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CandidateMapper {
    @Insert("INSERT INTO `candidate`(voteType,name,phone,gender,birthday,avatarUrl,votes,experience)\n" +
            "values(#{voteType},#{name},#{phone},#{gender},#{birthday},#{avatarUrl},#{votes},#{experience})")

    int insert(Candidate candidate);

    int update(Candidate candidate);

    @Delete("delete from `candidate` where id = #{id}")
    int delete(@Param("id") Integer id);

    //    批量删除
    Integer deleteBatch(@Param("ids") List<Long> ids);

    List<Candidate> selectPage(Integer pageNum, Integer pageSize, String name,String phone, String voteType);

    long selectTotal();
}
