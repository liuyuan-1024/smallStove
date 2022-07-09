package com.bug.framework.service;

import com.bug.framework.models.Result;
import com.bug.framework.security.domain.entity.LoginUser;
import com.bug.framework.security.domain.entity.Token;

public interface TokenService {

    /**
     * 签发令牌
     */
    Result<Token> issueToken(LoginUser loginUser);

    /**
     * 刷新令牌，重新签发令牌
     */
    Result<?> refreshToken(String refresh_token);

    /**
     * 检查令牌是否正常, 返回状态码: 200正常, 400异常
     */
    Result<?> checkToken(String access_token);
}
