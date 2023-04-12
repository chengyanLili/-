package com.example.village_back.controller;

import cn.hutool.core.util.StrUtil;
import com.example.village_back.controller.dao.UserDTO;
import com.example.village_back.entity.Users;
import com.example.village_back.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {
//    @Autowired
//    private UsersMapper usersMapper;
//    @PostMapping("/login")
//    public Boolean login(@RequestBody Users users) {
//        String username = users.getUsername();
//        String password = users.getPassword();
//        Integer identify = users.getIdentify();
//        if(StrUtil.isBlank(username) || StrUtil.isBlank(password) || identify == null){
//            return false;
//        }
//        return usersMapper.login(users);
//    }

}
