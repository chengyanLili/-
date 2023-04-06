package com.example.village_back.service;

import com.example.village_back.entity.Family;
import com.example.village_back.entity.FloatPopulation;
import com.example.village_back.entity.Population;
import com.example.village_back.mapper.FamilyMapper;
import com.example.village_back.mapper.FloatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FloatService {
    @Autowired
    private FloatMapper floatMapper;

    public Map<String,Object> findPage(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam String name,
                                       @RequestParam String phone,
                                       @RequestParam String idCard){
        pageNum = (pageNum - 1) * pageSize;
        List<FloatPopulation> data = floatMapper.selectPage(pageNum,pageSize,name,phone,idCard);
        long total = floatMapper.selectTotal();
        Map<String,Object> res = new HashMap<>();
        res.put("data",data);
        res.put("total",total);
        return res;
    }

    public int save(FloatPopulation floatPopulation) {
        if (floatPopulation.getId() == null) {
//            说明是新增数据
            return floatMapper.insert(floatPopulation);
        } else {
            return floatMapper.update(floatPopulation);
        }

    }

}
