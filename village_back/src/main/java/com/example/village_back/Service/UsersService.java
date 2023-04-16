package com.example.village_back.service;

import com.example.village_back.entity.FloatPopulation;
import com.example.village_back.entity.Users;
import com.example.village_back.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsersService {
    @Autowired
    private UsersMapper usersMapper;


    public Map<String,Object> findPage(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam String username){
        pageNum = (pageNum - 1) * pageSize;
        List<Users> data = usersMapper.selectPage(pageNum,pageSize,username);
        long total = usersMapper.selectTotal();
        Map<String,Object> res = new HashMap<>();
        res.put("data",data);
        res.put("total",total);
        return res;
    }

    public int save(Users users) {
        if (users.getId() == null) {
//            说明是新增数据
            return usersMapper.insert(users);
        } else {
            return usersMapper.update(users);
        }

    }

}
