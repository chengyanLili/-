package com.example.village_back.controller;

import com.example.village_back.entity.Family;
import com.example.village_back.entity.Population;
import com.example.village_back.mapper.FamilyMapper;
import com.example.village_back.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/family")
public class FamilyController {
    @Autowired
    private FamilyMapper familyMapper;

    @Autowired
    private FamilyService familyService;

    // 分页查询
    @GetMapping("/findPage")
    public Map<String,Object> findPage(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam String householdName,
                                       @RequestParam String name
                                       ){
        return familyService.findPage(pageNum,pageSize,householdName,name);
    }

//    查看户籍详情
    @GetMapping("/familyDetail")
        public List<Family> familyDetail(@RequestParam Integer accountNo){
        return familyMapper.familyDetail(accountNo);
    }

//    新增户主
    @PostMapping("/addHousehold")
    public int add(@RequestBody Family family){
        return familyService.add(family);
    }

//    新增或编辑家庭成员
    @PostMapping("/addOrUpdate") //插入数据
    public int save(@RequestBody Family family){
//        新增或者更新
        return familyService.save(family);
    }

    //    删除数据
    @DeleteMapping("/delete/{familyId}")
    public int delete(@PathVariable Integer familyId){ //PathVariable的路径参数要有花括号{id}
        return familyMapper.delete(familyId);
    }

    //    批量删除
    @PostMapping("/del/batch")
    public int deleteBatch(@RequestBody List<Long> familyIds){ //PathVariable的路径参数要有花括号{id}
        Integer res =  familyMapper.deleteBatch(familyIds);
        if(res == null) return 1;
        return res;
    }
}
