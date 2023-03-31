package com.example.village_back.controller;

import com.example.village_back.Service.UserService;
import com.example.village_back.config.Result;
import com.example.village_back.controller.dao.UserDTO;
import com.example.village_back.entity.User;
import com.example.village_back.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    //    用户登录接口
    @PostMapping("/login")
    public boolean login(@RequestBody UserDTO userDTO){
        return userService.login(userDTO);
}
