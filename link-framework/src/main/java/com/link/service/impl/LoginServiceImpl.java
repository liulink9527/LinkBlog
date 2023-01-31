package com.link.service.impl;

import com.link.constants.RedisConstants;
import com.link.domain.Result;
import com.link.domain.entity.LoginUser;
import com.link.domain.entity.User;
import com.link.domain.vo.UserInfoVo;
import com.link.domain.vo.UserLoginVo;
import com.link.service.LoginService;
import com.link.utils.BeanCopyUtils;
import com.link.utils.JwtUtil;
import com.link.utils.RedisCache;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author Link
 * @Description
 * @date 2023-01-15 13:02
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public Result login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authentication)) {
            throw new RuntimeException("用户名或密码错误");
        }
        //获取userid 生成token
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String token = JwtUtil.createJWT(userId);

        //用户信息存入Redis
        redisCache.setCacheObject(RedisConstants.LOGIN_PREFIX + userId, loginUser);

        //封装成userInfo返回
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        UserLoginVo userLoginVo = new UserLoginVo(token, userInfoVo);
        return Result.okResult(userLoginVo);
    }

    @Override
    public Result logout() {

        //获取userId
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        //删除redis用户信息
        redisCache.deleteObject(RedisConstants.LOGIN_PREFIX + userId);
        return Result.okResult();
    }


}
