package com.link.service;

import com.link.domain.Result;
import com.link.domain.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Link
 * @Description
 * @date 2023-01-15 13:02
 */
public interface LoginService {

    Result login(User user);

    Result logout();
}
