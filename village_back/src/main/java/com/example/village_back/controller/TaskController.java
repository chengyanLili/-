package com.example.village_back.controller;

import com.example.village_back.entity.FloatPopulation;
import com.example.village_back.entity.Task;
import com.example.village_back.mapper.FloatMapper;
import com.example.village_back.mapper.TaskMapper;
import com.example.village_back.service.FloatService;
import com.example.village_back.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private TaskService taskService;

    // 分页查询
    @GetMapping("/findPage")
    public Map<String,Object> findPage(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam String title,
                                       @RequestParam String noticeType){
        return taskService.findPage(pageNum,pageSize,title,noticeType);
    }

    //    新增或编辑家庭成员
    @PostMapping("/addOrUpdate") //插入数据
    public int save(@RequestBody Task task){
//        新增或者更新
        return taskService.save(task);
    }

    //    删除数据
    @DeleteMapping("/delete/{id}")
    public int delete(@PathVariable Integer id){ //PathVariable的路径参数要有花括号{id}
        return taskMapper.delete(id);
    }

    //    批量删除
    @PostMapping("/del/batch")
    public int deleteBatch(@RequestBody List<Long> ids){ //PathVariable的路径参数要有花括号{id}
        Integer res =  taskMapper.deleteBatch(ids);
        if(res == null) return 1;
        return res;
    }
}
