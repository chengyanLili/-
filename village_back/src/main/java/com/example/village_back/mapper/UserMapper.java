package com.example.village_back.mapper;

import com.example.village_back.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 李
 * @since 2023-04-01
 */
@Repository
@Primary
public interface UserMapper extends BaseMapper<User> {

}
