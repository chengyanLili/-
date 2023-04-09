package com.example.village_back.controller;

import com.example.village_back.entity.LandChange;
import com.example.village_back.mapper.LandMapper;
import com.example.village_back.service.LandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/land")
public class LandController {
    @Autowired
    private LandMapper landMapper;

    @Autowired
    private LandService landService;

    // 分页查询
    @GetMapping("/findPage")
    public Map<String,Object> findPage(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam String transferName,
                                       @RequestParam String transferPhone){
        return landService.findPage(pageNum,pageSize,transferName,transferPhone);
    }

    //    新增或编辑家庭成员
    @PostMapping("/addOrUpdate") //插入数据
    public int save(@RequestBody LandChange landChange){
//        新增或者更新
        return landService.save(landChange);
    }

    //    删除数据
    @DeleteMapping("/delete/{id}")
    public int delete(@PathVariable Integer id){ //PathVariable的路径参数要有花括号{id}
        return landMapper.delete(id);
    }

    //    批量删除
    @PostMapping("/del/batch")
    public int deleteBatch(@RequestBody List<Long> ids){ //PathVariable的路径参数要有花括号{id}
        Integer res =  landMapper.deleteBatch(ids);
        if(res == null) return 1;
        return res;
    }
}
