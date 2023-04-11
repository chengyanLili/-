package com.example.village_back.controller;

import com.example.village_back.config.Result;
import com.example.village_back.entity.Comment;
import com.example.village_back.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentMapper commentMapper;

    public Result findTree(@PathVariable Integer noticeId){
        List<Comment> noticeComments = commentMapper.findCommentDetail(noticeId);
        return Result.success();
    }
}
