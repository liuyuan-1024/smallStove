package com.bug.framework.service.impl;

import com.bug.framework.models.Result;
import com.bug.framework.models.ResultBuilder;
import com.bug.framework.models.ResultEnum;
import com.bug.framework.security.domain.entity.LoginUser;
import com.bug.framework.security.domain.entity.Token;
import com.bug.framework.service.TokenService;
import com.bug.framework.utils.JwtUtils;
import com.bug.framework.utils.RedisCache;
import com.bug.framework.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
public class TokenServiceImpl implements TokenService {
    @Resource
    private RedisCache redisCache;

    /**
     * 签发令牌
     * 令牌中包含：用户ID、用户名、用户权限
     */
    public Result<Token> issueToken(LoginUser loginUser) {
        //官方声明：用户名；自定义声明：用户ID、用户权限
        HashMap<String, Object> claims = new HashMap<>();
        claims.put(JwtUtils.SUBJECT, loginUser.getUsername());
        claims.put(JwtUtils.LOGIN_USER_ID, loginUser.getId());

        //生成access_token、refresh_token
        String access_token = JwtUtils.generateTokenByClaims(claims);
        String refresh_token = JwtUtils.generateRefreshTokenByToken(access_token);

        return new ResultBuilder().success(ResultEnum.LOGIN_SUCCESS, new Token(access_token, refresh_token));
    }

    @Override
    public Result<?> refreshToken(String refresh_token) {
        // refresh_token的合法性已在JwtAuthenticationFilter校验通过
        // 判断refresh_token是否可以刷新
        if (!JwtUtils.isRefresh(refresh_token)) {
            return new ResultBuilder().error(ResultEnum.AUTHENTICATION_NORMAL);
        }

        // 刷新令牌
        String token = JwtUtils.generateTokenByRefreshToken(refresh_token);
        String refreshToken = JwtUtils.generateRefreshTokenByToken(token);

        // 刷新redis
        LoginUser loginUser = SecurityUtils.getLoginUser();
        redisCache.set(redisCache.getLoginUserKey(loginUser.getId()), loginUser, redisCache.getExpirationTime());

        // 可刷新令牌：刷新access_token、refreshToken
        return new ResultBuilder().success(ResultEnum.AUTHENTICATION_REFRESH_SUCCESS, new Token(token, refreshToken));
    }

    @Override
    public Result<?> checkToken(String access_token) {
        if (JwtUtils.isLegal(access_token, SecurityUtils.getUsername())) {
            return new ResultBuilder().success(ResultEnum.AUTHENTICATION_NORMAL);
        }
        return new ResultBuilder().error(ResultEnum.AUTHENTICATION_ILLEGAL);
    }
}
