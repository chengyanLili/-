package com.example.village_back.Service;

import com.example.village_back.entity.Population;
import com.example.village_back.mapper.PopulationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class PopulationService {
    @Autowired
    private PopulationMapper populationMapper;

    public int save(Population population) {
        if (population.getId() == null) {
//            说明是新增数据
            return populationMapper.insert(population);
        } else {
            return populationMapper.update(population);
        }
    }
    public Map<String,Object> findPage(@RequestParam Integer pageNum,
                    @RequestParam Integer pageSize,
                    @RequestParam String name,
                    @RequestParam String phone,
                    @RequestParam String idCard){
        pageNum = (pageNum - 1) * pageSize;
        List<Population> data = populationMapper.selectPage(pageNum,pageSize,name,phone,idCard);
        long total = populationMapper.selectTotal();
        Map<String,Object> res = new HashMap<>();
        res.put("data",data);
        res.put("total",total);
        return res;
    }
}
