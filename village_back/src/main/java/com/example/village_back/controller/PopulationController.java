package com.example.village_back.controller;

import com.example.village_back.Service.PopulationService;
import com.example.village_back.config.Result;
import com.example.village_back.entity.Population;
import com.example.village_back.mapper.PopulationMapper;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/population") //统一给接口加前缀
public class PopulationController {
    @Autowired
    private PopulationMapper populationMapper;

    @Autowired
    private PopulationService populationService;

//    查询所有数据
    @GetMapping
    public List<Population> findAll(){
        return populationMapper.findAll();
    }

    // 分页查询
    @GetMapping("/findPage")
    public Map<String,Object> findPage(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam String name,
                                       @RequestParam String phone,
                                       @RequestParam String idCard){
        return populationService.findPage(pageNum,pageSize,name,phone,idCard);
    }

    @PostMapping("/add") //插入数据
    public Integer save(@RequestBody Population population){
//        新增或者更新
       return populationService.save(population);
    }

//    删除数据
    @DeleteMapping("/delete/{id}")
    public Integer delete(@PathVariable Integer id){ //PathVariable的路径参数要有花括号{id}
       return populationMapper.deleteById(id);
    }

}
