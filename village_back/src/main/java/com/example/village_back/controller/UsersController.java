package com.example.village_back.controller;

import cn.hutool.core.util.StrUtil;
import com.example.village_back.config.Result;
import com.example.village_back.entity.Message;
import com.example.village_back.entity.Users;
import com.example.village_back.mapper.UsersMapper;
import com.example.village_back.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Resource
    private UsersMapper usersMapper;

    @Autowired
    private UsersService usersService;

//    分页查询
    @GetMapping("/findPage")
    public Map<String,Object> findPage(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam String username){
        return usersService.findPage(pageNum,pageSize,username);
    }

//    新增
    @PostMapping("/add")
    public Result save( @RequestBody Users users){
        if(users.getIdentify() == null){
            users.setIdentify(2);
        }
        usersMapper.insert(users);
        return Result.success();
    }
  @PostMapping("/edit")
  public int edit(@RequestBody Users users){
        return usersMapper.update(users);
  }
    //    删除数据
    @DeleteMapping("/delete/{id}")
    public int delete(@PathVariable Integer id){ //PathVariable的路径参数要有花括号{id}
        return usersMapper.delete(id);
    }

    //    批量删除
    @PostMapping("/del/batch")
    public int deleteBatch(@RequestBody List<Long> ids){ //PathVariable的路径参数要有花括号{id}
        Integer res =  usersMapper.deleteBatch(ids);
        if(res == null) return 1;
        return res;
    }

    @PostMapping("/login")
    //  登录
    public List<Users> login(@RequestBody Users users) {
        return usersMapper.login(users);
    }

}
