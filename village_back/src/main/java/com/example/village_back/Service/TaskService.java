package com.example.village_back.service;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.example.village_back.entity.FloatPopulation;
import com.example.village_back.entity.Task;
import com.example.village_back.mapper.FloatMapper;
import com.example.village_back.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskService {
    @Autowired
    private TaskMapper taskMapper;

    public Map<String,Object> findPage(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam String title,
                                       @RequestParam String noticeType){
        pageNum = (pageNum - 1) * pageSize;
        List<Task> data = taskMapper.selectPage(pageNum,pageSize,title,noticeType);
        long total = taskMapper.selectTotal();
        Map<String,Object> res = new HashMap<>();
        res.put("data",data);
        res.put("total",total);
        return res;
    }

    public int save(Task task) {
        if (task.getId() == null) {
//            说明是新增数据
            task.setCreateTime(DateUtil.now());
            return taskMapper.insert(task);
        } else {
            return taskMapper.update(task);
        }

    }
}
