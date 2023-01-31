package com.link.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.link.domain.Result;
import com.link.domain.entity.User;
import com.link.domain.vo.UserInfoVo;
import com.link.enums.AppHttpCodeEnum;
import com.link.exception.SystemException;
import com.link.mapper.UserMapper;
import com.link.service.UserService;
import com.link.utils.BeanCopyUtils;
import com.link.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sun.security.util.Password;

import java.util.Objects;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2022-12-29 17:48:02
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Result userInfo() {
        Long userId = SecurityUtils.getUserId();
        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper();
        qw.eq(User::getId, userId);
        User user = getOne(qw);
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return Result.okResult(userInfoVo);
    }

    @Override
    public Result updateUserInfo(User user) {
        updateById(user);
        return Result.okResult();
    }

    @Override
    public Result register(User user) {

        if (!StringUtils.hasText(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getPassword())) {
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }

        //数据是否存在的判断
        if (userNameExist(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (userNameExist(user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }

        //对密码加密
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);

        //存入数据库
        save(user);


        return Result.okResult();
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper();
        qw.eq(User::getUserName, userName);
        return count(qw) > 0;
    }

    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper();
        qw.eq(User::getNickName, nickName);
        return count(qw) > 0;
    }
}
