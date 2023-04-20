package com.example.village_back.service;

import cn.hutool.core.date.DateUtil;
import com.example.village_back.entity.Task;
import com.example.village_back.entity.Voting;
import com.example.village_back.mapper.VotingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VotingService {
    @Autowired
    private VotingMapper votingMapper;

    public Map<String,Object> findPage(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam String title,
                                       @RequestParam String noticeType){
        pageNum = (pageNum - 1) * pageSize;
        List<Voting> data = votingMapper.selectPage(pageNum,pageSize,title,noticeType);
        long total = votingMapper.selectTotal();
        Map<String,Object> res = new HashMap<>();
        res.put("data",data);
        res.put("total",total);
        return res;
    }

    public int save(Voting voting) {
        if (voting.getId() == null) {
//            说明是新增数据
            return votingMapper.insert(voting);
        } else {
            return votingMapper.update(voting);
        }

    }
}
