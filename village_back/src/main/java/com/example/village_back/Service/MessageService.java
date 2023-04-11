package com.example.village_back.service;


import com.example.village_back.entity.Message;
import com.example.village_back.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageService {
    @Autowired
    private MessageMapper messageMapper;

    public Map<String,Object> findPage(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam String theme){
        pageNum = (pageNum - 1) * pageSize;
        List<Message> data = messageMapper.selectPage(pageNum,pageSize,theme);
        long total = messageMapper.selectTotal();
        Map<String,Object> res = new HashMap<>();
        res.put("data",data);
        res.put("total",total);
        return res;
    }

    public int save(Message message) {
        if (message.getId() == null) {
//            说明是新增数据
            return messageMapper.insert(message);
        } else {
            return messageMapper.update(message);
        }

    }
}
