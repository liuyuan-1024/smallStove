package com.bug.framework.security.handler;

import com.alibaba.fastjson.JSON;
import com.bug.framework.models.Result;
import com.bug.framework.security.domain.entity.LoginUser;
import com.bug.framework.security.domain.entity.Token;
import com.bug.framework.service.TokenService;
import com.bug.framework.utils.RedisCache;
import com.bug.framework.utils.WebUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登录成功处理器
 */
@Component
public class GoAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private RedisCache redisCache;

    @Resource
    private TokenService tokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                        Authentication authentication) throws IOException, ServletException {

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        // 将LoginUser对象存入redis中
        redisCache.set(redisCache.getLoginUserKey(loginUser.getId()), loginUser, redisCache.getExpirationTime());

        // 将access_token、refresh_token发送给前端
        Result<Token> token = tokenService.issueToken(loginUser);
        WebUtils.renderJson(response, JSON.toJSONString(token));
        chain.doFilter(request, response);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        // 将LoginUser对象存入redis中
        redisCache.set(redisCache.getLoginUserKey(loginUser.getId()), loginUser, redisCache.getExpirationTime());

        // 将access_token、refresh_token发送给前端
        Result<Token> token = tokenService.issueToken(loginUser);
        WebUtils.renderJson(response, JSON.toJSONString(token));
    }
}
