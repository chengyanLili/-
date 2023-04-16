package com.example.village_back.mapper;

import com.example.village_back.config.Result;
import com.example.village_back.entity.FloatPopulation;
import com.example.village_back.entity.Users;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Mapper
@Primary
public interface UsersMapper {

    @Insert("INSERT INTO `users`(username,gender,password,phone,birthday,identify,avatarUrl,address,nickName)\n" +
            "values(#{username},#{gender},#{password},#{phone},#{birthday},#{identify},#{avatarUrl},#{address},#{nickName})")

    int insert(Users users);
    int update(Users users);
    @Delete("delete from `users` where id = #{id}")
    int delete(@Param("id") Integer id);

    //    批量删除
    Integer deleteBatch(@Param("ids") List<Long> ids);

    List<Users> selectPage(Integer pageNum, Integer pageSize, String username);
    long selectTotal();

    List<Users> login(Users users);
}
