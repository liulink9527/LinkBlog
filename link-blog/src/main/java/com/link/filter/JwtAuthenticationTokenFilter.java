package com.link.filter;

import com.alibaba.fastjson.JSON;
import com.link.constants.RedisConstants;
import com.link.domain.Result;
import com.link.domain.entity.LoginUser;
import com.link.enums.AppHttpCodeEnum;
import com.link.utils.JwtUtil;
import com.link.utils.RedisCache;
import com.link.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author Link
 * @Description
 * @date 2023-01-15 14:31
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //获取请求头中的token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        //解析获取userId
        Claims claims = null;
        String userId = null;
        try { claims = JwtUtil.parseJWT(token);

            userId = claims.getSubject();

        } catch (Exception e) {
            e.printStackTrace();
            //token超时
            Result result = Result.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            String json = JSON.toJSONString(result);
            WebUtils.renderString(response, json);
            return;
        }

        //从redis中获取用户信息
        LoginUser loginUser = (LoginUser) redisCache.getCacheObject(RedisConstants.LOGIN_PREFIX + userId);
        if (Objects.isNull(loginUser)) {
            Result result = Result.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            String json = JSON.toJSONString(result);
            WebUtils.renderString(response, json);
            return;
        }

        //存入SecurityContextHolder
        SecurityContext context = SecurityContextHolder.getContext();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);
        context.setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
        return;
    }
}
