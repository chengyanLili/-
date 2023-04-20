package com.example.village_back.service;

import com.example.village_back.entity.Candidate;
import com.example.village_back.entity.Voting;
import com.example.village_back.mapper.CandidateMapper;
import com.example.village_back.mapper.VotingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CandidateService {
    @Autowired
    private CandidateMapper candidateMapper;

    public Map<String,Object> findPage(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam String name,
                                       @RequestParam String phone,
                                       @RequestParam String voteType){
        pageNum = (pageNum - 1) * pageSize;
        List<Candidate> data = candidateMapper.selectPage(pageNum,pageSize,name,phone,voteType);
        long total = candidateMapper.selectTotal();
        Map<String,Object> res = new HashMap<>();
        res.put("data",data);
        res.put("total",total);
        return res;
    }

    public int save(Candidate candidate) {
        if (candidate.getId() == null) {
//            说明是新增数据
            return candidateMapper.insert(candidate);
        } else {
            return candidateMapper.update(candidate);
        }

    }
}
