package com.link.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.link.domain.Result;
import com.link.domain.entity.User;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2022-12-29 17:48:02
 */
public interface UserService extends IService<User> {

    Result userInfo();

    Result updateUserInfo(User user);

    Result register(User user);
}
