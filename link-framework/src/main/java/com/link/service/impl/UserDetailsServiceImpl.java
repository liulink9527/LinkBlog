package com.link.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.link.domain.entity.LoginUser;
import com.link.domain.entity.User;
import com.link.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author Link
 * @Description
 * @date 2023-01-15 13:09
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //根据用户名查询用户信息
        User user = userMapper.selectByUsername(username);
//        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper();
//        qw.eq(User::getUserName, username);
//        User user = userMapper.selectOne(qw);
        if (Objects.isNull(user)) {
            throw new RuntimeException("用户名或密码错误");
        }

        //TODO 查询权限信息


        //返回用户信息
        return new LoginUser(user);


    }
}
