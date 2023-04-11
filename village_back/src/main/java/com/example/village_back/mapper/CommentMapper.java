package com.example.village_back.mapper;

import com.example.village_back.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public interface CommentMapper {

    List<Comment> findCommentDetail(Integer noticeId);
}
