package com.bug.api.controller.anonymous;

import com.bug.framework.models.Result;
import com.bug.framework.service.SysLoginService;
import com.bug.system.domain.entity.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

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

    // todo 前端已经通过token来判断用户是否已登录
    @ApiOperation(value = "是否已登录", notes = "查看用户登录状态")
    @GetMapping("/isLogin")
    public Result<Boolean> isLogin(@RequestParam Long userId) {
        return sysLoginService.isLogin(userId);
    }
}
