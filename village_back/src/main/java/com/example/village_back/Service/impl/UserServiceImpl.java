package com.example.village_back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.village_back.controller.dao.UserDTO;
import com.example.village_back.entity.User;
import com.example.village_back.mapper.UserMapper;
import com.example.village_back.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 李
 * @since 2023-04-01
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper baseMapper;
    @Override
    public Boolean login(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userDTO.getUsername());
        queryWrapper.eq("password",userDTO.getPassword());
        queryWrapper.eq("identify",userDTO.getIdentify());
        User one = getOne(queryWrapper);
        return one != null;
    }
}
