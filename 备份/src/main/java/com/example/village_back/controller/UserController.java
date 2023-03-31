package com.example.village_back.controller;

import com.example.village_back.config.Result;
import com.example.village_back.dao.UserDao;
import com.example.village_back.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserDao userDao;

    @GetMapping
    public Result findAll(){
        return Result.success(userDao.findAll());
    }


    @GetMapping("/test/{msg}")
    public String hello(@PathVariable String msg){
        return msg;
    }


    @PostMapping("/add")
    public Result add(@RequestBody User user){
        UserDao.insert(user);
        return Result.success();
    }

//    用户登录接口
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        if(user.getUsername()==null || user.getPassword()==null || user.getIdentify() == null){
            return Result.error("参数错误");
        }
       User result = userDao.findByUser(user.getUsername(),user.getPassword(), user.getIdentify());
//        当查询不到用户名或密码的时候，报错用户名或密码错误
        if(result==null){
            return Result.error("用户名或密码错误");
        }
        return Result.success(result);
    }
//    更新数据
    @PutMapping("/update")
    public Result update(@RequestBody User user){
        if(user.getId()==null){
            return Result.error("参数错误");
        }
        userDao.update(user);
        return Result.success();
    }
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        if(id==null || id==0){
            return Result.error("参数错误");
        }
//        如果返回1就说明删除了一条数据TRUE
        return Result.success(userDao.delete(id) == 1) ;
    }


}
