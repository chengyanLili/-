package com.example.village_back.mapper;

import com.example.village_back.entity.FloatPopulation;
import com.example.village_back.entity.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MessageMapper {
    @Select("select * from message")
    List<Message> findAll();

    @Insert("INSERT INTO `message`(name,sendName,content,sendTime,replyTime,msgStatus,handleName,reply)\n" +
            "values(#{name},#{sendName},#{content},#{sendTime},#{replyTime},#{msgStatus},#{handleName},#{reply})")

    int insert(Message message);

    int update(Message message);

    @Delete("delete from `message` where id = #{id}")
    int delete(@Param("id") Integer id);

    //    批量删除
    Integer deleteBatch(@Param("ids") List<Long> ids);

    List<Message> selectPage(Integer pageNum, Integer pageSize, String theme);

    long selectTotal();
}
