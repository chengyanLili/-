package com.example.village_back.service;

import com.example.village_back.entity.Family;
import com.example.village_back.entity.Population;
import com.example.village_back.mapper.FamilyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FamilyService {
    @Autowired
    private FamilyMapper familyMapper;

    public Map<String,Object> findPage(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam String householdName,
                                       @RequestParam String name
                                       ){
        pageNum = (pageNum - 1) * pageSize;
        List<Family> data = familyMapper.selectPage(pageNum,pageSize,householdName,name);
        long total = familyMapper.selectTotal();
        Map<String,Object> res = new HashMap<>();
        res.put("data",data);
        res.put("total",total);
        return res;
    }

    public int save(Family family) {
        if (family.getFamilyId() == null) {
//            说明是新增数据
            return familyMapper.insert(family);
        } else {
            return familyMapper.update(family);
        }

    }

//    新增户籍户主
    public int add(Family family) {
        return familyMapper.insert(family);
    }
}
