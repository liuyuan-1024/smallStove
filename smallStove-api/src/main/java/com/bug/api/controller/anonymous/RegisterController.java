package com.bug.api.controller.anonymous;

import com.bug.framework.models.Result;
import com.bug.system.domain.entity.SysUser;
import com.bug.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户注册
 */
@Api(tags = "注册接口")
@RestController
@RequestMapping("/anonymous")
public class RegisterController {

    @Resource
    private SysUserService userService;

    @ApiOperation(value = "注册新用户", notes = "任何人都可以注册")
    @PostMapping("/register")
    public Result<?> register(@RequestBody SysUser user) {
        return userService.register(user);
    }
}
