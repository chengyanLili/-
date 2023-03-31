package com.example.village_back.controller;

import com.example.village_back.config.Result;
import com.example.village_back.controller.DTO.PopulationQueryDTO;
import com.example.village_back.dao.PopulationDao;
import com.example.village_back.entity.Population;

import com.example.village_back.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/population")
public class PopulationController {
    @Autowired
    private PopulationDao populationDao;

//    查询所有的村民
    @GetMapping()
    public Result findAll(){
        return Result.success(populationDao.findAll());
    }
// 分页查询
    @GetMapping("/findPage")
    public Result findPage(PopulationQueryDTO populationQueryDTO){
        Integer currentPage = populationQueryDTO.getCurrentPage();
        Integer pageSize = populationQueryDTO.getPageSize();
        if(currentPage==null || currentPage <= 0
        || pageSize==null||pageSize<1){
            return Result.error("参数错误");
        }
        Integer pageNum = (currentPage - 1) * pageSize;
        populationQueryDTO.setPageNum(pageNum);

        List<Population> villagers =  populationDao.findPage(populationQueryDTO);

       long total = populationDao.count(populationQueryDTO);

        Map<String,Object> map = new HashMap<>();
        map.put("villagers",villagers);
        map.put("total",total);
        return Result.success(map);
}
//@PostMapping("/add")
//    public Result save(@RequestBody Population population){
//            PopulationDao.insert(population);
//            return Result.success();
//}
   // 修改
    @PutMapping("/editPopulation")
    public Result update(@RequestBody Population population){
        if(population.getId()==null){
            return Result.error("参数错误");
        }
        PopulationDao.update(population);
        return Result.success();
    }


}
