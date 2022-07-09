package com.bug.api.controller.anonymous;

import com.bug.framework.models.Result;
import com.bug.framework.service.SysLoginService;
import com.bug.system.domain.entity.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 登录控制器
 */
@RestController
@RequestMapping("/anonymous")
@Api(tags = "登录接口")
public class LoginController {

    @Resource
    private SysLoginService sysLoginService;

    @ApiOperation(value = "登录", notes = "未登录用户才能登录")
    @PostMapping("/login")
    public Result<?> login(@RequestBody SysUser user) {
        return sysLoginService.login(user.getUsername(), user.getPassword());
    }

}
