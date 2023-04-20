package com.example.village_back.controller;

import com.example.village_back.entity.Task;
import com.example.village_back.entity.Voting;
import com.example.village_back.mapper.TaskMapper;
import com.example.village_back.mapper.VotingMapper;
import com.example.village_back.service.TaskService;
import com.example.village_back.service.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/voting")
public class VotingController {
    @Autowired
    private VotingMapper votingMapper;

    @Autowired
    private VotingService votingService;

    // 分页查询
    @GetMapping("/findPage")
    public Map<String,Object> findPage(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam String year,
                                       @RequestParam String voteType){
        return votingService.findPage(pageNum,pageSize,year,voteType);
    }

    //    新增或编辑家庭成员
    @PostMapping("/addOrUpdate") //插入数据
    public int save(@RequestBody Voting voting){
//        新增或者更新
        return votingService.save(voting);
    }

    //    删除数据
    @DeleteMapping("/delete/{id}")
    public int delete(@PathVariable Integer id){ //PathVariable的路径参数要有花括号{id}
        return votingMapper.delete(id);
    }

    //    批量删除
    @PostMapping("/del/batch")
    public int deleteBatch(@RequestBody List<Long> ids){ //PathVariable的路径参数要有花括号{id}
        Integer res =  votingMapper.deleteBatch(ids);
        if(res == null) return 1;
        return res;
    }
}
