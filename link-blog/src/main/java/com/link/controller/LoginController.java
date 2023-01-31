package com.link.controller;

import com.link.domain.Result;
import com.link.domain.entity.User;
import com.link.enums.AppHttpCodeEnum;
import com.link.exception.SystemException;
import com.link.service.LoginService;
import com.link.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Link
 * @Description
 * @date 2023-01-15 12:58
 */
@Api
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @ApiOperation("登录接口")
    @RequestMapping("/login")
    public Result login(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }

    @ApiOperation("退出接口")
    @RequestMapping("/logout")
    public Result logout() {
        return loginService.logout();
    }


}
