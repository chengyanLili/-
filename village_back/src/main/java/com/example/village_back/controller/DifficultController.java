package com.example.village_back.controller;

import com.example.village_back.entity.Difficulty;
import com.example.village_back.mapper.DifficultMapper;
import com.example.village_back.service.DifficultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/difficult")

public class DifficultController {
    @Autowired
    private DifficultMapper difficultMapper;

    @Autowired
    private DifficultService difficultService;

    // 分页查询
    @GetMapping("/findPage")
    public Map<String,Object> findPage(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam String name,
                                       @RequestParam String phone){
        return difficultService.findPage(pageNum,pageSize,name,phone);
    }

    //    新增或编辑家庭成员
    @PostMapping("/addOrUpdate") //插入数据
    public int save(@RequestBody Difficulty difficulty){
//        新增或者更新
        return difficultService.save(difficulty);
    }

    //    删除数据
    @DeleteMapping("/delete/{id}")
    public int delete(@PathVariable Integer id){ //PathVariable的路径参数要有花括号{id}
        return difficultMapper.delete(id);
    }

    //    批量删除
    @PostMapping("/del/batch")
    public int deleteBatch(@RequestBody List<Long> ids){ //PathVariable的路径参数要有花括号{id}
        Integer res =  difficultMapper.deleteBatch(ids);
        if(res == null) return 1;
        return res;
    }
}
