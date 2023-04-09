package com.example.village_back.service;

import com.example.village_back.entity.Cadre;
import com.example.village_back.entity.FloatPopulation;
import com.example.village_back.mapper.CadreMapper;
import com.example.village_back.mapper.FloatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CadreService {
    @Autowired
    private CadreMapper cadreMapper;

    public Map<String,Object> findPage(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam String name,
                                       @RequestParam String phone,
                                       @RequestParam String idCard){
        pageNum = (pageNum - 1) * pageSize;
        List<Cadre> data = cadreMapper.selectPage(pageNum,pageSize,name,phone,idCard);
        long total = cadreMapper.selectTotal();
        Map<String,Object> res = new HashMap<>();
        res.put("data",data);
        res.put("total",total);
        return res;
    }

    public int save(Cadre cadre) {
        if (cadre.getId() == null) {
//            说明是新增数据
            return cadreMapper.insert(cadre);
        } else {
            return cadreMapper.update(cadre);
        }

    }
}
