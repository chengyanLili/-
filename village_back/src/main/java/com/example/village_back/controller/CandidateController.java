package com.example.village_back.controller;

import com.example.village_back.entity.Candidate;
import com.example.village_back.entity.Voting;
import com.example.village_back.mapper.CandidateMapper;
import com.example.village_back.mapper.VotingMapper;
import com.example.village_back.service.CandidateService;
import com.example.village_back.service.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/candidate")
public class CandidateController {
    @Autowired
    private CandidateMapper candidateMapper;

    @Autowired
    private CandidateService candidateService;

    // 分页查询
    @GetMapping("/findPage")
    public Map<String,Object> findPage(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam String name,
                                       @RequestParam String phone,
                                       @RequestParam String voteType){
        return candidateService.findPage(pageNum,pageSize,name,phone,voteType);
    }

    //    新增或编辑家庭成员
    @PostMapping("/addOrUpdate") //插入数据
    public int save(@RequestBody Candidate candidate){
//        新增或者更新
        return candidateService.save(candidate);
    }

    //    删除数据
    @DeleteMapping("/delete/{id}")
    public int delete(@PathVariable Integer id){ //PathVariable的路径参数要有花括号{id}
        return candidateMapper.delete(id);
    }

    //    批量删除
    @PostMapping("/del/batch")
    public int deleteBatch(@RequestBody List<Long> ids){ //PathVariable的路径参数要有花括号{id}
        Integer res =  candidateMapper.deleteBatch(ids);
        if(res == null) return 1;
        return res;
    }
}
