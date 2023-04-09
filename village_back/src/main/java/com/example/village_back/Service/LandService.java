package com.example.village_back.service;

import com.example.village_back.entity.FloatPopulation;
import com.example.village_back.entity.LandChange;
import com.example.village_back.mapper.FloatMapper;
import com.example.village_back.mapper.LandMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LandService {
    @Autowired
    private LandMapper landMapper;

    public Map<String,Object> findPage(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam String transferName,
                                       @RequestParam String transferPhone){
        pageNum = (pageNum - 1) * pageSize;
        List<LandChange> data = landMapper.selectPage(pageNum,pageSize,transferName,transferPhone);
        long total = landMapper.selectTotal();
        Map<String,Object> res = new HashMap<>();
        res.put("data",data);
        res.put("total",total);
        return res;
    }

    public int save(LandChange landChange) {
        if (landChange.getId() == null) {
//            说明是新增数据
            return landMapper.insert(landChange);
        } else {
            return landMapper.update(landChange);
        }

    }
}
