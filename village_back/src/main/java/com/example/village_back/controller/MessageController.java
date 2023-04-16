package com.example.village_back.controller;

import com.example.village_back.entity.LandChange;
import com.example.village_back.entity.Message;
import com.example.village_back.entity.Task;
import com.example.village_back.mapper.MessageMapper;
import com.example.village_back.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private MessageService messageService;
    // 分页查询
    @GetMapping("/findPage")
    public Map<String,Object> findPage(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam String theme,
                                       @RequestParam String sendName
                                       ){
        return messageService.findPage(pageNum,pageSize,theme,sendName);
    }
    //    新增或编辑
    @PostMapping("/addOrUpdate") //插入数据
    public int save(@RequestBody Message message){
//        新增或者更新
        return messageService.save(message);
    }
    //    删除数据
    @DeleteMapping("/delete/{id}")
    public int delete(@PathVariable Integer id){ //PathVariable的路径参数要有花括号{id}
        return messageMapper.delete(id);
    }

    //    批量删除
    @PostMapping("/del/batch")
    public int deleteBatch(@RequestBody List<Long> ids){ //PathVariable的路径参数要有花括号{id}
        Integer res =  messageMapper.deleteBatch(ids);
        if(res == null) return 1;
        return res;
    }
}
