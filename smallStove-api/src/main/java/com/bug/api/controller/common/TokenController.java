package com.bug.api.controller.common;

import com.bug.framework.constant.SystemConstant;
import com.bug.framework.models.Result;
import com.bug.framework.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Api(tags = "令牌刷新接口")
@RestController
public class TokenController {

    @Resource
    private TokenService tokenService;

    /**
     * 检查令牌是否正常, 用于前端在路由跳转的时候进行权限校验
     */
    @ApiOperation(value = "检查令牌", notes = "查看access_token是否过期")
    @GetMapping("/check-token")
    public Result<?> checkToken(HttpServletRequest request) {
        String access_token = request.getHeader(SystemConstant.ACCESS_TOKEN);
        return tokenService.checkToken(access_token);
    }

    /**
     * 刷新令牌
     */
    @ApiOperation(value = "令牌刷新", notes = "当refresh_token未过期时，刷新已过期的access_token")
    @PutMapping("/refresh-token")
    public Result<?> refreshToken(HttpServletRequest request) {
        String refresh_token = request.getHeader(SystemConstant.REFRESH_TOKEN);
        return tokenService.refreshToken(refresh_token);
    }
}
