package com.example.village_back.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.village_back.controller.dao.UserDTO;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import com.example.village_back.service.IUserService;
import com.example.village_back.entity.User;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 李
 * @since 2023-04-01
 */
@RestController
@RequestMapping("/user")
        public class UserController {

    @Resource
    private IUserService userService;

    @PostMapping("/login")
    public Boolean login(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        Integer identify = userDTO.getIdentify();
            if(StrUtil.isBlank(username) || StrUtil.isBlank(password) || identify == null){
                return false;
            }
                return userService.login(userDTO);
            }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
                return userService.removeById(id);
            }

    @GetMapping
    public List<User> findAll() {
                return userService.list();
            }

    @GetMapping("/{id}")
    public User findOne(@PathVariable Integer id) {
                return userService.getById(id);
            }

    @GetMapping("/page")
    public Page<User> findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize) {
                    return userService.page(new Page<>(pageNum, pageSize));
                }

            }

