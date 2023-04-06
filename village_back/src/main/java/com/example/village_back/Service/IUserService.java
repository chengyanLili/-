package com.example.village_back.service;

import com.example.village_back.controller.dao.UserDTO;
import com.example.village_back.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 李
 * @since 2023-04-01
 */
public interface IUserService extends IService<User> {

    Boolean login(UserDTO userDTO);
}
