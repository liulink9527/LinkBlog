package com.link.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.link.domain.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * 用户表(User)表数据库访问层
 *
 * @author makejava
 * @since 2022-12-29 17:48:02
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    User selectByUsername(@Param("userName") String username);
}

