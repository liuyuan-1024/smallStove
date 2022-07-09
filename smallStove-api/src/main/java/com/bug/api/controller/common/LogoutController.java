package com.bug.api.controller.common;

import com.bug.framework.models.Result;
import com.bug.framework.service.SysLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "注销接口")
@RestController
public class LogoutController {

    @Resource
    private SysLoginService sysLoginService;

    @ApiOperation(value = "注销", notes = "已登录才能注销")
    @PostMapping("/logout")
    public Result<?> logout() {
        return sysLoginService.logout();
    }
}
