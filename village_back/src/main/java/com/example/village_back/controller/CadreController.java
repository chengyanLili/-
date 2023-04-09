package com.example.village_back.controller;

import com.example.village_back.entity.Cadre;
import com.example.village_back.entity.FloatPopulation;
import com.example.village_back.mapper.CadreMapper;
import com.example.village_back.mapper.FloatMapper;
import com.example.village_back.service.CadreService;
import com.example.village_back.service.FloatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cadre")
public class CadreController {
    @Autowired
    private CadreMapper cadreMapper;

    @Autowired
    private CadreService cadreService;

    // 分页查询
    @GetMapping("/findPage")
    public Map<String,Object> findPage(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam String name,
                                       @RequestParam String phone,
                                       @RequestParam String idCard){
        return cadreService.findPage(pageNum,pageSize,name,phone,idCard);
    }

    //    新增或编辑家庭成员
    @PostMapping("/addOrUpdate") //插入数据
    public int save(@RequestBody Cadre cadre){
//        新增或者更新
        return cadreService.save(cadre);
    }

    //    删除数据
    @DeleteMapping("/delete/{id}")
    public int delete(@PathVariable Integer id){ //PathVariable的路径参数要有花括号{id}
        return cadreMapper.delete(id);
    }

    //    批量删除
    @PostMapping("/del/batch")
    public int deleteBatch(@RequestBody List<Long> ids){ //PathVariable的路径参数要有花括号{id}
        Integer res =  cadreMapper.deleteBatch(ids);
        if(res == null) return 1;
        return res;
    }
}
