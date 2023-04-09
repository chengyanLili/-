package com.example.village_back.service;

import com.example.village_back.entity.Cadre;
import com.example.village_back.entity.Difficulty;
import com.example.village_back.mapper.CadreMapper;
import com.example.village_back.mapper.DifficultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DifficultService {
    @Autowired
    private DifficultMapper difficultMapper;

    public Map<String,Object> findPage(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam String name,
                                       @RequestParam String phone){
        pageNum = (pageNum - 1) * pageSize;
        List<Difficulty> data = difficultMapper.selectPage(pageNum,pageSize,name,phone);
        long total = difficultMapper.selectTotal();
        Map<String,Object> res = new HashMap<>();
        res.put("data",data);
        res.put("total",total);
        return res;
    }

    public int save(Difficulty difficulty) {
        if (difficulty.getId() == null) {
//            说明是新增数据
            return difficultMapper.insert(difficulty);
        } else {
            return difficultMapper.update(difficulty);
        }

    }
}
