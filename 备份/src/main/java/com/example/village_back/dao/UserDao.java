package com.example.village_back.dao;

import com.example.village_back.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface UserDao {

    static void insert(User user) {
    }

    int insert();


//当返回的是一个数组时候就需要list
    List<User> findAll();

    User findByUser(@Param("username") String username, @Param("password") String password, @Param("identify") Integer identify);
    int update(User user);

    int delete(Integer id);

}
